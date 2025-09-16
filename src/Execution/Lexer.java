package Execution;

import Execution.Tokens.KeywordToken;
import Execution.Tokens.NamespaceToken;
import Execution.Tokens.NumberToken;
import Execution.Tokens.StringToken;
import Execution.Tokens.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public static ArrayList<Token> tokenize(String text) {
        // Regular expression to match:
        // 1. Strings in double quotes: ".*?"
        // 2. Numbers (integers or decimals): \d+\.?\d*
        // 3. Single characters: [(){}]
        // 4. Names (letters and/or underscores): [a-zA-Z_]+
        // + others
        String regex = "\"[^\"]*\"|\\d*\\.\\d+|\\d+|\\+\\+|--|\\+=|-=|==|!=|<=|>=|:[a-zA-Z_]+|[+\\-*/%=<>(){}]|[a-zA-Z_]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        ArrayList<Token> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(buildToken(matcher.group()));
        }
        return tokens;
    }

    private static Token buildToken(String representation) {
        switch (representation) {
            case "{" -> { return KeywordToken.OPEN_COND; }
            case "}" -> { return KeywordToken.CLOSE_COND; }
            case "(" -> { return KeywordToken.OPEN_BLOCK; }
            case ")" -> { return KeywordToken.CLOSE_BLOCK; }
            case "dup" -> { return KeywordToken.DUP; }
            case "pop" -> { return KeywordToken.POP; }
            case ":del" -> { return KeywordToken.DEL; }
            case ":num" -> { return KeywordToken.NUM; }
            case ":str" -> { return KeywordToken.STR; }
            case ":list" -> { return KeywordToken.LIST; }
            case ":block" -> { return KeywordToken.BLOCK; }
            case "=" -> { return KeywordToken.ASSIGN; }
            case "+=" -> { return KeywordToken.INCR; }
            case "-=" -> { return KeywordToken.DECR; }
            case "++" -> { return KeywordToken.INCR1; }
            case "--" -> { return KeywordToken.DECR1; }
            case "+" -> { return KeywordToken.ADD; }
            case "-" -> { return KeywordToken.SUB; }
            case "*" -> { return KeywordToken.MUL; }
            case "/" -> { return KeywordToken.DIV; }
            case "%" -> { return KeywordToken.MOD; }
            case "==" -> { return KeywordToken.EQ; }
            case "!=" -> { return KeywordToken.NEQ; }
            case "<" -> { return KeywordToken.LT; }
            case ">" -> { return KeywordToken.GT; }
            case "<=" -> { return KeywordToken.LEQ; }
            case ">=" -> { return KeywordToken.GEQ; }
            case "not" -> { return KeywordToken.NOT; }
            case "and" -> { return KeywordToken.AND; }
            case "or" -> { return KeywordToken.OR; }
            case "xor" -> { return KeywordToken.XOR; }
            case "size" -> { return KeywordToken.SIZE; }
            case "get" -> { return KeywordToken.GET; }
            case "put" -> { return KeywordToken.PUT; }
            case "remove" -> { return KeywordToken.REMOVE; }
            case "clear" -> { return KeywordToken.CLEAR; }
            case "contains" -> { return KeywordToken.CONTAINS; }
            case "set" -> { return KeywordToken.SET; }
            case "TRUE" -> { return KeywordToken.TRUE; }
            case "FALSE" -> { return KeywordToken.FALSE; }
            default -> {
                if (representation.startsWith("\"") && representation.endsWith("\"")) {
                    return new StringToken(representation);
                } else if (isParsableAsDouble(representation)) {
                    return new NumberToken(representation);
                } else if (representation.matches("[a-zA-Z_]+")) {
                    return new NamespaceToken(representation);
                } else {
                    throw new IllegalArgumentException("Unknown token: " + representation);
                }
            }
        }
    }

    private static boolean isParsableAsDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
