package Environment.LanguageObjects;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o==null || getClass()!=o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(getContent(), box.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getContent());
    }
}
