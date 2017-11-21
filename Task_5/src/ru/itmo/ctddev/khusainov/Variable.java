package ru.itmo.ctddev.khusainov;

/**
 * Created by Anver on 19.03.2017.
 */
public class Variable implements MultiExpression {

    private String name;

    public Variable(String s) {
        name = s;
    }

    public double evaluate(double x) {
        return x;
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

    public int evaluate(int x) {
        return x;
    }

}
