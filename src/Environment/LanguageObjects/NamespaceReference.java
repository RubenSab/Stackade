package Environment.LanguageObjects;

import Environment.Namespaces.Namespaces;

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
}
