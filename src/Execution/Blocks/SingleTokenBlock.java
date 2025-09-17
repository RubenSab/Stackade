package Execution.Blocks;

import Execution.OperationRegistry;
import Execution.Tokens.Token;

public class SingleTokenBlock implements Block {
    private final Token token;

    public SingleTokenBlock(Token token) {
        this.token = token;
    }

    @Override
    public void execute() {
        OperationRegistry.executeToken(token);
    }

    @Override
    public String toString() {
        return token.toString();
    }

    @Override
    public void add(Block block) {
        throw new RuntimeException("SingleTokenBlock doesn't support adding blocks");
    }
}
