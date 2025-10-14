package LanguageEnvironment.LanguageObjects;

import LanguageExecution.Blocks.MultipleTokensBlock;

import java.util.Objects;

public class Sequence extends LanguageObject {
    private final MultipleTokensBlock blocks;
    private String name;

    public Sequence(MultipleTokensBlock blocks) {
        this.blocks = blocks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipleTokensBlock getBlocks() {
        return blocks;
    }

    @Override
    public String represent() {
        return "sequence \"" + name + "\"";
    }

    @Override
    public String typeName() {
        return "seq";
    }

    @Override
    public String toString() {
        return "sequence: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        Sequence that = (Sequence) o;
        return Objects.equals(blocks, that.blocks) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, name);
    }
}
