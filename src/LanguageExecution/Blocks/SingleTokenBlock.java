package LanguageExecution.Blocks;

import LanguageExecution.Interpreter.OperationRegistry;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.Objects;


public class SingleTokenBlock extends Block {

    private final TokenAndLineWrapper tokenWrapper;

    public SingleTokenBlock(TokenAndLineWrapper tokenWrapper, Block parent) {
        super(parent);
        this.tokenWrapper = tokenWrapper;
    }

    public TokenAndLineWrapper getTokenWrapper() {
        return tokenWrapper;
    }

    public void execute() { // TODO: push to exec
        OperationRegistry.getInstance().executeToken(tokenWrapper);
    }

    @Override
    public String toString() {
        return tokenWrapper.toString();
    }

    @Override
    public void add(Block block) {
        throw new RuntimeException("SingleTokenBlock doesn't support adding blocks");
    }

    @Override
    public void setUnusedRecursive() {
        setUsed(false);
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        SingleTokenBlock that = (SingleTokenBlock) o;
        return Objects.equals(getTokenWrapper(), that.getTokenWrapper());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTokenWrapper());
    }
}
