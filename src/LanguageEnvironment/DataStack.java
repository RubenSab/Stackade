package LanguageEnvironment;

import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.Stack;

public class DataStack {
    private static final DataStack INSTANCE = new DataStack();
    private final Stack<LanguageObject> stack = new Stack<>();

    public static DataStack getInstance() {
        return INSTANCE;
    }

    public void push(LanguageObject object) {
        stack.push(object);
    }

    public LanguageObject pop(TokenAndLineWrapper possibleErrorSource) {
        if (!stack.isEmpty()) {
            return stack.pop();
        } else {
            ErrorsLogger.triggerInterpreterError(possibleErrorSource, StackadeError.EMPTY_STACK);
            return null;
        }
    }

    public LanguageObject peek(TokenAndLineWrapper possibleErrorSource) {
        if (!stack.isEmpty()) {
            return stack.peek();
        } else {
            ErrorsLogger.triggerInterpreterError(possibleErrorSource, StackadeError.EMPTY_STACK);
            return null;
        }
    }

    public void swap() {
        LanguageObject first = stack.pop();
        LanguageObject second = stack.pop();
        stack.push(first);
        stack.push(second);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    public static class EmptyPopException extends RuntimeException {
        public EmptyPopException() {
            super("Attempted to pop from empty stack.");
        }
    }
}