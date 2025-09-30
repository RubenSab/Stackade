package LanguageExecution.Blocks;

import LanguageExecution.OperationRegistry;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public record SingleTokenBlock(TokenAndLineWrapper tokenWrapper) implements Block {

    @Override
    public void execute() {
        OperationRegistry.executeToken(tokenWrapper);
    }

    @Override
    public String toString() {
        return tokenWrapper.toString();
    }

    @Override
    public void add(Block block) {
        throw new RuntimeException("SingleTokenBlock doesn't support adding blocks");
    }
}
