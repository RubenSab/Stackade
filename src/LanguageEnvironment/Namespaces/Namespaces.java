package LanguageEnvironment.Namespaces;

import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.StringPrimitive;

import java.util.Stack;


public class Namespaces extends Namespace {
    private final static Namespaces INSTANCE = new Namespaces();
    private final Stack<Namespace> namespaces = new Stack<>();

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
            // System.out.println(namespaces.get(i));
            LanguageObject variable = namespaces.get(i).get(name);
            if (variable!=null) {
                return variable;
            }
        }
        throw new UndefinedVariableException(name);
    }

    public void assign(String name, LanguageObject value) {
        namespaces.peek().assign(name, value);
    }

    public void raise(String name) {
        for (int i = namespaces.size() - 1; i >= 0; i--) {
            Namespace current = namespaces.get(i);
            LanguageObject variable = current.get(name);
            if (variable!=null) {
                if (i==0) {
                    throw new IllegalStateException();
                }
                Namespace outer = namespaces.get(i - 1);
                outer.define(name, variable);
                current.delete(name);
                return;
            }
        }
        // Not found in any namespace
        throw new UndefinedVariableException(name);
    }


    @Override
    public String toString() {
        return namespaces.toString();
    }

    public static class UndefinedVariableException extends RuntimeException {
        protected UndefinedVariableException(String variableName) {
            super("Undefined variable: " + variableName);
        }
    }
}
