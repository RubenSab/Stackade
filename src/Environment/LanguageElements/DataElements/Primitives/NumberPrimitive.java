package Environment.LanguageElements.DataElements.Primitives;

public class NumberPrimitive extends Primitive<Double> {

    public NumberPrimitive(Number value) {
        super((double) value);
    }
    
    public NumberPrimitive add(NumberPrimitive o) {
        return new NumberPrimitive(getValue() + o.getValue());
    }

    public NumberPrimitive sub(NumberPrimitive o) {
        return new NumberPrimitive(getValue() - o.getValue());
    }

    public NumberPrimitive mul(NumberPrimitive o) {
        return new NumberPrimitive(getValue() * o.getValue());
    }

    public NumberPrimitive div(NumberPrimitive o) {
        return new NumberPrimitive(getValue() / o.getValue());
    }

    public NumberPrimitive mod(NumberPrimitive o) {
        return new NumberPrimitive(getValue() % o.getValue());
    }

    public NumberPrimitive floor() {
        return new NumberPrimitive(Math.floor((double) getValue()));
    }

    public NumberPrimitive ceil() {
        return new NumberPrimitive(Math.ceil((double) getValue()));
    }

    public NumberPrimitive round() {
        return new NumberPrimitive((double) Math.round((double) getValue()));
    }

    public int intValue() {
        return getValue().intValue();
    }

    
    @Override
    public String toString() {
        if ((double) getValue() - intValue() == 0) {
            return String.valueOf(intValue());
        }
        return super.toString();
    }
}

