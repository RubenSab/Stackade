import Environment.DataStack;
import Execution.Blocks.MultipleTokensBlock;
import Execution.Lexer;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Execution.Parser;
import Execution.Tokens.Token;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataStack stack = DataStack.getInstance();

        new NumberPrimitive("2").execute();
        new NumberPrimitive("3").execute();

        // OperationRegistry.get(ADD).execute();

        System.out.println(stack);

        ArrayList<Token> tokens = Lexer.tokenize("{(i 0 :num i < 10) (i dup 1 + = print SELF) (i \"done\\n\" print)}");
        MultipleTokensBlock blocks = Parser.parse(tokens);
        System.out.println(blocks);

    }
}