package Execution.Blocks;
import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    private final ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void add(Block block) {
        blocks.add(block);
    }

    @Override
    public void execute() {
        blocks.forEach(Block::execute);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "(" + blocks + ")";
    }
}
