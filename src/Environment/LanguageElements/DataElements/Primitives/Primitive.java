package Environment.LanguageElements.DataElements.Primitives;

import Environment.LanguageElements.DataElements.DataElement;
import Interpreter.Tokens.Token;

public abstract class Primitive<T> extends DataElement {
    private final T value;

    public Primitive(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
