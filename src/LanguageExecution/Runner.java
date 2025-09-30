package LanguageExecution;

import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Blocks.MultipleTokensBlock;
import LanguageExecution.Tokens.TokenAndLineWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final Runner INSTANCE = new Runner();

    public static Runner getInstance() {
        return INSTANCE;
    }

    public void run(String filePath, boolean mainFile) throws IOException {
        List<String> source = Files.readAllLines(Paths.get(filePath));
        List<TokenAndLineWrapper> tokens = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            tokens.addAll(Lexer.tokenize(source.get(i), i));
        }
        MultipleTokensBlock blocks = Parser.parse(tokens);
        if (mainFile) {
            Namespaces.getInstance().pushNamespace();
        }
        Interpreter.execute(blocks);
    }
}
