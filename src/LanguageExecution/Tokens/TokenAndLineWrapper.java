package LanguageExecution.Tokens;

public record TokenAndLineWrapper(Token token, String symbol, Integer line) {


    @Override
    public String toString() {
        return "\"" + symbol + "\" in line " + line;
    }
}
