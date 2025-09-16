package Environment;

import Environment.LanguageElements.DataElements.DataElement;

import java.util.Stack;

public class DataStack {
    private static final DataStack INSTANCE = new DataStack();
    private final Stack<DataElement> stack;

    private DataStack() {
        stack = new Stack<>();
    }

    public static DataStack getInstance() {
        return INSTANCE;
    }

    public void push(DataElement object) {
        stack.push(object);
    }

    public DataElement pop() throws EmptyPopException {
        if (!stack.isEmpty()) {
            return stack.pop();
        } else {
            throw new EmptyPopException();
        }
    }

    public DataElement peek() {
        if (!stack.isEmpty()) {
            return stack.peek();
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    public static class EmptyPopException extends Exception {
        public EmptyPopException() {
            super("Attempted to pop from empty stack.");
        }
    }
}