package Execution;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageObjects.Box;
import Environment.LanguageObjects.UnexecutedSequence;
import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.Primitives.NamespaceReference;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;
import Environment.LanguageObjects.Primitives.NumberPrimitive;
import Environment.LanguageObjects.Primitives.Primitive;
import Environment.LanguageObjects.Primitives.StringPrimitive;
import Environment.Namespaces.Namespaces;
import Execution.Tokens.*;

import java.io.Console;
import java.util.function.BiFunction;

public class OperationRegistry {
    private static final DataStack stack = DataStack.getInstance();
    private static final Namespaces namespaces = Namespaces.getInstance();

    public static void executeToken(Token token) {
        switch (token) {
            case NumberToken numberToken -> stack.push(new NumberPrimitive(numberToken.get()));
            case StringToken stringToken -> stack.push(new StringPrimitive(stringToken.get()));
            case NamespaceToken namespaceToken -> {
                    LanguageObject invoked = namespaceToken.resolve();
                    if (invoked instanceof UnexecutedSequence) {
                        // One of the only two cases where nothing is pushed to the stack
                        ((UnexecutedSequence) invoked).run();
                    } else {
                        stack.push(new NamespaceReference(namespaceToken.getName().getValue()));
                    }
            }
            case KeywordToken keywordToken -> {
                switch (keywordToken) { // TODO: add string character extraction

                    // Booleans
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));

                    // SELF
                    // One of the only two cases where nothing is pushed to the stack
                    case SELF -> ConditionalContextsStack.getInstance().executeTop();

                    // Stack operations
                    case DUP -> stack.push(stack.peek());
                    case POP -> stack.pop();
                    case SWAP -> stack.swap();
                    case ROT -> {
                        LanguageObject a = stack.pop();
                        LanguageObject b = stack.pop();
                        LanguageObject c = stack.pop();
                        stack.push(a);
                        stack.push(b);
                        stack.push(c);
                    }
                    case EQ  -> stack.push(new BooleanPrimitive(stack.pop().equals(stack.pop())));

                    // Numeric args operations
                    // with Number return
                    case ADD -> numericArgsOperation(NumberPrimitive::add);
                    case SUB -> numericArgsOperation(NumberPrimitive::sub);
                    case MUL -> numericArgsOperation(NumberPrimitive::mul);
                    case DIV -> numericArgsOperation(NumberPrimitive::div);
                    case MOD -> numericArgsOperation(NumberPrimitive::mod);
                    // with Boolean return
                    case NEQ -> numericArgsOperation(NumberPrimitive::neq);
                    case LT  -> numericArgsOperation(NumberPrimitive::lt);
                    case GT  -> numericArgsOperation(NumberPrimitive::gt);
                    case LEQ -> numericArgsOperation(NumberPrimitive::leq);
                    case GEQ -> numericArgsOperation(NumberPrimitive::geq);

                    // Namespaces operations
                    case EXISTS -> stack.push(new BooleanPrimitive(
                            namespaces.contains(((StringPrimitive) stack.pop()).getValue())
                    ));
                    case REF_GET -> stack.push(namespaces.get(((NamespaceReference) stack.pop()).getName()));
                    // TODO: remove
                    // Mutations in Namespaces
                    case DEL -> namespaces.delete(((StringPrimitive) stack.pop().resolve()).getValue());
                    case RAISE_NAME -> { namespaces.raise(((StringPrimitive) stack.pop()).getValue());}
                    // Assignations
                    case ASSIGN -> {
                        LanguageObject value = stack.pop();
                        String name = ((NamespaceReference) stack.pop()).getName();
                        namespaces.assign(name, value);
                    }
                    // Definitions
                    case DEFINE_NUM -> definitionFunction(NumberPrimitive.class);
                    case DEFINE_BOOL -> definitionFunction(BooleanPrimitive.class);
                    case DEFINE_STR -> definitionFunction(StringPrimitive.class);
                    case DEFINE_SEQ -> definitionFunction(UnexecutedSequence.class);
                    case DEFINE_REF -> definitionFunction(NamespaceReference.class);
                    case DEFINE_BOX -> {
                        LanguageObject wrappedValue = stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, new Box(wrappedValue));
                    }
                    case UNBOX -> stack.push(((Box) stack.pop()).getContent());
                    case BOX -> stack.push(new Box(stack.pop()));
                    case STR_TO_NUM -> stack.push(((StringPrimitive) stack.pop().resolve()).toNumberPrimitive());
                    case STR_TO_REF -> stack.push(new NamespaceReference(((StringPrimitive) stack.pop()).getValue()));
                    case NUM_TO_STR -> stack.push(((NumberPrimitive) stack.pop().resolve()).toStringPrimitive());
                    case TYPE -> stack.push(new StringPrimitive(stack.pop().typeName()));
                    case STR_CAT -> {
                        String second = ((StringPrimitive) stack.pop().resolve()).getValue();
                        String first = ((StringPrimitive) stack.pop().resolve()).getValue();
                        stack.push(new StringPrimitive(first + second));
                    }
                    case STR_AT -> {
                        int index = ((NumberPrimitive) stack.pop().resolve()).intValue();
                        String string = ((StringPrimitive) stack.pop().resolve()).getValue();
                        stack.push(new StringPrimitive(String.valueOf(string.charAt(index))));
                    }
                    case STR_LEN -> stack.push(new NumberPrimitive((double) (((StringPrimitive) stack.pop().resolve()).getValue().length())));


                    // Boolean operations
                    case NOT -> stack.push(((BooleanPrimitive) stack.pop().resolve()).not());
                    case AND -> booleanArgsOperation(BooleanPrimitive::and);
                    case OR -> booleanArgsOperation(BooleanPrimitive::or);
                    case XOR -> booleanArgsOperation(BooleanPrimitive::xor);

                    // I/O
                    case PRINT -> System.out.print((stack.pop().resolve()).represent());
                    case INPUT -> {
                        Console console = System.console();
                        stack.push(new StringPrimitive(console.readLine()));
                    }
                    // Debugging
                    case DEBUG -> {
                        System.out.println("\ndata stack = " + DataStack.getInstance());
                        System.out.println("namespaces = " + Namespaces.getInstance());
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }
    }

