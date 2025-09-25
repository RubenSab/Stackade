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

    public static ArrayList<Token> tokenize(String source) {
        // Regular expression to match:
        // 1. Strings in double quotes: ".*?"
        // 2. Numbers (integers or decimals): \d+\.?\d*
        // 3. Single characters: [(){}]
        // 4. Names (letters and/or underscores): [a-zA-Z_]+
        // + others
        String sourceNoComments = source.replaceAll("#.*", "");
        String regex = "\"[^\"]*\"|\\d*\\.\\d+|\\d+|\\+\\+|--|\\+=|-=|==|!=|<=|>=|:[a-zA-Z_]+|[+\\-*/%=<>(){}\\[\\]]|[a-zA-Z_]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceNoComments);

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
            case "swap" -> { return KeywordToken.SWAP; }
            case ":del" -> { return KeywordToken.DEL; }
            case ":num" -> { return KeywordToken.DECLARE_NUM; }
            case ":bool" -> { return KeywordToken.DECLARE_BOOL; }
            case ":str" -> { return KeywordToken.DECLARE_STR; }
            case ":list" -> { return KeywordToken.DECLARE_LIST; }
            case ":seq" -> { return KeywordToken.DECLARE_UNEXECUTED_SEQUENCE; }
            case ":ref" -> { return KeywordToken.DECLARE_REFERENCE; }
            case ":box" -> { return KeywordToken.DECLARE_BOX; }
            case ":raiseName" -> { return KeywordToken.RAISE_VAR; }
            case "exists" -> { return KeywordToken.CHECK_DEFINED; }
            case "unbox" -> { return KeywordToken.GET_BOX_CONTENT; }
            case "box" -> { return KeywordToken.BOX; }
            case "cat" -> { return KeywordToken.CONCATENATE; }
            case "strToNum" -> { return KeywordToken.STR_TO_NUM; }
            case "numToStr" -> { return KeywordToken.NUM_TO_STR; }
            case "strGet" -> { return KeywordToken.RESOLVE_VARIABLE_IN_STR; }
            case "refGet" -> { return KeywordToken.RESOLVE_REFERENCE_VALUE; }
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
            case "true" -> { return KeywordToken.TRUE; }
            case "false" -> { return KeywordToken.FALSE; }
            case "self" -> { return KeywordToken.SELF; }
            case "print" -> { return KeywordToken.PRINT; }
            case "input" -> { return KeywordToken.INPUT; }
            case "debug" -> { return KeywordToken.DEBUG; }
            case "halt" -> { return KeywordToken.HALT; }
            default -> {
                if (representation.startsWith("\"") && representation.endsWith("\"")) {
                    // build new token from substring from index 1 to -1 to remove double quotes
                    return new StringToken(representation.substring(1, representation.length()-1));
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
