import Environment.DataStack;
import Execution.Blocks.MultipleTokensBlock;
import Execution.Interpreter;
import Execution.Lexer;
import Execution.Parser;
import Execution.Tokens.Token;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = Lexer.tokenize(
                "{(3 2 % 1 != )(\"yes\" print)(\"no\" print)} "
        );
        MultipleTokensBlock blocks = Parser.parse(tokens);
        Interpreter.execute(blocks);
        System.out.println(DataStack.getInstance());
    }
}