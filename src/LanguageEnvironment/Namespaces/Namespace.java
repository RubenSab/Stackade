package LanguageEnvironment.Namespaces;

import LanguageEnvironment.LanguageObjects.LanguageObject;

import java.util.HashMap;

public class Namespace {
    private final HashMap<String, LanguageObject> namespace;

    public Namespace() {
        namespace = new HashMap<>();
    }

    public void define(String name, LanguageObject entity) {
        if (!namespace.containsKey(name)) {
            namespace.put(name, entity);
        } else if (namespace.get(name).getClass().equals(entity.getClass())) {
            namespace.replace(name, entity);
        } else {
            // TODO: throw exception (variable already defined with another type)
        }
    }

    public boolean contains(String name) {
        return namespace.containsKey(name);
    }

    public void delete(String name) {
        namespace.remove(name);
    }

    public LanguageObject get(String name) {
        return namespace.getOrDefault(name, null);
    }

    public void assign(String name, LanguageObject value) {
        LanguageObject oldValue = namespace.get(name);
        if (oldValue!=null && (oldValue.getClass().equals(value.getClass()))) {
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
