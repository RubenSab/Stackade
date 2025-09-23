package Execution.Blocks;
import Environment.DataStack;
import Environment.LanguageObjects.UnexecutedSequence;

import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();

    public void forceExecuteEveryBlockInside() {
        blocks.forEach(Block::execute);
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
        return "multipleTokensBlock: " + blocks;
    }
}
