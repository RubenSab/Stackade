package LanguageExecution.Interpreter;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.Stack;

public class Interpreter {
    private final static Interpreter INSTANCE = new Interpreter();
    private final Stack<Block> blockStack = new Stack<>();

    public static Interpreter getInstance() {
        return INSTANCE;
    }

    public void run(MultipleTokensBlock block) {
        pushMultipleBlockContent(block);
        while (!blockStack.isEmpty()) {
            Block topBlock = blockStack.pop();
            switch (topBlock) {
                case SingleTokenBlock singleTokenBlock -> {
                    if (singleTokenBlock.tokenWrapper().token().equals(KeywordToken.SELF)) { // Self referencing
                        pushConditionalBLockContent(ConditionalContextsStack.getInstance().pop());
                    } else {
                        singleTokenBlock.execute();
                    }
                }
                case MultipleTokensBlock multipleTokensBlock -> pushMultipleBlockContent(multipleTokensBlock);
                case ConditionalBlock conditionalBlock -> pushConditionalBLockContent(conditionalBlock);
                default -> throw new IllegalStateException("Unexpected value: " + topBlock);
            }
        }
    }

    private void pushMultipleBlockContent(MultipleTokensBlock block) {
        blockStack.addAll(block.getBlocks().reversed());
    }

    private void pushConditionalBLockContent(ConditionalBlock condBlock) {
        ConditionalContextsStack.getInstance().push(condBlock);
        MultipleTokensBlock conditionBlock = condBlock.getConditionBlock();
        MultipleTokensBlock trueBlock = condBlock.getTrueBlock();
        MultipleTokensBlock falseBlock = condBlock.getFalseBlock();
        TokenAndLineWrapper beginTokenWrapper = condBlock.getBeginTokenWrapper();
        try {
            ConditionalContextsStack.getInstance().push(condBlock);
            conditionBlock.executeEveryBlockInside(); // TODO: check
            boolean conditionResult = (DataStack.getInstance().pop(beginTokenWrapper).tryCast(BooleanPrimitive.class, beginTokenWrapper)).getValue();
            if (conditionResult) {
                pushMultipleBlockContent(trueBlock);
            } else {
                if (falseBlock!=null) {
                    pushMultipleBlockContent(falseBlock);
                }
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
        }
    }
}
