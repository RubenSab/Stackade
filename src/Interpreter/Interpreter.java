package Interpreter;

import Environment.LanguageElements.LanguageElement;
import Interpreter.Tokens.NamespaceReferenceToken;
import Interpreter.Tokens.PrimitiveToken;
import Interpreter.Tokens.Token;

import java.util.ArrayList;

import static Interpreter.Tokens.StackFunctionToken.ADD;

// the interpreter receives an ArrayList of Token (NamedToken(s) and PrimitiveToken(s))
public class Interpreter {
    // TODO: make it singleton
    private final ArrayList<Token> tokenSequence;

    public Interpreter(ArrayList<Token> tokenSequence) {
        this.tokenSequence = tokenSequence;
    }

    public void interpret() {
        for (Token t : tokenSequence) {
            LanguageElement interpretedElement;
            if (t instanceof PrimitiveToken<?>) {
                interpretedElement = ((PrimitiveToken<?>) t).getPrimitive();
            } else if (t instanceof NamespaceReferenceToken) {
                interpretedElement = ((NamespaceReferenceToken) t).resolve();
            } else {
                interpretedElement = OperationRegistry.get(ADD);
            }
            interpretedElement.execute(); // all LanguageElements do something when executed
        }
    }
}
