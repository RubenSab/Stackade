import Environment.DataStack;
import Execution.Lexer;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;

public class Main {
    public static void main(String[] args) {
        DataStack stack = DataStack.getInstance();

        new NumberPrimitive("2").execute();
        new NumberPrimitive("3").execute();

        // OperationRegistry.get(ADD).execute();

        System.out.println(stack);

        System.out.println(Lexer.tokenize("{(i 0 :num i < 10) (i dup 1 + = print SELF) (i \"done\\n\" print)}"));
    }
}