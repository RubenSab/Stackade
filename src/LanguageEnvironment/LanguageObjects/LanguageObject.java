package LanguageEnvironment.LanguageObjects;

import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;
import LanguageExecution.Tokens.TokenWrapper;

public abstract class LanguageObject {

    public LanguageObject resolve() {
        if (this instanceof NamespaceReference) {
            return this.resolve();
        } else {
            return this;
        }
    }

    public <T extends LanguageObject> T tryCast(Class<T> targetClass, TokenWrapper tokenWrapper) {
        try {
            return targetClass.cast(this);
        } catch (ClassCastException e) {
            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.WRONG_OPERANDS_TYPE);
            return null;
        }
    }

    public <T extends LanguageObject> T tryCast(Class<T> targetClass) {
        try {
            return targetClass.cast(this);
        } catch (ClassCastException e) {
            ErrorsLogger.triggerInterpreterError(StackadeError.WRONG_OPERANDS_TYPE);
            return null;
        }
    }


    public abstract String represent();

    public abstract String typeName();
}
