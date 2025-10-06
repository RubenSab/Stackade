package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;

public abstract class Block {

    public abstract void add(Block block);
    public abstract void execute() throws DataStack.EmptyPopException;
}
