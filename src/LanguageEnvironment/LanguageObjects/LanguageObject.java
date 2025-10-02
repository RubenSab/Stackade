package LanguageEnvironment.LanguageObjects;

import LanguageEnvironment.LanguageObjects.Primitives.NamespaceReference;
import LanguageExecution.Interpreter.Error;
import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public abstract class LanguageObject {

    public LanguageObject resolve() {
        if (this instanceof NamespaceReference) {
            return this.resolve();
        } else {
            return this;
        }
    }

    public <T extends LanguageObject> T tryCast(Class<T> targetClass, TokenAndLineWrapper tokenWrapper) {
        try {
            return targetClass.cast(this);
        } catch (ClassCastException e) {
            ErrorsLogger.triggerError(tokenWrapper, Error.WRONG_OPERANDS_TYPE);
            throw new RuntimeException("placeholder exception");
        }
    }



    public abstract String represent();

    public abstract String typeName();
}
