package Execution.Blocks;

import Environment.DataStack;

public interface Block {
    void add(Block block);
    void execute() throws DataStack.EmptyPopException;
}