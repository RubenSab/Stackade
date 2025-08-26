package LanguageElements.DataElements.DataStructures;

import LanguageElements.DataElements.DataElement;
import LanguageElements.DataElements.Primitives.Null;

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
}