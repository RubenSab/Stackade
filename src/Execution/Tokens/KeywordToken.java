package Execution.Tokens;

public enum KeywordToken implements Token {
    TRUE, FALSE, // Booleans
    SELF,  // Self references
    DUP, POP, SWAP, // Stack
    DEL, DECLARE_NUM, DECLARE_BOOL, DECLARE_STR, DECLARE_LIST, DECLARE_UNEXECUTED_SEQUENCE, DECLARE_REFERENCE, // Declarations in Namespaces
    TO_NUM, TO_BOOL, TO_STR, // Casting
    CONCATENATE, RESOLVE_VARIABLE_IN_STR, // String operations
    RAISE_VAR,
    RESOLVE_REFERENCE_VALUE, // NamespaceReference operations
    ASSIGN, INCR, DECR, INCR1, DECR1, // Assignations in Namespaces
    ADD, SUB, MUL, DIV, MOD, // Num operations
    EQ, NEQ, LT, GT, LEQ, GEQ,
    NOT, AND, OR, XOR, // Boolean operations
    GET, LEN, PUT, REMOVE, CLEAR, CONTAINS, SET, // Lists
    PRINT, INPUT, // I/O
    OPEN_COND, CLOSE_COND, // Brackets (the only Tokens not transformed to Blocks)
    OPEN_BLOCK, CLOSE_BLOCK,
    DEBUG, HALT // Debugging
    }
