package Interpreter;

import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.LanguageElements.DataElements.Primitives.Null;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Environment.LanguageElements.FunctionElements.StackBiFunction;
import Environment.LanguageElements.FunctionElements.StackUnaryFunction;
import Environment.LanguageElements.LanguageElement;
import Interpreter.Tokens.KnownValueToken;
import Interpreter.Tokens.KeywordToken;
import Interpreter.Tokens.StackFunctionToken;

import java.util.HashMap;
import java.util.Map;

public class OperationRegistry {
    private static final Map<KeywordToken, LanguageElement> operations = new HashMap<>();

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
        operations.put(KnownValueToken.TRUE, new BooleanPrimitive("true"));
        operations.put(KnownValueToken.FALSE, new BooleanPrimitive("false"));
        operations.put(KnownValueToken.NULL, new Null());
    }

    public static LanguageElement get(KeywordToken token) {
        return operations.get(token);
    }
}

