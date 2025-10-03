package LanguageEnvironment.LanguageObjects;

import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Interpreter.ExecutionStack;

import java.util.Objects;

public class UnexecutedSequence extends LanguageObject {
    private final MultipleTokensBlock blocks;
    private String name;

    public UnexecutedSequence(MultipleTokensBlock block) {
        this.blocks = block;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void execute() {
        Namespaces.getInstance().pushNamespace(); // TODO: check namespaces mutation
        ExecutionStack.getInstance().standardRunBlock(blocks);
        Namespaces.getInstance().popNamespace(); // TODO: check namespaces mutation
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
