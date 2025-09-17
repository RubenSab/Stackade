package Execution.Tokens;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageElements.DataElements.DataElement;
import Environment.LanguageElements.DataElements.Primitives.BooleanPrimitive;
import Environment.LanguageElements.DataElements.Primitives.NumberPrimitive;
import Environment.LanguageElements.DataElements.Primitives.StringPrimitive;
import Environment.LanguageElements.LanguageElement;
import Environment.Namespaces.Namespaces;

import java.util.function.BiFunction;

public class OperationRegistry {
    private static final DataStack stack = DataStack.getInstance();
    private static final Namespaces namespaces = Namespaces.getInstance();

    public static void execute(Token token) {
        switch (token) {
            case NumberToken numberToken ->  // is a primitive token
                    stack.push(new NumberPrimitive(numberToken.get()));
            case StringToken stringToken ->
                    stack.push(new StringPrimitive(stringToken.get()));
            case NamespaceToken namespaceToken ->
                    stack.push((DataElement) namespaceToken.resolve());
            case KeywordToken keywordToken -> {
                switch (keywordToken) { // TODO: add list
                    // Booleans
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));
                    // SELF
                    case SELF -> ConditionalContextsStack.getInstance().executeTop();
                    // Stack
                    case DUP -> stack.push(stack.peek());
                    case POP -> stack.pop();
                    // Declarations in Namespaces
                    case DEL -> namespaces.delete(((StringPrimitive) stack.pop()).getValue());
                    case NUM -> {
                        NumberPrimitive value = (NumberPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, value);
                    }
                    case STR -> {
                        StringPrimitive value = (StringPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, value);
                    }
                    // Assignations in Namespaces
                    case ASSIGN -> {
                        LanguageElement value = stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.assign(name, value);
                    }
                    case INCR -> {
                        NumberPrimitive increment = (NumberPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        NumberPrimitive oldValue = (NumberPrimitive) namespaces.get(name);
                        namespaces.assign(name, new NumberPrimitive(
                            oldValue.getValue() + increment.getValue()
                        ));
                    }
                    case DECR -> {
                        NumberPrimitive increment = (NumberPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        NumberPrimitive oldValue = (NumberPrimitive) namespaces.get(name);
                        namespaces.assign(name, new NumberPrimitive(
                                oldValue.getValue() - increment.getValue()
                        ));
                    }
                    case INCR1 -> {
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        NumberPrimitive oldValue = (NumberPrimitive) namespaces.get(name);
                        namespaces.assign(name, new NumberPrimitive(
                                oldValue.getValue() + 1
                        ));
                    }
                    case DECR1 -> {
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        NumberPrimitive oldValue = (NumberPrimitive) namespaces.get(name);
                        namespaces.assign(name, new NumberPrimitive(
                                oldValue.getValue() - 1
                        ));
                    }
                    case ADD -> stack.push(((NumberPrimitive) stack.pop()).add((NumberPrimitive) stack.pop()));
                    case SUB -> {
                        NumberPrimitive op1 = (NumberPrimitive) stack.pop();
                        NumberPrimitive op2 = (NumberPrimitive) stack.pop();
                        stack.push(op1.sub(op2));
                    }
                    case MUL -> stack.push(((NumberPrimitive) stack.pop()).mul((NumberPrimitive) stack.pop()));
                    case DIV -> {
                        NumberPrimitive op1 = (NumberPrimitive) stack.pop();
                        NumberPrimitive op2 = (NumberPrimitive) stack.pop();
                        stack.push(op1.div(op2));
                    }
                    case MOD -> {
                        NumberPrimitive op1 = (NumberPrimitive) stack.pop();
                        NumberPrimitive op2 = (NumberPrimitive) stack.pop();
                        stack.push(op1.mod(op2));
                    }

                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }
    }

}
