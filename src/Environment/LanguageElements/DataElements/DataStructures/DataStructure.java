package Environment.LanguageElements.DataElements.DataStructures;

import Environment.LanguageElements.DataElements.DataElement;
import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;

public abstract class DataStructure extends DataElement {

    public abstract NumberPrimitive size();
    public abstract void add(DataElement element);
    public abstract void remove(DataElement element);
    public abstract void clear();
    public abstract BooleanPrimitive contains(DataElement element);
}