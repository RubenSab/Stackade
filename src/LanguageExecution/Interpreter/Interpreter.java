package LanguageExecution.Interpreter;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageEnvironment.LanguageObjects.Primitives.NamespaceReference;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.NamespaceToken;
import LanguageExecution.Tokens.Token;

import java.util.Stack;

public class Interpreter {
    private Block currentBlock;
    private final Stack<MultipleTokensBlock> callStack = new Stack<>();

    public void interpret(Block mainBlock) {
        currentBlock = mainBlock;
        while (true) {
            //System.out.println(Namespaces.getInstance());
            // System.out.println(">> " + currentBlock + " " + currentBlock.getUsed());
            switch (currentBlock) {
                case SingleTokenBlock singleTokenBlock -> {
                    Token token = singleTokenBlock.getTokenWrapper().token();
                    if (token instanceof NamespaceToken) {
                        LanguageObject invoked = ((NamespaceToken) token).resolve();
                        if (invoked instanceof UnexecutedSequence) {
                            MultipleTokensBlock sequenceBlocks = ((UnexecutedSequence) invoked).getBlocks();
                            sequenceBlocks.setUnusedRecursive();
                            callStack.push(sequenceBlocks);
                            Namespaces.getInstance().pushNamespace();
                            Block next = currentBlock.getNext();
                            currentBlock = sequenceBlocks; // substitution with function body
                            currentBlock.setNext(next);
                        } else {
                            singleTokenBlock.execute();
                            goToNextElseParent();
                        }
                    } else if (token.equals(KeywordToken.SELF)) {
                        while (!(currentBlock instanceof ConditionalBlock)) {
                            currentBlock = currentBlock.getParent();
                        }
                        currentBlock.setUnusedRecursive();
                    } else {
                        ((SingleTokenBlock) currentBlock).execute();
                        currentBlock.setUsed(true);
                        goToNextElseParent();
                    }
                }
                case ConditionalBlock conditionalBlock -> {
                    if (conditionalBlock.getConditionBlock().getUsed()) {
                        goToNextElseParent();
                    } else {
                        conditionalBlock.getConditionBlock().evaluate();
                        conditionalBlock.getConditionBlock().setUsed(true);
                        boolean conditionResult = (DataStack.getInstance().pop().tryCast(BooleanPrimitive.class)).getValue();
                        if (conditionResult) {
                            currentBlock = conditionalBlock.getTrueBlock();
                        } else {
                            if (conditionalBlock.getFalseBlock()!=null) {
                                currentBlock = conditionalBlock.getFalseBlock();
                            }
                        }
                    }
                }
                case MultipleTokensBlock multipleTokensBlock -> {
                    if (multipleTokensBlock.getNext() != null && (((SingleTokenBlock) multipleTokensBlock.getNext()).getTokenWrapper().token()).equals(KeywordToken.DEFINE_SEQ)) {
                        DataStack.getInstance().push(new UnexecutedSequence(multipleTokensBlock));
                        goToNextElseParent();
                    } else {
                        if (multipleTokensBlock.getUsed()) {
                            goToNextElseParent();
                        } else {
                            // the current block is set used as soon as it's traversed, the blocks to execute are inside of it anyway.
                            currentBlock.setUsed(true);
                            currentBlock = multipleTokensBlock.getFirstBlock();
                        }
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + currentBlock);
            }
        }
    }

    public void goToParent() {
        Block parent = currentBlock.getParent();
        if (parent == null) {
            ErrorsLogger.halt();
        } else {
            currentBlock = parent;
        }
    }

    private void goToNextElseParent() {
        if (!callStack.isEmpty() && currentBlock.equals(callStack.peek())) {
            callStack.pop();
            Namespaces.getInstance().popNamespace();
        }
        Block next = currentBlock.getNext();
        if (next == null) {
            goToParent();
        } else {
            currentBlock = next;
        }
    }
}
