package LanguageElements.DataElements.Primitives;

public class BooleanPrimitive extends Primitive<Boolean> {
    public BooleanPrimitive(String repr) {
        super(Boolean.valueOf(repr));
    }

    public BooleanPrimitive(Boolean value) {
        super(value);
    }
}

