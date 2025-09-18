package Execution.Blocks;

import Environment.DataStack;
import Environment.LanguageObjects.FrozenBlock;

public class FrozenMultipleTokensBlock extends MultipleTokensBlock {
    @Override
    public void execute() {
        DataStack.getInstance().push(new FrozenBlock(blocks));
    }
}
