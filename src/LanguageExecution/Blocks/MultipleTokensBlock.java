package LanguageExecution.Blocks;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageExecution.Interpreter.Interpreter;

import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();

    public void executeEveryBlockInside() {
        for (Block b : blocks) {
            if (!Interpreter.getInstance().isHalted()) b.execute();
        }
    }

    @Override
    public void add(Block block) {
        blocks.add(block);
    }

    @Override
    public void execute() {
        DataStack.getInstance().push(new UnexecutedSequence(blocks));
    }

    @Override
    public String toString() {
        return "multipleTokensBlock(" + blocks + ")";
    }
}
