package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 19.03.2017.
 */
public class Const implements TripleExpression {

    private int value;

    public Const(int x) {
        value = x;
    }

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}
