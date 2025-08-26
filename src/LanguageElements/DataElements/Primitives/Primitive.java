package LanguageElements.DataElements.Primitives;

import LanguageElements.DataElements.DataElement;

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
