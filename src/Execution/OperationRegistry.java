package Execution;

import Environment.ConditionalContextsStack;
import Environment.DataStack;
import Environment.LanguageObjects.FrozenBlock;
import Environment.LanguageObjects.LanguageObject;
import Environment.LanguageObjects.Primitives.BooleanPrimitive;
import Environment.LanguageObjects.Primitives.NumberPrimitive;
import Environment.LanguageObjects.Primitives.StringPrimitive;
import Environment.Namespaces.Namespaces;
import Execution.Tokens.*;

import java.util.function.BiFunction;

public class OperationRegistry {
    private static final DataStack stack = DataStack.getInstance();
    private static final Namespaces namespaces = Namespaces.getInstance();

    public static void executeToken(Token token) {
        switch (token) {
            case NumberToken numberToken ->  // is a primitive token
                    stack.push(new NumberPrimitive(numberToken.get()));
            case StringToken stringToken ->
                    stack.push(new StringPrimitive(stringToken.get()));
            case NamespaceToken namespaceToken -> {
                    LanguageObject invoked = namespaceToken.resolve();
                    if (invoked instanceof FrozenBlock) {
                        ((FrozenBlock) invoked).run();
                    } else {
                        stack.push(namespaceToken.getName()); // TODO: manage namespace tokens instead of pushing strings
                    }
            }
            case KeywordToken keywordToken -> {
                switch (keywordToken) { // TODO: add lists and list operations, add input, add BLOCK
                    // Booleans
                    case TRUE -> stack.push(new BooleanPrimitive(true));
                    case FALSE -> stack.push(new BooleanPrimitive(false));
                    // SELF
                    case SELF -> ConditionalContextsStack.getInstance().executeTop();
                    // Stack
                    case DUP -> stack.push(stack.peek());
                    case POP -> stack.pop();
                    case SWAP -> stack.swap();
                    // Declarations in Namespaces
                    case DEL -> namespaces.delete(((StringPrimitive) stack.pop()).getValue());
                    case DECLARE_NUM -> {
                        NumberPrimitive value = (NumberPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, value);
                    }
                    case DECLARE_BOOL -> {
                        BooleanPrimitive value = (BooleanPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, value);
                    }
                    case DECLARE_STR -> {
                        StringPrimitive value = (StringPrimitive) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, value);
                    }
                    case DECLARE_FROZEN_BLOCK -> {
                        FrozenBlock block = (FrozenBlock) stack.pop();
                        String name = ((StringPrimitive) stack.pop()).getValue();
                        namespaces.define(name, block);
                    }
                    // Assignations in Namespaces
                    case ASSIGN -> {
                        LanguageObject value = stack.pop();
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
                    // Num operations
                    case ADD -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).add((NumberPrimitive) y));
                    case SUB -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).sub((NumberPrimitive) y));
                    case MUL -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).mul((NumberPrimitive) y));
                    case DIV -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).div((NumberPrimitive) y));
                    case MOD -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).mod((NumberPrimitive) y));
                    case EQ -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).eq((NumberPrimitive) y));
                    case NEQ -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).neq((NumberPrimitive) y));
                    case LT -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).lt((NumberPrimitive) y));
                    case GT -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).gt((NumberPrimitive) y));
                    case LEQ -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).leq((NumberPrimitive) y));
                    case GEQ -> applyStackBinaryFunction((x, y) -> ((NumberPrimitive) x).geq((NumberPrimitive) y));
                    // Boolean operations
                    case NOT -> stack.push(((BooleanPrimitive) stack.pop()).not());
                    case AND -> applyStackBinaryFunction((x, y) -> ((BooleanPrimitive) x).and((BooleanPrimitive) y));
                    case OR -> applyStackBinaryFunction((x, y) -> ((BooleanPrimitive) x).or((BooleanPrimitive) y));
                    case XOR -> applyStackBinaryFunction((x, y) -> ((BooleanPrimitive) x).xor((BooleanPrimitive) y));
                    // I/O
                    case PRINT -> System.out.print(stack.pop()); // TODO: resolve from namespace
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }
    }

    private static void applyStackBinaryFunction(BiFunction<LanguageObject, LanguageObject, LanguageObject> biFunction) {
        LanguageObject b = stack.pop();
        LanguageObject a = stack.pop();
        LanguageObject op2 = null;
        LanguageObject op1 = null;
        if (b instanceof NumberPrimitive) {
            op2 = b;
        } else if (a instanceof StringPrimitive) {
            op2 = Namespaces.getInstance().get(((StringPrimitive) b).getValue());
        }
        if (a instanceof NumberPrimitive) {
            op1 = a;
        } else if (a instanceof StringPrimitive) {
            op1 = Namespaces.getInstance().get(((StringPrimitive) a).getValue());
        }
        stack.push(biFunction.apply(op1, op2));
    }

}
