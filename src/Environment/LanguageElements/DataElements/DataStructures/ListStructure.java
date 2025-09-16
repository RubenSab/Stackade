package Environment.LanguageElements.DataElements.DataStructures;

import Environment.LanguageElements.DataElements.DataElement;
import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;

import java.util.ArrayList;

public class ListStructure extends DataStructure{
    private final ArrayList<DataElement> structure = new ArrayList<>();

    @Override
    public NumberPrimitive size() {
        return new NumberPrimitive(structure.size());
    }

    @Override
    public void put(DataElement element) {
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

    public DataElement get(NumberPrimitive index) {
        int idx = index.getValue().intValue();
        if (idx < 0 || idx >= structure.size()) {
            return null; // TODO: maybe throwing an exception is better
        }
        return structure.get(idx);
    }

    public void set(NumberPrimitive index, DataElement value) {
        int idx = index.getValue().intValue();

        if (idx < 0 || idx >= structure.size()) {
            // TODO: throw exception
        } else {
            structure.set(idx, value);
        }
    }

}
