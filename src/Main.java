import Environment.DataStack;
import Execution.Blocks.MultipleTokensBlock;
import Execution.Interpreter;
import Execution.Lexer;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Execution.Parser;
import Execution.Tokens.Token;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokens = Lexer.tokenize("{(i 0 :num i < 10) (i dup 1 + = print self) (i \"done\\n\" print)}");
        MultipleTokensBlock blocks = Parser.parse(tokens);
        Interpreter.execute(blocks);
        System.out.println(DataStack.getInstance());
    }
}