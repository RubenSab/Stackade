package LanguageExecution.Tokens;

import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.StringPrimitive;
import LanguageEnvironment.Namespaces.Namespaces;

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
        return "namespace token(" + name + ")";
    }
}
