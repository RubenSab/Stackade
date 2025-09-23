package Environment.LanguageObjects.Primitives;

public class StringPrimitive extends Primitive<String> {
    public StringPrimitive(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public String toString() {
        return "string: " + getValue();
    }

    @Override
    public String represent() {
        return super.getValue()
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\b", "\b")
                .replace("\\f", "f");
    }
}