    // Helper functions
    private static <T extends LanguageObject> void definitionFunction(Class<T> classOfVariable) {
        T value = classOfVariable.cast(stack.pop()); // not doing stack.pop().resolve() allows declaring pointer variables (NamespaceReference)
        String name = ((StringPrimitive) stack.pop()).getValue();
        namespaces.define(name, value);

        if (classOfVariable.equals(UnexecutedSequence.class)) {
            ((UnexecutedSequence) value).setName(name);
        }
    }

    private static void numericMutationFunction(int fixedIncrement) {
        String name = ((NamespaceReference) stack.pop()).getName();
        NumberPrimitive oldValue = ((NumberPrimitive) namespaces.get(name));
        namespaces.assign(name, new NumberPrimitive(oldValue.getValue() + fixedIncrement));
    }

    private static void numericArgsOperation(BiFunction<NumberPrimitive, NumberPrimitive, Primitive<?>> biFunction) {
        NumberPrimitive op2 = (NumberPrimitive) stack.pop().resolve();
        NumberPrimitive op1 = (NumberPrimitive) stack.pop().resolve();
        stack.push(biFunction.apply(op1, op2));
    }
    private static void booleanArgsOperation(BiFunction<BooleanPrimitive, BooleanPrimitive, BooleanPrimitive> biFunction) {
        BooleanPrimitive op2 = (BooleanPrimitive) stack.pop().resolve();
        BooleanPrimitive op1 = (BooleanPrimitive) stack.pop().resolve();
        stack.push(biFunction.apply(op1, op2));
    }

    private static void dataStructureOperation() {}



}
