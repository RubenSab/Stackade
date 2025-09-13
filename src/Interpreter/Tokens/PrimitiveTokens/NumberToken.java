package Interpreter.Tokens.PrimitiveTokens;

import Environment.LanguageElements.DataElements.Primitives.Primitive;

public class NumberToken extends PrimitiveToken<Number> {
    public NumberToken(Primitive<Number> primitive) {
        super(primitive);
    }
}