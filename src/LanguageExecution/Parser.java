package LanguageExecution;

import LanguageExecution.Blocks.Block;
import LanguageExecution.Blocks.ConditionalBlock;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Blocks.SingleTokenBlock;
import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;
import LanguageExecution.Tokens.KeywordToken;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.util.List;
import java.util.Stack;

public class Parser {

    public static MultipleTokensBlock parse(List<TokenAndLineWrapper> tokens) {
        try {
            Stack<Block> blockStack = new Stack<>();
            blockStack.push(new MultipleTokensBlock());

            for (TokenAndLineWrapper tokenAndLineWrapper : tokens) {
                switch (tokenAndLineWrapper.token()) {
                    case KeywordToken.OPEN_BLOCK -> blockStack.push(new MultipleTokensBlock(tokenAndLineWrapper));
                    case KeywordToken.CLOSE_BLOCK, KeywordToken.CLOSE_COND -> {
                        Block lastBlock = blockStack.pop();
                        blockStack.peek().add(lastBlock);
                    }
                    case KeywordToken.OPEN_COND -> blockStack.push(new ConditionalBlock(tokenAndLineWrapper));
                    default -> blockStack.peek().add(new SingleTokenBlock(tokenAndLineWrapper));
                }
            }

            return (MultipleTokensBlock) blockStack.pop();

        } catch (ClassCastException e) {
            ErrorsLogger.triggerParserError(StackadeError.INVALID_BRACKETING);
            return null; // ensure method always returns something
        }
    }

}
