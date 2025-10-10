package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;

public abstract class Block {
    private final Block parent;
    private Block next;

    private boolean used = false;

    public Block(Block parent) {
        this.parent = parent;
    }

    public void setNext(Block next) {
        this.next = next;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean getUsed() {
        return used;
    }

    public Block getParent() {
        return parent;
    }

    public Block getNext() {
        return next;
    }

    public abstract void add(Block block);
    public abstract void setUnusedRecursive();
    public abstract void execute() throws DataStack.EmptyPopException;
}