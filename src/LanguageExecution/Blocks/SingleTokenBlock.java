package LanguageExecution.Blocks;

import LanguageExecution.Interpreter.OperationRegistry;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.List;

public class SingleTokenBlock extends Block {

    private final TokenAndLineWrapper tokenWrapper;

    public SingleTokenBlock(TokenAndLineWrapper tokenWrapper, Block parent) {
        super(parent);
        this.tokenWrapper = tokenWrapper;
    }

    public TokenAndLineWrapper getTokenWrapper() {
        return tokenWrapper;
    }

    @Override
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
    public void setChildrenUnused() {
        this.setUsed(false);
    }
}
