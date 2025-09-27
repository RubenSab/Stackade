package Environment.LanguageObjects.Primitives;

public class BooleanPrimitive extends Primitive<Boolean> {

    public BooleanPrimitive(Boolean value) {
        super(value);
    }

    public BooleanPrimitive not() { return new BooleanPrimitive(!getValue()); }
    public BooleanPrimitive and(BooleanPrimitive o) { return new BooleanPrimitive(getValue() && o.getValue()); }
    public BooleanPrimitive or(BooleanPrimitive o) { return new BooleanPrimitive(getValue() || o.getValue()); }
    public BooleanPrimitive xor(BooleanPrimitive o) { return new BooleanPrimitive(getValue() ^ o.getValue()); }

    @Override
    public Boolean getValue() {
        return super.getValue();
    }

    @Override
    public String toString() {
        return "boolean: " + getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BooleanPrimitive)) {
            return false;
        }
        return getValue().equals(((BooleanPrimitive) o).getValue());
    }

    @Override
    public String represent() {
        return getValue().toString();
    }

    @Override
    public String typeName() {
        return "bool";
    }
}

