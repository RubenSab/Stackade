package LanguageExecution.Interpreter;

import LanguageExecution.Runner;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public class ErrorsLogger {

    public static void triggerParserError(StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Parser error: " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(TokenAndLineWrapper tokenWrapper, StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Interpreter error from " + tokenWrapper.toString() + ": " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Interpreter error: " + stackadeError);
        halt();
    }

    private static void halt() {
        Runtime.getRuntime().halt(0);
    }
}
