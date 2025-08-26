import Datastack.DataStack;
import LanguageElements.DataElements.Primitives.NumberPrimitive;
import Parsing.OperationRegistry;

import static Parsing.StackFunctionToken.ADD;

public class Main {
    public static void main(String[] args) {
        DataStack stack = DataStack.getInstance();

        new NumberPrimitive("2").execute();
        new NumberPrimitive("3").execute();

        OperationRegistry.get(ADD).execute();

        System.out.println(stack);
    }
}