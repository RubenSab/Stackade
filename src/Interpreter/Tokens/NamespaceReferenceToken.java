package Interpreter.Tokens;

import Environment.LanguageElements.LanguageElement;
import Environment.Namespaces.Namespaces;

public class NamespaceReferenceToken implements Token {
    private final String name;

    public NamespaceReferenceToken(String name) {
        this.name = name;
    }

    public LanguageElement resolve() {
        return Namespaces.getInstance().get(name);
    }
}
