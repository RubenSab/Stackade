package Execution;

import Execution.Blocks.*;
import Execution.Tokens.KeywordToken;
import Execution.Tokens.Token;

import java.util.ArrayList;
import java.util.Stack;

public class Parser {

    public static MultipleTokensBlock parse(ArrayList<Token> tokens) {
        Stack<Block> blockStack = new Stack<>();
        blockStack.push(new MultipleTokensBlock());

        for (Token t : tokens) {
            switch (t) {
                case KeywordToken.OPEN_BLOCK -> blockStack.push(new MultipleTokensBlock());
                case KeywordToken.CLOSE_BLOCK -> {
                    Block lastBlock = blockStack.pop();
                    blockStack.peek().add(lastBlock);
                }
                case KeywordToken.OPEN_COND -> blockStack.push(new ConditionalBlock());
                case KeywordToken.CLOSE_COND -> {
                    Block lastBlock = blockStack.pop();
                    blockStack.peek().add(lastBlock);
                }
                case KeywordToken.OPEN_FROZEN -> blockStack.push(new FrozenMultipleTokensBlock());
                case KeywordToken.CLOSE_FROZEN -> {
                    Block lastBlock = blockStack.pop();
                    blockStack.peek().add(lastBlock);
                }
                default -> blockStack.peek().add(new SingleTokenBlock(t));
            }
        }

        return (MultipleTokensBlock) blockStack.pop();
    }
}
