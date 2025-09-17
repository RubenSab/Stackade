package Execution.Tokens;

public enum KeywordToken implements Token {
    TRUE, FALSE, // Booleans
    SELF,  // Self references
    DUP, POP, // Stack
    DEL, NUM, STR, LIST, // Declarations in Namespaces
    ASSIGN, INCR, DECR, INCR1, DECR1, // Assignations in Namespaces
    ADD, SUB, MUL, DIV, MOD, // Num operations
    EQ, NEQ, LT, GT, LEQ, GEQ,
    NOT, AND, OR, XOR, // Bool operations
    GET, SIZE, PUT, REMOVE, CLEAR, CONTAINS, SET, // Arrays
    PRINT, INPUT, // I/O
    OPEN_COND, OPEN_BLOCK, CLOSE_COND, CLOSE_BLOCK // Brackets (the only Tokens not transformed to Blocks)
    }
