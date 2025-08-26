package Namespaces;

import LanguageElements.LanguageElement;

import java.util.LinkedList;

public class Namespaces {
    private final LinkedList<Namespace> namespaces = new LinkedList<>();
    private final static Namespaces INSTANCE = new Namespaces();

    public static Namespaces getInstance() {
        return INSTANCE;
    }

    public void pushNamespace() {
        namespaces.push(new Namespace());
    }

    public void popNamespace() {
        namespaces.pop();
    }

    public void define(String name, LanguageElement entity) {
        namespaces.getLast().define(name, entity);
    }

    public void delete(String name) {
        namespaces.getLast().delete(name);
    }

    public void assign(String name, LanguageElement value) {
        namespaces.getLast().assign(name, value);
    }
}
