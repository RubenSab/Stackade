package LanguageExecution.Interpreter;

import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Runner;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public class ErrorsLogger {

    public static void triggerParserError(StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Parser error: " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(TokenAndLineWrapper errorSource, StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Interpreter error from " + errorSource.toString() + ": " + stackadeError);
        halt();
    }

    public static void triggerInterpreterError(StackadeError stackadeError) {
        System.out.println("(" + Runner.getInstance().getCurrentFile() + ") Interpreter error: " + stackadeError);
        halt();
    }

    public static void halt() {
        Runtime.getRuntime().halt(0);
    }
}
