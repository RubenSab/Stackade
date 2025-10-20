import LanguageExecution.Runner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: stackade <program name>");
            System.exit(2);
        }
        try {
            Runner.getInstance().run(args[0], true);
        } catch (IOException e) {
            System.out.println("File " + args[0] + " not found");
        }
    }
}