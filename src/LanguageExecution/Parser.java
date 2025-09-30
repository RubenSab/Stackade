package LanguageExecution;

import LanguageExecution.Blocks.*;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.List;
import java.util.Stack;

public class Parser {

    public static MultipleTokensBlock parse(List<TokenAndLineWrapper> tokens) {
        Stack<Block> blockStack = new Stack<>();
        blockStack.push(new MultipleTokensBlock());

        for (TokenAndLineWrapper tokenAndLineWrapper : tokens) {
            switch (tokenAndLineWrapper.token()) {
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
                default -> blockStack.peek().add(new SingleTokenBlock(tokenAndLineWrapper));
            }
        }

        return (MultipleTokensBlock) blockStack.pop();
    }
}
