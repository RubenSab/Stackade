package Environment.LanguageObjects;

public class Box extends LanguageObject {
    private LanguageObject content;

    public Box(LanguageObject content) {
        this.content = content;
    }

    public LanguageObject getContent() {
        return content;
    }

    public void replace(LanguageObject content) {
        this.content = content;
    }

    @Override
    public String represent() {
        return "box(" + content + ")";
    }

    @Override
    public String toString() {
        return "box: " + content;
    }
}
