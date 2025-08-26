package LanguageElements.DataElements.DataStructures;

import LanguageElements.DataElements.DataElement;
import LanguageElements.DataElements.Primitives.BooleanPrimitive;
import LanguageElements.DataElements.Primitives.Null;
import LanguageElements.DataElements.Primitives.NumberPrimitive;

public abstract class DataStructure extends DataElement {

    public abstract NumberPrimitive size();
    public abstract void add(DataElement element);
    public abstract void remove(DataElement element);
    public abstract void clear();
    public abstract BooleanPrimitive contains(DataElement element);
}