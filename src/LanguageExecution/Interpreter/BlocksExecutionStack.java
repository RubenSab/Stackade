package LanguageExecution.Interpreter;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.Stack;

public class BlocksExecutionStack {
    private static final BlocksExecutionStack INSTANCE = new BlocksExecutionStack();
    private final Stack<Block> executionStack = new Stack<>();

    public static BlocksExecutionStack getInstance() {
        return INSTANCE;
    }

    private void runBlock(SingleTokenBlock block) {
        block.execute();
    }

    private void runBlock(MultipleTokensBlock block) {
        executionStack.addAll(block.getBlocks().reversed());
    }

    private void runBlock(ConditionalBlock block) {
        MultipleTokensBlock conditionBlock = block.getConditionBlock();
        MultipleTokensBlock trueBlock = block.getTrueBlock();
        MultipleTokensBlock falseBlock = block.getFalseBlock();
        TokenAndLineWrapper beginTokenWrapper = block.getBeginTokenWrapper();

        ConditionalContextsStack.getInstance().push(block);
        try {
            conditionBlock.executeEveryBlockInside(); // don't change

            boolean conditionResult = (DataStack.getInstance().pop(beginTokenWrapper).tryCast(BooleanPrimitive.class, beginTokenWrapper)).getValue();
            if (conditionResult) {
                runBlock(trueBlock);
            } else {
                if (falseBlock!=null) {
                    runBlock(falseBlock);
                }
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
        }
    }

    public void run() {
        while (!executionStack.isEmpty()) {
            Block current = executionStack.pop();
            switch (current) {
                case SingleTokenBlock single -> runBlock(single);
                case MultipleTokensBlock multiple -> runBlock(multiple);
                case ConditionalBlock conditional -> runBlock(conditional);
                default -> System.out.println("unknown block");
            }
        }
    }


}
