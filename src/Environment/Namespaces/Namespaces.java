package Environment.Namespaces;
import Environment.LanguageObjects.LanguageObject;
import java.util.LinkedList;
import java.util.Stack;


public class Namespaces extends Namespace {
    private final Stack<Namespace> namespaces = new Stack<>();
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

    public void define(String name, LanguageObject entity) {
        namespaces.peek().define(name, entity);
    }

    @Override
    public boolean contains(String name) {
        return namespaces.peek().contains(name);
    }

    public void delete(String name) {
        namespaces.peek().delete(name);
    }

    public LanguageObject get(String name) {
        for (int i = namespaces.size() - 1; i >= 0; i--) {
            LanguageObject variable = namespaces.get(i).get(name);
            if (variable != null) {
                return variable;
            }
        }
        throw new UndefinedVariableException(name);
    }

    public void assign(String name, LanguageObject value) {
        namespaces.peek().assign(name, value);
    }

    public void raise(String name) {
        int i;
        for (i = namespaces.size() - 1; i >= 0; i--) {
            LanguageObject variable = namespaces.get(i).get(name);
            if (variable != null) {
                namespaces.get(i-1).define(name, namespaces.get(i).get(name));
                namespaces.get(i).delete(name);
                break;
            } else {
                throw new UndefinedVariableException(name);
            }
        }
    }

    @Override
    public String toString() {
        return namespaces.toString();
    }

    protected static class UndefinedVariableException extends RuntimeException {
        protected UndefinedVariableException(String variableName) {
            super("Undefined variable: " + variableName);
        }
    }
}
