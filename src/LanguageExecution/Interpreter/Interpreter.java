package LanguageExecution.Interpreter;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageEnvironment.LanguageObjects.Primitives.NamespaceReference;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.NamespaceToken;
import LanguageExecution.Tokens.Token;

public class Interpreter {
    private Block currentBlock;

    public void interpret(Block mainBlock) {
        currentBlock = mainBlock;
        while (currentBlock != null) {
            //System.out.println(">> " + currentBlock + " " + currentBlock.getClass());
            switch (currentBlock) {
                case SingleTokenBlock singleTokenBlock -> {
                    Token token = singleTokenBlock.getTokenWrapper().token();
                    if (token instanceof NamespaceToken) {
                        LanguageObject invoked = ((NamespaceToken) token).resolve();
                        if (invoked instanceof UnexecutedSequence) {
                            Interpreter sequenceInterpreter = new Interpreter();
                            MultipleTokensBlock blocks = ((UnexecutedSequence) invoked).getBlocks();
                            blocks.setNext(currentBlock.getNext());
                            sequenceInterpreter.interpret(blocks);
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
                        currentBlock.execute();
                        currentBlock.setUsed(true);
                        goToNextElseParent();
                    }
                }
                case ConditionalBlock conditionalBlock -> {
                    if (conditionalBlock.getConditionBlock().getUsed()) {
                        goToNextElseParent();
                    } else {
                        conditionalBlock.getConditionBlock().executeEveryBlockInside();
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
                        if (multipleTokensBlock.getFirstBlock().getUsed()) {
                            goToNextElseParent();
                        } else {
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
        Block next = currentBlock.getNext();
        if (next == null) {
            goToParent();
        } else {
            currentBlock = next;
        }
    }
}
