package Environment.LanguageObjects;

import Environment.Namespaces.Namespaces;
import Execution.Blocks.Block;
import Execution.Blocks.FrozenMultipleTokensBlock;

import java.util.ArrayList;

public class FrozenBlock extends LanguageObject {
    private final ArrayList<Block> blocks;

    public FrozenBlock(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public void run() {
        Namespaces.getInstance().pushNamespace();
        blocks.forEach(Block::execute);
        Namespaces.getInstance().popNamespace();
    }

    @Override
    public String toString() {
        return blocks.toString();
    }
}
