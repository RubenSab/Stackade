package Execution.Tokens;

import Environment.LanguageObjects.LanguageObject;
import Environment.Namespaces.Namespaces;

public class NamespaceToken implements Token {
    private final String name;

    public NamespaceToken(String name) {
        this.name = name;
    }

    public LanguageObject resolve() {
        return Namespaces.getInstance().get(name);
    }

    @Override
    public String toString() {
        return "<" + name + ">";
    }
}
