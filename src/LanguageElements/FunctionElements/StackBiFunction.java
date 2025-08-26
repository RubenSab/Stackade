package LanguageElements.FunctionElements;

import Datastack.DataStack;
import LanguageElements.DataElements.DataElement;
import LanguageElements.LanguageElement;

import java.util.function.BiFunction;

public class StackBiFunction extends LanguageElement {
    BiFunction<DataElement, DataElement, DataElement> biFunction;

    public StackBiFunction(BiFunction<DataElement, DataElement, DataElement> biFunction) {
        this.biFunction = biFunction;
    }

    @Override
    public void execute() {
        try {
            DataElement op2 = DataStack.getInstance().pop();
            DataElement op1 = DataStack.getInstance().pop();
            DataStack.getInstance().push(biFunction.apply(op1, op2));
        } catch (DataStack.EmptyPopException e) {
            System.out.println("Not enough elements on stack for operation: " + e.getMessage());
            // TODO: halt
        }
    }

}
