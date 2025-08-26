package Environment.Namespaces;

import Environment.LanguageElements.DataElements.Primitives.Null;
import Environment.LanguageElements.LanguageElement;

import java.util.Comparator;
import java.util.LinkedList;

public class Namespaces extends Namespace{
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

    public LanguageElement get(String name) {
        Comparator<Namespace> reverseOrder = (a, b) -> namespaces.indexOf(b) - namespaces.indexOf(a);
        return namespaces.stream()
                .sorted(reverseOrder)
                .map(ns -> ns.get(name))
                .filter(x -> !x.getClass().equals(Null.class))
                .findFirst()
                .orElse(new Null()); // TODO: throw exception
    }


    public void assign(String name, LanguageElement value) {
        namespaces.getLast().assign(name, value);
    }
}
