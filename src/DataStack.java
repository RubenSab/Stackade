import LanguageElements.DataElement;

import java.util.Stack;

public class DataStack {
    private final Stack<DataElement> stack = new Stack<>();

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

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public static class EmptyPopException extends Exception {
        public EmptyPopException() {
            super("Attempted to pop from empty stack.");
        }
    }

}