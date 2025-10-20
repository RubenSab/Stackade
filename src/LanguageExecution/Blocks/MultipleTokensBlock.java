package LanguageExecution.Blocks;

import LanguageExecution.Tokens.TokenWrapper;

import java.util.ArrayList;
import java.util.Objects;

public class MultipleTokensBlock extends Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();
    private final TokenWrapper beginTokenWrapper;

    public MultipleTokensBlock(Block parent) {
        super(parent);
        this.beginTokenWrapper = null;
    }

    public MultipleTokensBlock(TokenWrapper beginTokenWrapper, Block parent) {
        super(parent);
        this.beginTokenWrapper = beginTokenWrapper;
    }

    public Block getFirstBlock() {
        return (!blocks.isEmpty()) ? blocks.getFirst() : null;
    }

    public TokenWrapper getBeginTokenWrapper() {
        return beginTokenWrapper;
    }

    public void evaluate() { // TODO: push to exec
        blocks.forEach(x -> ((SingleTokenBlock) x).execute());
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
    public String toString() {
        return "multipleTokensBlock(" + blocks + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        MultipleTokensBlock that = (MultipleTokensBlock) o;
        return Objects.equals(blocks, that.blocks) && Objects.equals(beginTokenWrapper, that.beginTokenWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, beginTokenWrapper);
    }
}
