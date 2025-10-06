package LanguageExecution.Blocks;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import javax.xml.crypto.Data;

public class ConditionalBlock extends Block {
    private final TokenAndLineWrapper beginTokenWrapper;
    private MultipleTokensBlock conditionBlock;
    private MultipleTokensBlock trueBlock;
    private MultipleTokensBlock falseBlock;

    public ConditionalBlock() {
        this.beginTokenWrapper = null;
    }

    public ConditionalBlock(TokenAndLineWrapper beginTokenWrapper) {
        this.beginTokenWrapper = beginTokenWrapper;
    }

    @Override
    public void add(Block block) {
        if (conditionBlock==null) {
            conditionBlock = (MultipleTokensBlock) block;
        } else if (trueBlock==null) {
            trueBlock = (MultipleTokensBlock) block;
        } else if (falseBlock==null) {
            falseBlock = (MultipleTokensBlock) block;
        }
    }

    @Override
    public void execute() { // TODO: push to exec
        ConditionalContextsStack.getInstance().push(this);
        try {
            conditionBlock.executeEveryBlockInside();

            boolean conditionResult = (DataStack.getInstance().pop(beginTokenWrapper).tryCast(BooleanPrimitive.class, beginTokenWrapper)).getValue();
            if (conditionResult) {
                trueBlock.executeEveryBlockInside();
            } else {
                if (falseBlock!=null) {
                    falseBlock.executeEveryBlockInside();
                }
            }
        } finally {
            ConditionalContextsStack.getInstance().pop();
        }
    }

    @Override
    public String toString() {
        return "conditional block(condition block(" + conditionBlock + "), true block(" + trueBlock + "), false block(" + falseBlock + ")";
    }
}
