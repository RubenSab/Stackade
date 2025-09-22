package Environment.LanguageObjects;

import Environment.DataStack;

public abstract class LanguageObject {
    public LanguageObject resolve() {
        if (this instanceof NamespaceReference) {
            return ((NamespaceReference) this).resolve();
        } else {
            return this;
        }
    }
}
