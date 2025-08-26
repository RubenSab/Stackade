package Environment.LanguageElements.DataElements;

import Environment.DataStack;
import Environment.LanguageElements.LanguageElement;

public abstract class DataElement extends LanguageElement {

    public void execute() {
        DataStack.getInstance().push(this);
    }
}
