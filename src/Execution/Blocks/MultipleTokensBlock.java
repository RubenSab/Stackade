package Execution.Blocks;
import Environment.DataStack;
import Environment.LanguageObjects.UnexecutedSequence;
import Execution.Tokens.KeywordToken;

import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();

    public void forceExecuteEveryBlockInside() {
        for (Block b : blocks) {
            if (b instanceof SingleTokenBlock && ((SingleTokenBlock) b).getToken() == KeywordToken.BREAKPOINT) {
                break;
            }
            b.execute();
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
