package LanguageExecution.Interpreter;

import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Box;
import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.NamespaceReference;
import LanguageEnvironment.LanguageObjects.Primitives.*;
import LanguageEnvironment.LanguageObjects.Sequence;
import LanguageEnvironment.Namespaces.Namespaces;
import LanguageExecution.Runner;
import LanguageExecution.Tokens.*;

import java.io.Console;
import java.io.IOException;
import java.util.function.BiFunction;

public class OperationRegistry {
    private static final OperationRegistry INSTANCE = new OperationRegistry();
    private static final DataStack stack = DataStack.getInstance();
    private static final Namespaces namespaces = Namespaces.getInstance();
    private TokenAndLineWrapper tokenWrapper;

    public static OperationRegistry getInstance() {
        return INSTANCE;
    }

    public void executeToken(TokenAndLineWrapper tokenWrapper) {
        this.tokenWrapper = tokenWrapper;

        switch (tokenWrapper.token()) {
            case NumberToken numberToken -> stack.push(new NumberPrimitive(numberToken.get()));
            case StringToken stringToken -> stack.push(new StringPrimitive(stringToken.get()));
            case NamespaceToken namespaceToken -> { // Sequence type variables already handled by the Interpreter
                try {
                    stack.push(new NamespaceReference(namespaceToken.getName().getValue()));
                } catch (Namespaces.UndefinedVariableException e) {
                    ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.UNDEFINED_VARIABLE);
                }

            }
            case KeywordToken keywordToken -> {
                switch (keywordToken) {

                    // Stack operations
                    case DUP -> stack.push(stack.peek(tokenWrapper));
                    case POP -> stack.pop(tokenWrapper);
                    case SWAP -> stack.swap(tokenWrapper);
                    case ROT -> {
                        LanguageObject a = stack.pop(tokenWrapper);
                        LanguageObject b = stack.pop(tokenWrapper);
                        LanguageObject c = stack.pop(tokenWrapper);
                        stack.push(a);
                        stack.push(b);
                        stack.push(c);
                    }
                    case STACK_HEIGHT -> stack.push(new NumberPrimitive(stack.height()));
                    case EQ -> {
                        Primitive<?> op2 = stack.pop(tokenWrapper).resolve().tryCast(Primitive.class, tokenWrapper);
                        Primitive<?> op1 = stack.pop(tokenWrapper).resolve().tryCast(Primitive.class, tokenWrapper);
                        stack.push(new BooleanPrimitive(op1.equals(op2)));
                    }
                    case NEQ -> {
                        Primitive<?> op2 = stack.pop(tokenWrapper).resolve().tryCast(Primitive.class, tokenWrapper);
                        Primitive<?> op1 = stack.pop(tokenWrapper).resolve().tryCast(Primitive.class, tokenWrapper);
                        stack.push(new BooleanPrimitive(!op1.equals(op2)));
                    }

                    // Numeric args operations
                    case ADD -> numericArgsOperation(NumberPrimitive::add);
                    case SUB -> numericArgsOperation(NumberPrimitive::sub);
                    case MUL -> numericArgsOperation(NumberPrimitive::mul);
                    case DIV -> {
                        if (stack.peek(tokenWrapper).resolve().equals(new NumberPrimitive(0D))) {
                            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.DIVISION_BY_ZERO);
                        }
                        numericArgsOperation(NumberPrimitive::div);
                    }
                    case MOD -> {
                        if (stack.peek(tokenWrapper).resolve().equals(new NumberPrimitive(0D))) {
                            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.DIVISION_BY_ZERO);
                        }
                        numericArgsOperation(NumberPrimitive::mod);
                    }
                    // With Boolean return
                    case LT -> numericArgsOperation(NumberPrimitive::lt);
                    case GT -> numericArgsOperation(NumberPrimitive::gt);
                    case LEQ -> numericArgsOperation(NumberPrimitive::leq);
                    case GEQ -> numericArgsOperation(NumberPrimitive::geq);

                    // Boolean operations
                    case NOT ->
                            stack.push((stack.pop(tokenWrapper).resolve().tryCast(BooleanPrimitive.class, tokenWrapper)).not());
                    case AND -> booleanArgsOperation(BooleanPrimitive::and);
                    case OR -> booleanArgsOperation(BooleanPrimitive::or);
                    case XOR -> booleanArgsOperation(BooleanPrimitive::xor);

                    // String operations
                    case STR_AT -> {
                        int index = stack.pop(tokenWrapper).resolve().tryCast(NumberPrimitive.class, tokenWrapper).intValue();
                        String string = stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        try {
                            stack.push(new StringPrimitive(String.valueOf(string.charAt(index))));
                        } catch (StringIndexOutOfBoundsException e) {
                            ErrorsLogger.triggerInterpreterError(StackadeError.STRING_INDEX_OUT_OF_BOUNDS);
                        }
                    }
                    case STR_CAT -> {
                        String second = stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        String first = stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        stack.push(new StringPrimitive(first + second));
                    }
                    case STR_LEN ->
                            stack.push(new NumberPrimitive((double) (stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue().length())));

                    // References operations
                    case REF_GET ->
                            stack.push(namespaces.get(stack.pop(tokenWrapper).tryCast(NamespaceReference.class, tokenWrapper).getName()));
                    case REF_NAME ->
                            stack.push(new StringPrimitive(stack.pop(tokenWrapper).tryCast(NamespaceReference.class, tokenWrapper).getName()));

                    // Namespaces operations
                    case EXISTS -> stack.push(new BooleanPrimitive(
                            namespaces.contains(stack.pop(tokenWrapper).tryCast(NamespaceReference.class, tokenWrapper).getName())
                    ));

                    // Mutations in Namespaces
                    case ASSIGN -> {
                        LanguageObject value = stack.pop(tokenWrapper);
                        String name = stack.pop(tokenWrapper).tryCast(NamespaceReference.class, tokenWrapper).getName();
                        namespaces.assign(name, value);
                    }
                    case DEL ->
                            namespaces.delete(stack.pop(tokenWrapper).tryCast(StringPrimitive.class, tokenWrapper).getValue());
                    case RAISE_NAME -> {
                        try {
                            namespaces.raise(stack.pop(tokenWrapper).tryCast(StringPrimitive.class, tokenWrapper).getValue());
                        } catch (IllegalStateException e) {
                            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.RAISE_VARIABLE_ERROR);
                        }
                    }

