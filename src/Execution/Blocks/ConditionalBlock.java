package Execution.Blocks;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;

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
            conditionBlock.forceExecuteEveryBlockInside();
            BooleanPrimitive conditionResult = (BooleanPrimitive) DataStack.getInstance().pop();
            if (conditionResult.getValue()) {
                trueBlock.forceExecuteEveryBlockInside();
            } else {
                if (falseBlock != null) {
                    falseBlock.forceExecuteEveryBlockInside();
                }
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
        }
    }

    @Override
    public String toString() {
        return "conditional block: " + conditionBlock + ", " + trueBlock + ", " + falseBlock;
    }
}
