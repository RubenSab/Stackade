package LanguageExecution.Tokens;

public record TokenAndLineWrapper(Token token, int line) {
    @Override
    public Token token() {
        return token;
    }

    @Override
    public int line() {
        return line;
    }

    @Override
    public String toString() {
        return "token: " + token + " in line " + line;
    }
}
