import LanguageElements.DataElements.Null;
import LanguageElements.LanguageElement;

import java.util.HashMap;

public class Namespace {
    private static final Null NULL = new Null();
    private final HashMap<String, LanguageElement> namespace = new HashMap<>();

    public void delete(String name) {
        namespace.remove(name);
    }

    public void define(String name, LanguageElement entity) {
        if (!namespace.containsKey(name)) {
            namespace.put(name, entity);
        }
    }

    public LanguageElement get(String name) {
        return namespace.get(name); // UnresolvedTokenException is handled before reaching namespace
    }

    public void assign(String name, LanguageElement value) {
        if (value.getClass() == get(name).getClass()) {
            namespace.replace(name, value);
        }
    }
}
