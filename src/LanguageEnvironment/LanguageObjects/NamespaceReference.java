package LanguageEnvironment.LanguageObjects;

import LanguageEnvironment.Namespaces.Namespaces;

import java.util.Objects;

public class NamespaceReference extends LanguageObject {

    private final String name;

    public NamespaceReference(String name) {
        this.name = name;
    }

    @Override
    public LanguageObject resolve() {
        return Namespaces.getInstance().get(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "reference to: " + name;
    }

    @Override
    public String represent() {
        return "reference(" + name + ")";
    }

    @Override
    public String typeName() {
        return "ref";
    }

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        NamespaceReference that = (NamespaceReference) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
