import Execution.Blocks.MultipleTokensBlock;
import Execution.Interpreter;
import Execution.Lexer;
import Execution.Parser;
import Execution.Tokens.Token;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = Lexer.tokenize(
                "0 {(dup 10 <=)(dup print \" \" print 1 + self)(\"\ndone\" print)}"
        );
        MultipleTokensBlock blocks = Parser.parse(tokens);
        Interpreter.execute(blocks);
        System.out.println();
        // System.out.println(DataStack.getInstance());
        // System.out.println(ConditionalContextsStack.getInstance());
    }
}