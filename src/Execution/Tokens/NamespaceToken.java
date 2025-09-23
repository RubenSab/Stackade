package Execution.Tokens;

import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.Primitives.StringPrimitive;
import Environment.Namespaces.Namespaces;

public class NamespaceToken implements Token {
    private final String name;

    public NamespaceToken(String name) {
        this.name = name;
    }

    public LanguageObject resolve() {
        return Namespaces.getInstance().get(name);
    }

    public StringPrimitive getName() {
        return new StringPrimitive(name);
    }

    @Override
    public String toString() {
        return "namespace token: " + name;
    }
}
