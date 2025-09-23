package Execution;

import Execution.Blocks.MultipleTokensBlock;
import Execution.Tokens.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Runner {
    public static void run(String filePath) throws IOException {
        String source = Files.readString(Paths.get(filePath));
        ArrayList<Token> tokens = Lexer.tokenize(source);
        MultipleTokensBlock blocks = Parser.parse(tokens);
        Interpreter.execute(blocks);
    }
}
