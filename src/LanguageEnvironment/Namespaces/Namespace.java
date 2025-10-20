package LanguageEnvironment.Namespaces;

import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageExecution.Interpreter.ErrorsLogger;
import LanguageExecution.Interpreter.StackadeError;
import LanguageExecution.Tokens.TokenWrapper;

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
            ErrorsLogger.triggerInterpreterError(new TokenWrapper(null, name, null), StackadeError.WRONG_REDEFINITION_TYPE);
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
            ErrorsLogger.triggerInterpreterError(new TokenWrapper(null, name, null), StackadeError.WRONG_ASSIGNATION_TYPE);
        }
    }

    @Override
    public String toString() {
        return namespace.toString();
    }
}
