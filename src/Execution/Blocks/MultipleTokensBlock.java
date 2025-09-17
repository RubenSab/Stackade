package Execution.Blocks;

import Execution.Tokens.Token;

import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    private final ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void add(Block block) {
        blocks.add(block);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "(" + blocks.toString() + ")";
    }
}
