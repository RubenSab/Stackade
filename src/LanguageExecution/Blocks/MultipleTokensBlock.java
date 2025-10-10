package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.ArrayList;

public class MultipleTokensBlock extends Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();
    private final TokenAndLineWrapper beginTokenWrapper;

    public MultipleTokensBlock(Block parent) {
        super(parent);
        this.beginTokenWrapper = null;
    }

    public MultipleTokensBlock(TokenAndLineWrapper beginTokenWrapper, Block parent) {
        super(parent);
        this.beginTokenWrapper = beginTokenWrapper;
    }

    public Block getFirstBlock() {
        return blocks.getFirst();
    }

    public void executeEveryBlockInside() { // TODO: push to exec
        blocks.forEach(Block::execute);
    }

    @Override
    public void add(Block block) {
        if (!blocks.isEmpty()) {
            blocks.getLast().setNext(block);
        }
        blocks.add(block);
    }

    @Override
    public void setUnusedRecursive() {
        setUsed(false);
        blocks.forEach(Block::setUnusedRecursive);
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
