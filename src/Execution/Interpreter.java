package Execution;

import Execution.Blocks.MultipleTokensBlock;

public class Interpreter {
    private final static Interpreter INSTANCE = new Interpreter();
    private boolean halted = false;

    public static Interpreter getInstance() {
        return INSTANCE;
    }

    public static void execute(MultipleTokensBlock multipleTokensBlock) {
        multipleTokensBlock.executeEveryBlockInside();
    }

    public void halt() {
        this.halted = true;
    }

    public boolean isHalted() {
        return halted;
    }
}
