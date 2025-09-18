package Environment.LanguageObjects.DataStructures;

import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;
import Environment.LanguageObjects.Primitives.NumberPrimitive;

import java.util.ArrayList;

public class ListStructure extends DataStructure{
    private final ArrayList<LanguageObject> structure = new ArrayList<>();

    @Override
    public NumberPrimitive size() {
        return new NumberPrimitive(structure.size());
    }

    @Override
    public void put(LanguageObject element) {
        structure.add(element);
    }

    @Override
    public boolean remove(LanguageObject element) {
        return structure.remove(element);
    }

    @Override
    public void clear() {
        structure.clear();
    }

    @Override
    public BooleanPrimitive contains(LanguageObject element) {
        return new BooleanPrimitive(structure.contains(element));
    }

    public LanguageObject get(NumberPrimitive index) {
        int idx = index.getValue().intValue();
        if (idx < 0 || idx >= structure.size()) {
            return null; // TODO: maybe throwing an exception is better
        }
        return structure.get(idx);
    }

    public void set(NumberPrimitive index, LanguageObject value) {
        int idx = index.getValue().intValue();

        if (idx < 0 || idx >= structure.size()) {
            // TODO: throw exception
        } else {
            structure.set(idx, value);
        }
    }

}
