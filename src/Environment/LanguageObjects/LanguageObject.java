package Environment.LanguageObjects;

public abstract class LanguageObject {
    public LanguageObject resolve() {
        if (this instanceof NamespaceReference) {
            return this.resolve();
        } else {
            return this;
        }
    }
}
