package Environment.Namespaces;
import Environment.LanguageObjects.LanguageObject;

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
        namespaces.removeLast();
    }

    public void define(String name, LanguageObject entity) {
        namespaces.getLast().define(name, entity);
    }

    public void delete(String name) {
        namespaces.getLast().delete(name);
    }

    public LanguageObject get(String name) {
        for (int i = namespaces.size() - 1; i >= 0; i--) {
            LanguageObject result = namespaces.get(i).get(name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public void assign(String name, LanguageObject value) {
        namespaces.getLast().assign(name, value);
    }

    @Override
    public String toString() {
        return namespaces.toString();
    }
}
