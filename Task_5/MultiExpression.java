package ru.itmo.ctddev.khusainov;

/**
 * Created by user on 22.03.2017.
 */
public interface MultiExpression extends Expression, DoubleExpression, TripleExpression {
    int evaluate(int x, int y, int z);

    double evaluate(double x);

    int evaluate(int x);
}
