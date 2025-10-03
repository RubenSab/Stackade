package LanguageExecution.Interpreter;

import LanguageExecution.Tokens.TokenAndLineWrapper;

public class ErrorsLogger {

    public static void triggerParserError(StackadeError stackadeError) {
        System.out.println("Parser error: " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(TokenAndLineWrapper tokenWrapper, StackadeError stackadeError) {
        System.out.println("Interpreter error from " + tokenWrapper.toString() + ": " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(StackadeError stackadeError) {
        System.out.println("Interpreter error: " + stackadeError);
        halt();
    }

    private static void halt() {
        Runtime.getRuntime().halt(0);
    }
}
