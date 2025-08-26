package LanguageElements.DataElements;

import Datastack.DataStack;
import LanguageElements.LanguageElement;

public abstract class DataElement extends LanguageElement {

    public void execute() {
        DataStack.getInstance().push(this);
    }
}
