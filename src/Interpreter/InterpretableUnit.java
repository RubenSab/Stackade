package Interpreter;

import Interpreter.Tokens.Token;

public record InterpretableUnit(Token token, int lineNumber) {}
