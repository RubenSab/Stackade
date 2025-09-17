package Environment;

import Execution.Blocks.ConditionalBlock;

import java.util.Stack;

public class ConditionalContextsStack {
    private final static ConditionalContextsStack INSTANCE = new ConditionalContextsStack();
    private final Stack<ConditionalBlock> stack = new Stack<>();

    public static ConditionalContextsStack getInstance() {
        return INSTANCE;
    }

    public void push(ConditionalBlock conditionalBlock) {
        stack.push(conditionalBlock);
    }

    public void pop() {
        stack.pop();
    }

    public void executeTop() {
        stack.peek().execute();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
