package Interpreter.Tokens.PrimitiveTokens;

import Environment.LanguageElements.DataElements.Primitives.Primitive;

public abstract class PrimitiveToken<T> implements TokenType {
    final private Primitive<T> primitive;

    public PrimitiveToken(Primitive<T> primitive) {
        this.primitive = primitive;
    }

    public Primitive<T> getPrimitive() {
        return primitive;
    }
}