                    case DEFINE_NUM -> definitionFunction(NumberPrimitive.class);
                    case DEFINE_STR -> definitionFunction(StringPrimitive.class);
                    case DEFINE_BOOL -> definitionFunction(BooleanPrimitive.class);
                    case DEFINE_REF -> definitionFunction(NamespaceReference.class);
                    case DEFINE_BOX -> definitionFunction(Box.class);
                    case DEFINE_SEQ -> definitionFunction(Sequence.class);

                    // Casting
                    case TYPE -> stack.push(new StringPrimitive(stack.pop(tokenWrapper).typeName()));
                    case BOX -> stack.push(new Box(stack.pop(tokenWrapper)));
                    case UNBOX -> stack.push(stack.pop(tokenWrapper).tryCast(Box.class, tokenWrapper).getContent());
                    case STR_TO_NUM ->
                            stack.push(stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).toNumberPrimitive());
                    case STR_TO_REF ->
                            stack.push(new NamespaceReference(stack.pop(tokenWrapper).resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue()));
                    case NUM_TO_STR ->
                            stack.push(stack.pop(tokenWrapper).resolve().tryCast(NumberPrimitive.class, tokenWrapper).toStringPrimitive());

                    // Self referencing (handled by interpreter)

                    // I/O
                    case PRINT -> System.out.print((stack.pop(tokenWrapper).resolve()).represent());
                    case INPUT -> {
                        Console console = System.console();
                        stack.push(new StringPrimitive(console.readLine()));
                    }

                    // Debugging
                    case DEBUG -> {
                        System.out.println("\ndata stack = " + DataStack.getInstance());
                        System.out.println("namespaces = " + Namespaces.getInstance());
                    }
                    case HALT -> ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.HALTED_BY_OPERATOR);

                    // Source files inclusion
                    case RUN -> {
                        try {
                            Runner.getInstance().run(stack.pop(tokenWrapper).tryCast(StringPrimitive.class, tokenWrapper).getValue(), false);
                        } catch (IOException e) {
                            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.FILE_NOT_FOUND);
                        }
                    }

                    // Time
                    case NANOS -> {
                        stack.push(new NumberPrimitive(System.nanoTime()));
                    }
                    // Other
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));

                    case END_SEQ -> Namespaces.getInstance().popNamespace();
                }
            }

            default -> ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.UNKNOWN_TOKEN);
        }
    }

    // Helper functions
    private <T extends LanguageObject> void definitionFunction(Class<T> classOfVariable) {
        // System.out.println(stack);
        try {
            T value = stack.pop(tokenWrapper).tryCast(classOfVariable, tokenWrapper); // not doing stack.pop().resolve() allows declaring pointer variables (NamespaceReference)
            String name = stack.pop(tokenWrapper).tryCast(StringPrimitive.class, tokenWrapper).getValue();
            namespaces.define(name, value);

            if (classOfVariable.equals(Sequence.class)) {
                ((Sequence) value).setName(name);
            }
        } catch (ClassCastException e) {
            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.WRONG_DEFINITION_TYPE);
        }

    }

    private void numericArgsOperation(BiFunction<NumberPrimitive, NumberPrimitive, Primitive<?>> biFunction) {
        try {
            NumberPrimitive op2 = stack.pop(tokenWrapper).resolve().tryCast(NumberPrimitive.class, tokenWrapper);
            NumberPrimitive op1 = stack.pop(tokenWrapper).resolve().tryCast(NumberPrimitive.class, tokenWrapper);
            stack.push(biFunction.apply(op1, op2));
        } catch (ClassCastException e) {
            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.WRONG_OPERANDS_TYPE);
        }

    }

    private void booleanArgsOperation(BiFunction<BooleanPrimitive, BooleanPrimitive, BooleanPrimitive> biFunction) {
        try {
            BooleanPrimitive op2 = stack.pop(tokenWrapper).resolve().tryCast(BooleanPrimitive.class, tokenWrapper);
            BooleanPrimitive op1 = stack.pop(tokenWrapper).resolve().tryCast(BooleanPrimitive.class, tokenWrapper);
            stack.push(biFunction.apply(op1, op2));
        } catch (ClassCastException e) {
            ErrorsLogger.triggerInterpreterError(tokenWrapper, StackadeError.WRONG_OPERANDS_TYPE);
        }
    }

}
