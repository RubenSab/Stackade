package Parsing;

import LanguageElements.DataElements.Primitives.BooleanPrimitive;
import LanguageElements.DataElements.Primitives.Null;
import LanguageElements.DataElements.Primitives.NumberPrimitive;
import LanguageElements.FunctionElements.StackBiFunction;
import LanguageElements.FunctionElements.StackUnaryFunction;
import LanguageElements.LanguageElement;

import java.util.HashMap;
import java.util.Map;

public class OperationRegistry {
    private static final Map<Token, LanguageElement> operations = new HashMap<>();

    static {
        // Binary functions
        operations.put(StackFunctionToken.ADD,
                new StackBiFunction((a, b) ->
                        new NumberPrimitive(
                                String.valueOf(((NumberPrimitive)a).getValue().doubleValue() +
                                        ((NumberPrimitive)b).getValue().doubleValue())
                        )
                ));

        operations.put(StackFunctionToken.SUB,
                new StackBiFunction((a, b) ->
                        new NumberPrimitive(
                                String.valueOf(((NumberPrimitive)a).getValue().doubleValue() -
                                        ((NumberPrimitive)b).getValue().doubleValue())
                        )
                ));

        // Unary function
        operations.put(StackFunctionToken.NEG,
                new StackUnaryFunction(a ->
                        new NumberPrimitive(
                                String.valueOf(-((NumberPrimitive)a).getValue().doubleValue())
                        )
                ));

        operations.put(StackFunctionToken.NOT,
                new StackUnaryFunction(a ->
                        new BooleanPrimitive(
                                String.valueOf(!((BooleanPrimitive)a).getValue())
                        )
                ));

        // Constants (DataElement executes by pushing itself)
        operations.put(ConstantToken.TRUE, new BooleanPrimitive("true"));
        operations.put(ConstantToken.FALSE, new BooleanPrimitive("false"));
        operations.put(ConstantToken.NULL, new Null());
    }

    public static LanguageElement get(Token token) {
        return operations.get(token);
    }
}

