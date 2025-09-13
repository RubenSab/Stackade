package Interpreter;

import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.LanguageElements.DataElements.Primitives.Null;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Environment.LanguageElements.FunctionElements.StackBiFunction;
import Environment.LanguageElements.FunctionElements.StackUnaryFunction;
import Environment.LanguageElements.LanguageElement;
import Interpreter.Tokens.KeywordTokens.PriorKnownValueToken;
import Interpreter.Tokens.KeywordTokens.KeywordToken;
import Interpreter.Tokens.KeywordTokens.StackFunctionToken;

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
        operations.put(PriorKnownValueToken.TRUE, new BooleanPrimitive("true"));
        operations.put(PriorKnownValueToken.FALSE, new BooleanPrimitive("false"));
        operations.put(PriorKnownValueToken.NULL, new Null());
    }

    public static LanguageElement get(KeywordToken token) {
        return operations.get(token);
    }
}

