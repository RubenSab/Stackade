package LanguageElements.FunctionElements;

import Datastack.DataStack;
import LanguageElements.DataElements.DataElement;
import LanguageElements.LanguageElement;

import java.util.function.Function;

public class StackUnaryFunction extends LanguageElement {
    Function<DataElement, DataElement> function;

    public StackUnaryFunction(Function<DataElement, DataElement> function) {
        this.function = function;
    }

    @Override
    public void execute() {
        try {
            DataElement op = DataStack.getInstance().pop();
            DataStack.getInstance().push(function.apply(op));
        } catch (DataStack.EmptyPopException e) {
            System.out.println("Not enough elements on stack for operation: " + e.getMessage());
            // TODO: halt
        }
    }
}
