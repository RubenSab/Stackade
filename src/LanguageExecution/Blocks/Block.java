package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;

import java.util.List;

public abstract class Block {
    private final Block parent;
    private boolean traversed;

    public Block(Block parent) {
        this.parent = parent;
    }

    public Block getParent() {
        return parent;
    }

    public boolean isTraversed() {
        return traversed;
    }

    public void setTraversed(boolean traversed) {
        this.traversed = traversed;
    }

    public abstract void add(Block block);
    public abstract void execute() throws DataStack.EmptyPopException;
    public abstract List<Block> getChildren();
}