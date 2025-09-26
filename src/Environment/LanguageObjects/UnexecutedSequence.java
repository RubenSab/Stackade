package Environment.LanguageObjects;

import Environment.Namespaces.Namespaces;
import Execution.Blocks.Block;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        UnexecutedSequence that = (UnexecutedSequence) o;
        return Objects.equals(blocks, that.blocks) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, name);
    }
}
