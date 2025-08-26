package LanguageElements.DataElements.Primitives;

public class NumberPrimitive extends Primitive<Number> {
    public NumberPrimitive(String repr) {
        super(Double.valueOf(repr));
    }

    public NumberPrimitive(Number value) {
        super(value);
    }
}

