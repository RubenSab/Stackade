package LanguageExecution.Blocks;

import LanguageExecution.Tokens.TokenWrapper;

import java.util.Objects;

public class ConditionalBlock extends Block {
    private final TokenWrapper beginTokenWrapper;
    private MultipleTokensBlock conditionBlock;
    private MultipleTokensBlock trueBlock;
    private MultipleTokensBlock falseBlock;

    public ConditionalBlock(TokenWrapper beginTokenWrapper, Block parent) {
        super(parent);
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
    public void setUnusedRecursive() {
        setUsed(false);
        conditionBlock.setUnusedRecursive();
        trueBlock.setUnusedRecursive();
        if (falseBlock != null) {
            falseBlock.setUnusedRecursive();
        }
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

    public TokenWrapper getBeginTokenWrapper() {
        return beginTokenWrapper;
    }

    @Override
    public String toString() {
        return "conditional block(condition block(" + conditionBlock + "), true block(" + trueBlock + "), false block(" + falseBlock + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        ConditionalBlock that = (ConditionalBlock) o;
        return Objects.equals(beginTokenWrapper, that.beginTokenWrapper) && Objects.equals(getConditionBlock(), that.getConditionBlock()) && Objects.equals(getTrueBlock(), that.getTrueBlock()) && Objects.equals(getFalseBlock(), that.getFalseBlock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginTokenWrapper, getConditionBlock(), getTrueBlock(), getFalseBlock());
    }
}
