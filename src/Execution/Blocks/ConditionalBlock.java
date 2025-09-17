package Execution.Blocks;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;

public class ConditionalBlock implements Block {
    private Block conditionBlock;
    private Block trueBlock;
    private Block falseBlock;

    @Override
    public void add(Block block) {
        if (conditionBlock == null) {
            conditionBlock = block;
        } else if (trueBlock == null) {
            trueBlock = block;
        } else if (falseBlock == null) {
            falseBlock = block;
        }
    }

    @Override
    public void execute() {
        ConditionalContextsStack.getInstance().push(this);
        conditionBlock.execute();
        if (DataStack.getInstance().pop().equals(new BooleanPrimitive("true"))) {
            trueBlock.execute();
        } else {
            falseBlock.execute();
        }
        ConditionalContextsStack.getInstance().pop();
    }

    public Block getConditionBlock() {
        return conditionBlock;
    }

    public Block getTrueBlock() {
        return trueBlock;
    }

    public Block getFalseBlock() {
        return falseBlock;
    }

    @Override
    public String toString() {
        return "{\n\t" + conditionBlock + "\n\t" + trueBlock + "\n\t" + falseBlock + "\n}";
    }
}
