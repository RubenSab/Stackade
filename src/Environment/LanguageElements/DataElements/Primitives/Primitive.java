package Environment.LanguageElements.DataElements.Primitives;

import Environment.LanguageElements.DataElements.DataElement;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        Primitive<?> primitive = (Primitive<?>) o;
        return Objects.equals(getValue(), primitive.getValue());
    }
}
