package LanguageElements.DataElements.DataStructures;

import LanguageElements.DataElements.DataElement;
import LanguageElements.DataElements.Primitives.BooleanPrimitive;
import LanguageElements.DataElements.Primitives.Null;
import LanguageElements.DataElements.Primitives.NumberPrimitive;

import java.util.ArrayList;

public class ListStructure extends DataStructure{
    private final ArrayList<DataElement> structure = new ArrayList<>();

    @Override
    public NumberPrimitive size() {
        return new NumberPrimitive(structure.size());
    }

    @Override
    public void add(DataElement element) {
        structure.add(element);
    }

    @Override
    public void remove(DataElement element) {
        structure.remove(element);
    }

    @Override
    public void clear() {
        structure.clear();
    }

    @Override
    public BooleanPrimitive contains(DataElement element) {
        return new BooleanPrimitive(structure.contains(element));
    }
}
