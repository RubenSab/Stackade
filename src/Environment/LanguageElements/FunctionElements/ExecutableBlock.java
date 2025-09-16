package Environment.LanguageElements.FunctionElements;

import Environment.LanguageElements.LanguageElement;

import java.util.ArrayList;

public class ExecutableBlock extends LanguageElement {

    private final ArrayList<LanguageElement> elements;

    public ExecutableBlock(ArrayList<LanguageElement> elements) {
        this.elements = elements;
    }

    @Override
    public void execute() {
        for (LanguageElement e : elements) {
            try {
                e.execute();
            } catch (Exception ex) {
                System.out.println("Error executing element: " + ex.getMessage());
            }
        }
    }
}
