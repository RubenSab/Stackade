package LanguageExecution.Interpreter;

public enum StackadeError {

    // Parser exceptions
    INVALID_BRACKETING("The source code has missing or misplaced parenthesis"),

    // Interpreter exceptions
    EMPTY_STACK("Attempted to pop from empty Stack"),
    HALTED_BY_OPERATOR("Execution halted by operator"),
    UNDEFINED_VARIABLE("Undefined variable"),
    UNKNOWN_TOKEN("Unknown token"),
    DIVISION_BY_ZERO("Division by zero"),
    FILE_NOT_FOUND("File not found"),
    WRONG_OPERANDS_TYPE("Operand/s type is wrong for this operation"),
    // Namespace,
    WRONG_DEFINITION_TYPE("Attempted to define a variable with the wrong type"),
    WRONG_REDEFINITION_TYPE("Attempted to redefine a variable with the wrong type"),
    WRONG_ASSIGNATION_TYPE("Attempted to assign a value with the wrong type to a variable"),
    RAISE_VARIABLE_ERROR("Cannot raise variable from the outermost namespace");

    private final String message;

    StackadeError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
