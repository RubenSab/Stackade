import Interpreter.InterpretableUnit;

import java.util.ArrayList;

public class test {
    public static ArrayList<Unit> parse(String source) {

    }

    public static void main(String[] args) {
        System.out.println(parse("test ((abc f) de)"));
    }
    // create B(text), create B(B(abc, f), B(de))
}
