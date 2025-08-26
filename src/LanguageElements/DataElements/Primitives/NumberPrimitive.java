package LanguageElements.DataElements.Primitives;

public class NumberPrimitive extends Primitive<Number> {
    public NumberPrimitive(String repr) {
        super(Double.valueOf(repr));
    }

    public NumberPrimitive(Number value) {
        super(value);
    }

    public NumberPrimitive floor() {
        return new NumberPrimitive(Math.floor((double) getValue()));
    }

    public NumberPrimitive ceil() {
        return new NumberPrimitive(Math.ceil((double) getValue()));
    }

    public NumberPrimitive round() {
        return new NumberPrimitive(Math.round((double) getValue()));
    }

    public int intValue() {
        return getValue().intValue();
    }
}

