package Namespaces;

import LanguageElements.DataElements.Primitives.Null;
import LanguageElements.LanguageElement;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

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
        return namespace.getOrDefault(name, new Null());
    }

    protected void assign(String name, LanguageElement value) {
        LanguageElement oldValue = namespace.get(name);
        if (oldValue != null && oldValue.getClass().equals(value.getClass())) {
            namespace.replace(name, value);
        } else {
            // TODO: throw exception
        }
    }
}
