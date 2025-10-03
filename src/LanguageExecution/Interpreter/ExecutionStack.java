package LanguageExecution.Interpreter;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.Stack;

public class ExecutionStack {
    private static final ExecutionStack INSTANCE = new ExecutionStack();
    private final Stack<Block> executionStack = new Stack<>();

    public static ExecutionStack getInstance() {
        return INSTANCE;
    }

    public void run() {
        while (!executionStack.isEmpty()) {
            Block current = executionStack.pop();
            switch (current) {
                case SingleTokenBlock single -> standardRunBlock(single);
                case MultipleTokensBlock multiple -> multiple.execute();
                case ConditionalBlock conditional -> standardRunBlock(conditional);
                default -> System.out.println("unknown block");
            }
        }
    }

    public void standardRunBlock(SingleTokenBlock block) {
        block.execute();
    }

    public void standardRunBlock(MultipleTokensBlock block) {
        executionStack.addAll(block.getBlocks().reversed());
    }

    public void standardRunBlock(ConditionalBlock block) {
        MultipleTokensBlock conditionBlock = block.getConditionBlock();
        MultipleTokensBlock trueBlock = block.getTrueBlock();
        MultipleTokensBlock falseBlock = block.getFalseBlock();
        TokenAndLineWrapper beginTokenWrapper = block.getBeginTokenWrapper();

        ConditionalContextsStack.getInstance().push(block);
        try {
            conditionBlock.executeEveryBlockInside(); // don't change

            boolean conditionResult = (DataStack.getInstance().pop(beginTokenWrapper).tryCast(BooleanPrimitive.class, beginTokenWrapper)).getValue();
            if (conditionResult) {
                standardRunBlock(trueBlock);
            } else {
                if (falseBlock!=null) {
                    standardRunBlock(falseBlock);
                }
            }
        } finally {
            // ConditionalContextsStack.getInstance().pop();
        }
    }


}
