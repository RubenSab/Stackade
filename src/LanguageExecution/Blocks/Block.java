package LanguageExecution.Blocks;

import LanguageEnvironment.DataStack;

public interface Block {
    void add(Block block);

    void execute() throws DataStack.EmptyPopException;
}