package Environment.LanguageObjects.DataStructures;

import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;
import Environment.LanguageObjects.Primitives.NumberPrimitive;

public abstract class DataStructure extends LanguageObject {

    public abstract NumberPrimitive size();
    public abstract void put(LanguageObject element);
    public abstract boolean remove(LanguageObject element);
    public abstract void clear();
    public abstract BooleanPrimitive contains(LanguageObject element);
}