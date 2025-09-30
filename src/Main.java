import LanguageExecution.Runner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: stackade <program name>");
            System.exit(2);
        }
        Runner.getInstance().run(args[0], true);
    }
}