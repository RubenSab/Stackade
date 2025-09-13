package Interpreter.Tokens.PrimitiveTokens;

import Environment.LanguageElements.DataElements.Primitives.Primitive;

public class StringToken extends PrimitiveToken<String> {
    public StringToken(Primitive<String> primitive) {
        super(primitive);
    }
}
