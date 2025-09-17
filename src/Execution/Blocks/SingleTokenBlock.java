package Execution.Blocks;

import Execution.Tokens.OperationRegistry;
import Execution.Tokens.Token;

public class SingleTokenBlock implements Block {
    private final Token token;

    public SingleTokenBlock(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public void execute() {
        OperationRegistry.execute(token);
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
