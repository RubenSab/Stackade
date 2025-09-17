package Environment;

import Environment.LanguageElements.DataElements.DataElement;
import Execution.Blocks.ConditionalBlock;

import java.util.Stack;

public class ConditionalContextsStack {
    private final static ConditionalContextsStack INSTANCE = new ConditionalContextsStack();
    private final Stack<DataElement> stack = new Stack<>();

    public static ConditionalContextsStack getInstance() {
        return INSTANCE;
    }

    public void push(ConditionalBlock conditionalBlock) {
        stack.push(stack.pop());
    }

    public void pop() {
        stack.pop();
    }

    public void executeTop() {
        stack.peek().execute();
    }
}
