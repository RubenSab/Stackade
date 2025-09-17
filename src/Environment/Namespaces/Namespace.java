package Environment.Namespaces;
import Environment.LanguageElements.LanguageElement;

import java.util.HashMap;

public class Namespace {
    private final HashMap<String, LanguageElement> namespace;

    public Namespace() {
         namespace = new HashMap<>();
    }

    public void define(String name, LanguageElement entity) {
        if (!namespace.containsKey(name)) {
            namespace.put(name, entity);
        }
    }

    public void delete(String name) {
        namespace.remove(name);
    }

    public LanguageElement get(String name) {
        return namespace.getOrDefault(name, null);
    }

    public void assign(String name, LanguageElement value) {
        LanguageElement oldValue = namespace.get(name);
        if (oldValue != null && oldValue.getClass().equals(value.getClass())) {
            namespace.replace(name, value);
        } else {
            // TODO: throw exception
        }
    }

    @Override
    public String toString() {
        return namespace.toString();
    }
}
