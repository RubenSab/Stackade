package LanguageExecution.Blocks;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;

public class ConditionalBlock implements Block {
    private MultipleTokensBlock conditionBlock;
    private MultipleTokensBlock trueBlock;
    private MultipleTokensBlock falseBlock;

    @Override
    public void add(Block block) {
        if (conditionBlock == null) {
            conditionBlock = (MultipleTokensBlock) block;
        } else if (trueBlock == null) {
            trueBlock = (MultipleTokensBlock) block;
        } else if (falseBlock == null) {
            falseBlock = (MultipleTokensBlock) block;
        }
    }

    @Override
    public void execute() {
        ConditionalContextsStack.getInstance().push(this);
        try {
            conditionBlock.executeEveryBlockInside();
            boolean conditionResult = ((BooleanPrimitive) DataStack.getInstance().pop()).getValue();
            if (conditionResult) {
                trueBlock.executeEveryBlockInside();
            } else {
                if (falseBlock != null) {
                    falseBlock.executeEveryBlockInside();
                }
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
        }
    }

    @Override
    public String toString() {
        return "conditional block(condition block(" + conditionBlock + "), true block(" + trueBlock + "), false block(" + falseBlock + ")";
    }
}
