package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();
    private final TokenAndLineWrapper beginTokenWrapper;

    public MultipleTokensBlock() {
        this.beginTokenWrapper = null;
    }

    public MultipleTokensBlock(TokenAndLineWrapper beginTokenWrapper) {
        this.beginTokenWrapper = beginTokenWrapper;
    }

    public void executeEveryBlockInside() {
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
        return "multipleTokensBlock(" + blocks + ")";
    }
}
