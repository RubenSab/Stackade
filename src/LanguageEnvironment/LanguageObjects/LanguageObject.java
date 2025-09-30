package LanguageEnvironment.LanguageObjects;

import LanguageEnvironment.LanguageObjects.Primitives.NamespaceReference;

public abstract class LanguageObject {

    public LanguageObject resolve() {
        if (this instanceof NamespaceReference) {
            return this.resolve();
        } else {
            return this;
        }
    }

    public abstract String represent();

    public abstract String typeName();
}
