package Execution.Blocks;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.Namespaces.Namespaces;

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
        Namespaces.getInstance().pushNamespace();
        try {
            conditionBlock.execute();
            BooleanPrimitive conditionResult = (BooleanPrimitive) DataStack.getInstance().pop();
            if (conditionResult.getValue()) {
                trueBlock.execute();
            } else {
                falseBlock.execute();
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
            Namespaces.getInstance().popNamespace();
        }
    }

    @Override
    public String toString() {
        return "{" + conditionBlock + " " + trueBlock + " " + falseBlock + "}";
    }
}
