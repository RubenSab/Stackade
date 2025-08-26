package Parsing;

public enum StackFunctionToken implements Token{
    DUP, FLUSH,   // stack
    NEG,   // math
    ADD, SUB, MUL, DIV, REM, POW, ROOT,
    IS_EQ, IS_NEQ, LT, GT, LEQ, GEQ,   // comparisons
    NOT, AND, OR, XOR,   // logic
}
