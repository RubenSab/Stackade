package Execution.Tokens;

public enum KeywordToken implements Token {
    // Stack operations
    DUP, POP, SWAP, ROT,

    // Numeric args operations
    ADD, SUB, MUL, DIV, MOD, // With Number return
    EQ, NEQ, LT, GT, LEQ, GEQ, // With Boolean return

    // Boolean operations
    NOT, AND, OR, XOR,

    // String operations
    STR_AT, STR_CAT, STR_LEN,

    // Namespaces operations
    EXISTS, REF_GET,
    // Mutations in Namespaces
    ASSIGN, DEL, RAISE_NAME,
    DEFINE_NUM, DEFINE_STR, DEFINE_BOOL, DEFINE_REF, DEFINE_BOX, DEFINE_SEQ,

    // Casting
    TYPE, BOX, UNBOX, STR_TO_NUM, STR_TO_REF, NUM_TO_STR,

    // Self referencing
    SELF,

    // I/O
    PRINT, INPUT,

    // Debugging
    DEBUG, HALT,

    // Other
    TRUE, FALSE, // Booleans
    OPEN_COND, CLOSE_COND, // Brackets (the only Tokens not transformed to Blocks)
    OPEN_BLOCK, CLOSE_BLOCK
}
