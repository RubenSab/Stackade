package LanguageExecution.Tokens;

public record TokenWrapper(Token token, String symbol, Integer line) {


    @Override
    public String toString() {
        if (line != null) {
            return "\"" + symbol + "\" in line " + line;
        } else {
            return symbol;
        }
    }
}
