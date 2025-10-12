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
        //try {
            Stack<Block> blockStack = new Stack<>();
            blockStack.push(new MultipleTokensBlock(null));

            for (TokenAndLineWrapper tokenAndLineWrapper : tokens) {
                switch (tokenAndLineWrapper.token()) {
                    case KeywordToken.OPEN_BLOCK -> blockStack.push(new MultipleTokensBlock(tokenAndLineWrapper, blockStack.peek()));
                    case KeywordToken.OPEN_COND -> blockStack.push(new ConditionalBlock(tokenAndLineWrapper, blockStack.peek()));
                    case KeywordToken.CLOSE_BLOCK, KeywordToken.CLOSE_COND -> {
                        Block lastBlock = blockStack.pop();

                        // If the top Block is a Sequence definition, add an END_SEQ token at the end of it
                        if (!(lastBlock instanceof SingleTokenBlock) && !(lastBlock instanceof ConditionalBlock) && !(lastBlock.getParent() instanceof ConditionalBlock)) {
                            lastBlock.add(new SingleTokenBlock(new TokenAndLineWrapper(KeywordToken.END_SEQ, null, null), lastBlock));
                        }

                        blockStack.peek().add(lastBlock);
                    }
                    default -> {
                        Block newBlock = new SingleTokenBlock(tokenAndLineWrapper, blockStack.peek());
                        blockStack.peek().add(newBlock);
                    }
                }
            }
        System.out.println(blockStack.peek());
            return (MultipleTokensBlock) blockStack.pop();

        //} catch (ClassCastException e) {
        //    ErrorsLogger.triggerParserError(StackadeError.INVALID_BRACKETING);
        //    return null; // ensure method always returns something
        //}
    }

}
