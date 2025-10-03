package LanguageExecution.Blocks;

import LanguageExecution.Interpreter.ExecutionStack;
import LanguageExecution.Tokens.TokenAndLineWrapper;

public class ConditionalBlock implements Block {
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
    public void execute() {
        ExecutionStack.getInstance().standardRunBlock(this);
    }

    public MultipleTokensBlock getConditionBlock() {
        return conditionBlock;
    }

    public MultipleTokensBlock getTrueBlock() {
        return trueBlock;
    }

    public MultipleTokensBlock getFalseBlock() {
        return falseBlock;
    }

    public TokenAndLineWrapper getBeginTokenWrapper() {
        return beginTokenWrapper;
    }

    @Override
    public String toString() {
        return "conditional block(condition block(" + conditionBlock + "), true block(" + trueBlock + "), false block(" + falseBlock + ")";
    }
}
