package Environment.LanguageObjects;

import Environment.Namespaces.Namespaces;
import Execution.Blocks.Block;

import java.util.ArrayList;

public class UnexecutedSequence extends LanguageObject {
    private final ArrayList<Block> blocks;
    private String name;

    public UnexecutedSequence(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run() {
        Namespaces.getInstance().pushNamespace();
        blocks.forEach(Block::execute);
        Namespaces.getInstance().popNamespace();
    }

    @Override
    public String represent() {
        return "sequence \"" + name + "\"";
    }

    @Override
    public String toString() {
        return "unexecuted sequence(" + name + ")";
    }
}
