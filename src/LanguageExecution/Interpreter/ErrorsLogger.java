package LanguageExecution.Interpreter;

import LanguageExecution.Tokens.TokenAndLineWrapper;

public class ErrorsLogger {

    public static void triggerError(TokenAndLineWrapper tokenWrapper, Error error) {
        System.out.println("Error from " + tokenWrapper.toString() + ": " + error);
        Runtime.getRuntime().halt(0);
    }
}
