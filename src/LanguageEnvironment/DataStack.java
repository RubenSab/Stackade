package LanguageEnvironment;

import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.EmptyStackException;
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

    public LanguageObject pop(TokenAndLineWrapper errorSource) {
        if (!stack.isEmpty()) {
            return stack.pop();
        } else {
            ErrorsLogger.triggerInterpreterError(errorSource, StackadeError.EMPTY_STACK);
            return null;
        }
    }

    public LanguageObject peek(TokenAndLineWrapper errorSource) {
        if (!stack.isEmpty()) {
            return stack.peek();
        } else {
            ErrorsLogger.triggerInterpreterError(errorSource, StackadeError.EMPTY_STACK);
            return null;
        }
    }

    public void swap(TokenAndLineWrapper errorSource) {
        try {
            LanguageObject first = stack.pop();
            LanguageObject second = stack.pop();
            stack.push(first);
            stack.push(second);
        } catch (EmptyStackException e) {
            ErrorsLogger.triggerInterpreterError(errorSource, StackadeError.EMPTY_STACK);
        }
    }

    public int height() {
        return stack.size();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}