package LanguageEnvironment.LanguageObjects.Primitives;

import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;

public class NumberPrimitive extends Primitive<Double> {

    public NumberPrimitive(Number value) {
        super((double) value);
    }

    public static NumberPrimitive parseNumber(String string) {
        try {
            return new NumberPrimitive(Double.parseDouble(string));
        } catch (NumberFormatException e) {
            ErrorsLogger.triggerInterpreterError(StackadeError.WRONG_OPERANDS_TYPE);
            return null;
        }
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

    public StringPrimitive toStringPrimitive() {
        return new StringPrimitive(represent());
    }

    @Override
    public String toString() {
        return "number: " + getValue();
    }

    @Override
    public String represent() {
        if (getValue() - intValue()==0) {
            return String.valueOf(intValue());
        }
        return super.toString();
    }

    @Override
    public String typeName() {
        return "num";
    }
}

