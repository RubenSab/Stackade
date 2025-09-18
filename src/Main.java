import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.Namespaces.Namespaces;
import Execution.Blocks.MultipleTokensBlock;
import Execution.Interpreter;
import Execution.Lexer;
import Execution.Parser;
import Execution.Tokens.Token;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = Lexer.tokenize(
                "\"avg\" [+ 2 /] :fblock 2 4 avg 5 6 avg 7 8 avg"
        );
        MultipleTokensBlock blocks = Parser.parse(tokens);
        Interpreter.execute(blocks);
        System.out.println();
        System.out.println("data stack = " + DataStack.getInstance());
        System.out.println("namespace =" + Namespaces.getInstance());
    }
}