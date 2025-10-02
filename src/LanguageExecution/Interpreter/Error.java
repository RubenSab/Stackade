package LanguageExecution.Interpreter;

public enum Error {
    HALTED_BY_OPERATOR("Execution halted by operator"),
    UNDEFINED_VARIABLE("Undefined variable"),
    UNKNOWN_TOKEN("Unknown token"),
    DIVISION_BY_ZERO("Division by zero"),
    FILE_NOT_FOUND("File not found"),
    WRONG_DEFINITION_TYPE("Attempting to define a variable with the wrong type"),
    WRONG_OPERANDS_TYPE("Operand/s type is wrong for this operation"),
    RAISE_VARIABLE_ERROR("Cannot raise variable from the outermost namespace");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
