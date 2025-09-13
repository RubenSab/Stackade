package Interpreter;

import Environment.LanguageElements.LanguageElement;
import Interpreter.Tokens.KeywordTokens.KeywordToken;
import Interpreter.Tokens.NamespaceReferenceToken;
import Interpreter.Tokens.PrimitiveTokens.PrimitiveToken;

import java.util.ArrayList;

// the interpreter receives an ArrayList of Token (NamedToken(s) and PrimitiveToken(s))
public class Interpreter {
    // TODO: make it singleton
    private final ArrayList<InterpretableUnit> interpretableSequence;

    public Interpreter(ArrayList<InterpretableUnit> interpretableSequence) {
        this.interpretableSequence = interpretableSequence;
    }

    public void interpret() throws InvalidTokenException {
        for (InterpretableUnit t : interpretableSequence) {
            LanguageElement interpretedElement = switch (t.token()) {
                case PrimitiveToken<?> primitiveToken -> primitiveToken.getPrimitive();
                case NamespaceReferenceToken namespaceReferenceToken -> namespaceReferenceToken.resolve();
                case KeywordToken keywordToken -> OperationRegistry.get(keywordToken);
                case null, default -> throw new InvalidTokenException("invalid Token");
            };
            interpretedElement.execute(); // all LanguageElements do something when executed
        }
    }

    public class InvalidTokenException extends Exception {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}
