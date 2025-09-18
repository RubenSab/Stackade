package Environment.LanguageObjects.Primitives;

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

    public BooleanPrimitive eq(NumberPrimitive o) {
        return new BooleanPrimitive(getValue().equals(o.getValue()));
    }

    public BooleanPrimitive neq(NumberPrimitive o) {
        return new BooleanPrimitive(!getValue().equals(o.getValue()));
    }

    public BooleanPrimitive lt(NumberPrimitive o) {
        return new BooleanPrimitive(getValue() < o.getValue());
    }

    public BooleanPrimitive gt(NumberPrimitive o) {
        return new BooleanPrimitive(getValue() > o.getValue());
    }

    public BooleanPrimitive leq(NumberPrimitive o) {
        return new BooleanPrimitive(getValue() <= o.getValue());
    }

    public BooleanPrimitive geq(NumberPrimitive o) {
        return new BooleanPrimitive(getValue() >= o.getValue());
    }

    public NumberPrimitive floor() {
        return new NumberPrimitive(Math.floor(getValue()));
    }

    public NumberPrimitive ceil() {
        return new NumberPrimitive(Math.ceil(getValue()));
    }

    public NumberPrimitive round() {
        return new NumberPrimitive((double) Math.round(getValue()));
    }

    public int intValue() {
        return getValue().intValue();
    }

    
    @Override
    public String toString() {
        if (getValue() - intValue() == 0) {
            return String.valueOf(intValue());
        }
        return super.toString();
    }
}

