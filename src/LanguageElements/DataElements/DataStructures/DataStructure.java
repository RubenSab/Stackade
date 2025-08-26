package LanguageElements.DataElements.DataStructures;

import LanguageElements.DataElements.DataElement;
import LanguageElements.DataElements.Primitives.Null;
import LanguageElements.DataElements.Primitives.NumberPrimitive;

public abstract class DataStructure extends DataElement {
    private final DataElement structure;

    public DataStructure() {
        this.structure = new Null();  // default to null
    }

    public DataStructure(DataStructure structure) {
        this.structure = structure;  // allow setting
    }

    public DataElement getStructure() {
        return structure;
    }

    public abstract NumberPrimitive size();
    public abstract DataElement add();
    public abstract DataElement remove();
    public abstract DataElement clear();
    public abstract DataElement contains();
}