package LanguageExecution.Interpreter;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public class Interpreter {
    private final static Interpreter INSTANCE = new Interpreter();
    private Block currentBlock;

    public static Interpreter getInstance() {
        return INSTANCE;
    }

    public void interpret(MultipleTokensBlock mainBlock) {
        currentBlock = mainBlock;
        while (true) {
            switch (currentBlock) {
                case SingleTokenBlock singleTokenBlock -> {
                    if (singleTokenBlock.getTokenWrapper().token().equals(KeywordToken.SELF)) {
                        while (!(currentBlock instanceof ConditionalBlock)) {
                            currentBlock = currentBlock.getParent();
                        }
                        currentBlock.setChildrenUnused();
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
                            conditionalBlock.getTrueBlock().executeEveryBlockInside();
                        } else {
                            if (conditionalBlock.getFalseBlock()!=null) {
                                conditionalBlock.getFalseBlock().executeEveryBlockInside();
                            }
                        }
                    }
                }
                case MultipleTokensBlock multipleTokensBlock -> {
                    if (multipleTokensBlock.getFirstBlock().getUsed()) {
                        goToNextElseParent();
                    } else {
                        currentBlock = multipleTokensBlock.getFirstBlock();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + currentBlock);
            }
        }
    }

    private void goToParent() {
        Block parent = currentBlock.getParent();
        if (parent == null) {
            ErrorsLogger.halt();
        } else {
            currentBlock = parent;
        }
    }

    private void goToNextElseParent() {
        System.out.println(currentBlock);
        Block next = currentBlock.getNext();
        if (next == null) {
            goToParent();
        } else {
            currentBlock = next;
        }
    }
}
