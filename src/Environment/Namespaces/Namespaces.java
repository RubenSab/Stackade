package Environment.Namespaces;
import Environment.LanguageElements.LanguageElement;

import java.util.LinkedList;


public class Namespaces extends Namespace{
    private final LinkedList<Namespace> namespaces = new LinkedList<>();
    private final static Namespaces INSTANCE = new Namespaces();

    public static Namespaces getInstance() {
        return INSTANCE;
    }

    public void pushNamespace() {
        namespaces.addLast(new Namespace());
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
        return namespaces.getLast().get(name); // TODO search downwards
    }

    public void assign(String name, LanguageElement value) {
        namespaces.getLast().assign(name, value);
    }

    @Override
    public String toString() {
        return namespaces.toString();
    }
}
