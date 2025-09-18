package Execution.Blocks;
import java.util.ArrayList;

public class MultipleTokensBlock implements Block {

    protected final ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void add(Block block) {
        blocks.add(block);
    }

    @Override
    public void execute() {
        blocks.forEach(Block::execute);
    }

    @Override
    public String toString() {
        return "(" + blocks + ")";
    }
}
