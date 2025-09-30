package LanguageExecution.Tokens;

public class StringToken implements Token {
    private final String string;

    public StringToken(String string) {
        this.string = string;
    }

    public String get() {
        return string;
    }

    @Override
    public String toString() {
        return "string token(" + string + ")";
    }
}
