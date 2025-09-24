package Execution;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageObjects.UnexecutedSequence;
import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.NamespaceReference;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;
import Environment.LanguageObjects.Primitives.NumberPrimitive;
import Environment.LanguageObjects.Primitives.Primitive;
import Environment.LanguageObjects.Primitives.StringPrimitive;
import Environment.Namespaces.Namespaces;
import Execution.Tokens.*;

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
                switch (keywordToken) { // TODO: add lists and list operations, add input, add BLOCK

                    // Booleans
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));

                    // SELF
                    // One of the only two cases where nothing is pushed to the stack
                    case SELF -> ConditionalContextsStack.getInstance().executeTop();

                    // Stack
                    case DUP -> stack.push(stack.peek());
                    case POP -> stack.pop();
                    case SWAP -> stack.swap();

                    // Declarations in Namespaces
                    case DEL -> namespaces.delete(((StringPrimitive) stack.pop().resolve()).getValue());
                    case DECLARE_NUM -> declarationFunction(NumberPrimitive.class);
                    case DECLARE_BOOL -> declarationFunction(BooleanPrimitive.class);
                    case DECLARE_STR -> declarationFunction(StringPrimitive.class);
                    case DECLARE_UNEXECUTED_SEQUENCE -> declarationFunction(UnexecutedSequence.class);
                    case DECLARE_REFERENCE -> declarationFunction(NamespaceReference.class);
                    case GET_REFERENCE_VALUE -> stack.push(namespaces.get(((NamespaceReference) stack.pop()).getName()));

                    // Assignations in Namespaces
                    case ASSIGN -> {
                        LanguageObject value = stack.pop();
                        String name = ((NamespaceReference) stack.pop()).getName();
                        namespaces.assign(name, value);
                    }
                    case INCR -> numericMutationFunction(NumberPrimitive::add);
                    case DECR -> numericMutationFunction(NumberPrimitive::sub);
                    case INCR1 -> numericMutationFunction(1);
                    case DECR1 -> numericMutationFunction(-1);

                    // Num operations
                    case ADD -> numericArgsOperation(NumberPrimitive::add);
                    case SUB -> numericArgsOperation(NumberPrimitive::sub);
                    case MUL -> numericArgsOperation(NumberPrimitive::mul);
                    case DIV -> numericArgsOperation(NumberPrimitive::div);
                    case MOD -> numericArgsOperation(NumberPrimitive::mod);
                    case EQ  -> numericArgsOperation(NumberPrimitive::eq);
                    case NEQ -> numericArgsOperation(NumberPrimitive::neq);
                    case LT  -> numericArgsOperation(NumberPrimitive::lt);
                    case GT  -> numericArgsOperation(NumberPrimitive::gt);
                    case LEQ -> numericArgsOperation(NumberPrimitive::leq);
                    case GEQ -> numericArgsOperation(NumberPrimitive::geq);

                    // Boolean operations
                    case NOT -> stack.push(((BooleanPrimitive) stack.pop().resolve()).not());
                    case AND -> booleanArgsOperation(BooleanPrimitive::and);
                    case OR -> booleanArgsOperation(BooleanPrimitive::or);
                    case XOR -> booleanArgsOperation(BooleanPrimitive::xor);

                    // I/O
                    case PRINT -> System.out.print((stack.pop().resolve()).represent());

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
    private static <T extends LanguageObject> void declarationFunction(Class<T> classOfVariable) {
        T value = classOfVariable.cast(stack.pop()); // not doing stack.pop().resolve() allows declaring pointer variables (NamespaceReference)
        String name = ((StringPrimitive) stack.pop()).getValue();
        namespaces.define(name, value);

        if (classOfVariable.equals(UnexecutedSequence.class)) {
            ((UnexecutedSequence) value).setName(name);
        }
    }

    private static void numericMutationFunction(BiFunction<NumberPrimitive, NumberPrimitive, NumberPrimitive> biFunction) {
        NumberPrimitive increment = (NumberPrimitive) stack.pop().resolve();
        String name = ((NamespaceReference) stack.pop()).getName();
        NumberPrimitive oldValue = ((NumberPrimitive) namespaces.get(name));
        NumberPrimitive newValue = biFunction.apply(oldValue, increment);
        namespaces.assign(name, newValue);
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
