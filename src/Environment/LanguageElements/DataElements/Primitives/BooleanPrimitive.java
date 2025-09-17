package Environment.LanguageElements.DataElements.Primitives;

public class BooleanPrimitive extends Primitive<Boolean> {

    public BooleanPrimitive(String repr) {
        super(Boolean.valueOf(repr));
    }

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


}

