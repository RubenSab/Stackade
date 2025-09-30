package LanguageExecution.Tokens;

public class NumberToken implements Token {
    private final Double value;

    public NumberToken(String string) {
        this.value = Double.parseDouble(string);
    }

    public double get() {
        return value;
    }

    @Override
    public String toString() {
        return "number token(" + value + ")";
    }
}