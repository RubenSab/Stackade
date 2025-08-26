package Namespaces;

import LanguageElements.LanguageElement;

import java.util.HashMap;

public class Namespace {
    private final HashMap<String, LanguageElement> namespace;

    protected Namespace() {
         namespace = new HashMap<>();
    }

    protected void define(String name, LanguageElement entity) {
        if (!namespace.containsKey(name)) {
            namespace.put(name, entity);
        }
    }

    protected void delete(String name) {
        namespace.remove(name);
    }

    protected LanguageElement get(String name) {
        return namespace.get(name); // UnresolvedTokenException is handled before reaching namespace
    }

    protected void assign(String name, LanguageElement value) {
        if (value.getClass() == get(name).getClass()) {
            namespace.replace(name, value);
        }
    }

    // TODO: apply function
}
