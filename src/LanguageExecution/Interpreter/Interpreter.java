package LanguageExecution.Interpreter;

import LanguageExecution.Blocks.MultipleTokensBlock;

public class Interpreter {
    private final static Interpreter INSTANCE = new Interpreter();

    public static Interpreter getInstance() {
        return INSTANCE;
    }

    public static void execute(MultipleTokensBlock multipleTokensBlock) {
        multipleTokensBlock.executeEveryBlockInside();
    }
}
