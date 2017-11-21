package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 19.03.2017.
 */
public class Variable implements TripleExpression {

    private String name;

    public Variable(String s) {
        name = s;
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return z;
        }
    }
}
