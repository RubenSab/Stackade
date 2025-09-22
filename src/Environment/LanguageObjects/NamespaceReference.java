package Environment.LanguageObjects;

import Environment.LanguageObjects.Primitives.StringPrimitive;
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

    public StringPrimitive getName() {
        return new StringPrimitive(name);
    }

}
