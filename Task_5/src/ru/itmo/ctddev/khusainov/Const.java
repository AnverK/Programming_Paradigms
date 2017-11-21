package ru.itmo.ctddev.khusainov;

/**
 * Created by Anver on 19.03.2017.
 */
public class Const implements MultiExpression {

    private double value;

    public Const(int x) {
        value = (double) x;
    }

    public double evaluate(double x) {
        return value;
    }

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    public int evaluate(int x) {
        return (int) value;
    }

}
