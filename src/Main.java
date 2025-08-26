import Environment.DataStack;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Interpreter.OperationRegistry;

import static Interpreter.Tokens.StackFunctionToken.ADD;

public class Main {
    public static void main(String[] args) {
        DataStack stack = DataStack.getInstance();

        new NumberPrimitive("2").execute();
        new NumberPrimitive("3").execute();

        OperationRegistry.get(ADD).execute();

        System.out.println(stack);
    }
}