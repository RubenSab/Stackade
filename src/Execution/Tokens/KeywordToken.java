package Execution.Tokens;

public enum KeywordToken implements Token {
    TRUE, FALSE, SELF,
    DUP, POP, // stack
    DEL, NUM, STR, LIST, BLOCK, // namespace
    ASSIGN, INCR, DECR, INCR1, DECR1, // assignation
    ADD, SUB, MUL, DIV, MOD, // num operations
    EQ, NEQ, LT, GT, LEQ, GEQ,
    NOT, AND, OR, XOR, // bool operations
    GET, SIZE, PUT, REMOVE, CLEAR, CONTAINS, SET, // array
    OPEN_COND, OPEN_BLOCK, CLOSE_COND, CLOSE_BLOCK; // brackets
}
