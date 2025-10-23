package LanguageExecution.Interpreter;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageEnvironment.LanguageObjects.Sequence;
import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.NamespaceToken;
import LanguageExecution.Tokens.Token;
import LanguageExecution.Tokens.TokenWrapper;

import java.util.EmptyStackException;

public class Interpreter {
    private Block currentBlock;

    public void interpret(Block mainBlock) {
        currentBlock = mainBlock;
        while (currentBlock != null) {
            switch (currentBlock) {
                case SingleTokenBlock singleTokenBlock -> {
                    TokenWrapper wrapper = singleTokenBlock.getTokenWrapper();
                    Token token = singleTokenBlock.getTokenWrapper().token();

                    if (token instanceof NamespaceToken || token.equals(KeywordToken.RUN_SEQ)) {
                        LanguageObject invoked = token instanceof NamespaceToken ?
                                ((NamespaceToken) token).resolve() : DataStack.getInstance().pop(wrapper).tryCast(Sequence.class);
                        if (invoked instanceof Sequence) {
                            MultipleTokensBlock sequenceBlocks = ((Sequence) invoked).getBlocks();
                            sequenceBlocks.setUnusedRecursive();
                            Namespaces.getInstance().pushNamespace(); // Namespace popping is handled by END_SEQ KeywordToken in OperationRegistry
                            Block next = currentBlock.getNext();
                            if (next == null) {
                                next = currentBlock.getParent().getNext();
                            }
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
                    if (conditionalBlock.getConditionBlock() != null) { // empty conditional block
                        if (conditionalBlock.getConditionBlock().getUsed()) {
                            goToNextElseParent();
                        } else {
                            conditionalBlock.getConditionBlock().evaluate();
                            conditionalBlock.getConditionBlock().setUsed(true);
                            try {
                                boolean conditionResult = (
                                        DataStack.getInstance().pop(conditionalBlock.getBeginTokenWrapper())
                                                .tryCast(BooleanPrimitive.class))
                                                .getValue();
                                if (conditionResult) {
                                    currentBlock = conditionalBlock.getTrueBlock();
                                } else {
                                    if (conditionalBlock.getFalseBlock()!=null) {
                                        currentBlock = conditionalBlock.getFalseBlock();
                                    }
                                }
                            } catch (EmptyStackException e) {
                                ErrorsLogger.triggerInterpreterError(StackadeError.EMPTY_STACK);
                            }
                        }
                    } else {
                        currentBlock.setUsed(true);
                        goToNextElseParent();
                    }
                }
                case MultipleTokensBlock multipleTokensBlock -> {
                    // System.out.println(multipleTokensBlock);
                    if (
                            multipleTokensBlock.getNext() != null && (multipleTokensBlock.getNext() instanceof SingleTokenBlock) &&
                            ((((SingleTokenBlock) multipleTokensBlock.getNext()).getTokenWrapper().token()).equals(KeywordToken.DEFINE_SEQ) ||
                            (((SingleTokenBlock) multipleTokensBlock.getNext()).getTokenWrapper().token()).equals(KeywordToken.PUSH_SEQ))
                    ){
                        DataStack.getInstance().push(new Sequence(multipleTokensBlock));
                        goToNextElseParent();
                    } else {
                        if (multipleTokensBlock.getUsed()) {
                            goToNextElseParent();
                        } else {
                            // the current block is set used as soon as it's traversed, the blocks to execute are inside of it anyway.
                            currentBlock.setUsed(true);
                            if (multipleTokensBlock.getFirstBlock() != null) {
                                currentBlock = multipleTokensBlock.getFirstBlock();
                            } else {
                                currentBlock = currentBlock.getParent();
                            }
                        }
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + currentBlock);
            }
        }
    }

    public void goToParent() {
        Block parent = currentBlock.getParent();
        currentBlock = parent;
    }

    private void goToNextElseParent() {
        Block next = currentBlock.getNext();
        if (next == null) {
            goToParent();
        } else {
            currentBlock = next;
        }
    }
}
