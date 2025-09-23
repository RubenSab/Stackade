package Execution;

import Environment.Namespaces.Namespaces;
import Execution.Blocks.MultipleTokensBlock;

public class Interpreter {
    public static void execute(MultipleTokensBlock multipleTokensBlock) {
        Namespaces.getInstance().pushNamespace();
        multipleTokensBlock.forceExecuteEveryBlockInside();
    }
}
