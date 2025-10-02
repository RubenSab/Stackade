package LanguageExecution.Interpreter;

import LanguageEnvironment.ConditionalContextsStack;
import LanguageEnvironment.DataStack;
import LanguageEnvironment.LanguageObjects.Box;
import LanguageEnvironment.LanguageObjects.UnexecutedSequence;
import LanguageEnvironment.LanguageObjects.LanguageObject;
import LanguageEnvironment.LanguageObjects.Primitives.NamespaceReference;
import LanguageEnvironment.LanguageObjects.Primitives.BooleanPrimitive;
import LanguageEnvironment.LanguageObjects.Primitives.NumberPrimitive;
import LanguageEnvironment.LanguageObjects.Primitives.Primitive;
import LanguageEnvironment.LanguageObjects.Primitives.StringPrimitive;
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
            case NamespaceToken namespaceToken -> {
                try {
                    LanguageObject invoked = namespaceToken.resolve();
                    if (invoked instanceof UnexecutedSequence) {
                        ((UnexecutedSequence) invoked).run();
                    } else {
                        stack.push(new NamespaceReference(namespaceToken.getName().getValue()));
                    }
                } catch (Namespaces.UndefinedVariableException e) {
                    ErrorsLogger.triggerError(tokenWrapper, Error.UNDEFINED_VARIABLE);
                }

            }
            case KeywordToken keywordToken -> {
                switch (keywordToken) {

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
                    case NEQ -> stack.push(new BooleanPrimitive(!stack.pop().equals(stack.pop())));

                    // Numeric args operations
                    case ADD -> numericArgsOperation(NumberPrimitive::add);
                    case SUB -> numericArgsOperation(NumberPrimitive::sub);
                    case MUL -> numericArgsOperation(NumberPrimitive::mul);
                    case DIV -> {
                        if (stack.peek().resolve().equals(new NumberPrimitive(0D))) {
                            ErrorsLogger.triggerError(tokenWrapper, Error.DIVISION_BY_ZERO);
                        }
                        numericArgsOperation(NumberPrimitive::div);
                    }
                    case MOD -> {
                        if (stack.peek().resolve().equals(new NumberPrimitive(0D))) {
                            ErrorsLogger.triggerError(tokenWrapper, Error.DIVISION_BY_ZERO);
                        }
                        numericArgsOperation(NumberPrimitive::mod);
                    }
                    // With Boolean return
                    case LT  -> numericArgsOperation(NumberPrimitive::lt);
                    case GT  -> numericArgsOperation(NumberPrimitive::gt);
                    case LEQ -> numericArgsOperation(NumberPrimitive::leq);
                    case GEQ -> numericArgsOperation(NumberPrimitive::geq);

                    // Boolean operations
                    case NOT -> stack.push((stack.pop().resolve().tryCast(BooleanPrimitive.class, tokenWrapper)).not());
                    case AND -> booleanArgsOperation(BooleanPrimitive::and);
                    case OR  -> booleanArgsOperation(BooleanPrimitive::or);
                    case XOR -> booleanArgsOperation(BooleanPrimitive::xor);

                    // String operations
                    case STR_AT -> {
                        int index = stack.pop().resolve().tryCast(NumberPrimitive.class, tokenWrapper).intValue();
                        String string = stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        stack.push(new StringPrimitive(String.valueOf(string.charAt(index))));
                    }
                    case STR_CAT -> {
                        String second = stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        String first = stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue();
                        stack.push(new StringPrimitive(first + second));
                    }
                    case STR_LEN -> stack.push(new NumberPrimitive((double) (stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue().length())));

                    // References operations
                    case REF_GET -> stack.push(namespaces.get(stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName()));
                    case REF_NAME -> stack.push(new StringPrimitive(stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName()));

                    // Namespaces operations
                    case EXISTS -> stack.push(new BooleanPrimitive(
                            namespaces.contains(stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName())
                    ));

                    // Mutations in Namespaces
                    case ASSIGN -> {
                        LanguageObject value = stack.pop();
                        String name = stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName();
                        namespaces.assign(name, value);
                    }
                    case DEL -> namespaces.delete(stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName());
                    case RAISE_NAME -> {
                        try {
                            namespaces.raise(stack.pop().tryCast(NamespaceReference.class, tokenWrapper).getName());
                        } catch (IllegalStateException e) {
                            ErrorsLogger.triggerError(tokenWrapper, Error.RAISE_VARIABLE_ERROR);
                        }
                    }

                    case DEFINE_NUM -> definitionFunction(NumberPrimitive.class);
                    case DEFINE_STR -> definitionFunction(StringPrimitive.class);
                    case DEFINE_BOOL -> definitionFunction(BooleanPrimitive.class);
                    case DEFINE_REF -> definitionFunction(NamespaceReference.class);
                    case DEFINE_BOX -> definitionFunction(Box.class);
                    case DEFINE_SEQ -> definitionFunction(UnexecutedSequence.class);

                    // Casting
                    case TYPE -> stack.push(new StringPrimitive(stack.pop().typeName()));
                    case BOX -> stack.push(new Box(stack.pop()));
                    case UNBOX -> stack.push(stack.pop().tryCast(Box.class, tokenWrapper).getContent());
                    case STR_TO_NUM -> stack.push(stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).toNumberPrimitive());
                    case STR_TO_REF -> stack.push(new NamespaceReference(stack.pop().resolve().tryCast(StringPrimitive.class, tokenWrapper).getValue()));
                    case NUM_TO_STR -> stack.push(stack.pop().resolve().tryCast(NumberPrimitive.class, tokenWrapper).toStringPrimitive());

                    // Self referencing
                    case SELF -> ConditionalContextsStack.getInstance().executeTop();

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
                    case HALT -> ErrorsLogger.triggerError(tokenWrapper, Error.HALTED_BY_OPERATOR);

                    // Source files inclusion
                    case RUN -> {
                        try {
                            Runner.getInstance().run(stack.pop().tryCast(StringPrimitive.class, tokenWrapper).getValue(), false);
                        } catch (IOException e) {
                            ErrorsLogger.triggerError(tokenWrapper, Error.FILE_NOT_FOUND);
                        }
                    }
                    // Other
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));

                }
            }

            default -> ErrorsLogger.triggerError(tokenWrapper, Error.UNKNOWN_TOKEN);
        }
    }

    // Helper functions
    private <T extends LanguageObject> void definitionFunction(Class<T> classOfVariable) {
        try {
            T value = stack.pop().tryCast(classOfVariable, tokenWrapper); // not doing stack.pop().resolve() allows declaring pointer variables (NamespaceReference)
            String name = stack.pop().tryCast(StringPrimitive.class, tokenWrapper).getValue();
            namespaces.define(name, value);

            if (classOfVariable.equals(UnexecutedSequence.class)) {
                ((UnexecutedSequence) value).setName(name);
            }
        } catch (ClassCastException e) {
            ErrorsLogger.triggerError(tokenWrapper, Error.WRONG_DEFINITION_TYPE);
        }

    }

    private void numericArgsOperation(BiFunction<NumberPrimitive, NumberPrimitive, Primitive<?>> biFunction) {
        try {
            NumberPrimitive op2 = stack.pop().resolve().tryCast(NumberPrimitive.class, tokenWrapper);
            NumberPrimitive op1 = stack.pop().resolve().tryCast(NumberPrimitive.class, tokenWrapper);
            stack.push(biFunction.apply(op1, op2));
        } catch (ClassCastException e) {
            ErrorsLogger.triggerError(tokenWrapper, Error.WRONG_OPERANDS_TYPE);
        }

    }
    private void booleanArgsOperation(BiFunction<BooleanPrimitive, BooleanPrimitive, BooleanPrimitive> biFunction) {
        try {
            BooleanPrimitive op2 = stack.pop().resolve().tryCast(BooleanPrimitive.class, tokenWrapper);
            BooleanPrimitive op1 = stack.pop().resolve().tryCast(BooleanPrimitive.class, tokenWrapper);
            stack.push(biFunction.apply(op1, op2));
        } catch (ClassCastException e) {
            ErrorsLogger.triggerError(tokenWrapper, Error.WRONG_OPERANDS_TYPE);
        }
    }

}
