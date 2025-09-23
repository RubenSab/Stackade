package Execution;

import Environment.DataStack;
import Environment.Namespaces.Namespaces;
import Execution.Blocks.MultipleTokensBlock;
import Execution.Tokens.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Runner {
    public static void run(String filePath, boolean verbose) throws IOException {
        String source = Files.readString(Paths.get(filePath));

        ArrayList<Token> tokens = Lexer.tokenize(source);
        // System.out.println(tokens);

        MultipleTokensBlock blocks = Parser.parse(tokens);
        // System.out.println(blocks);

        Interpreter.execute(blocks);

        if (verbose) {
            System.out.println("\ndata stack = " + DataStack.getInstance());
            System.out.println("namespaces = " + Namespaces.getInstance());
        }
    }
}
