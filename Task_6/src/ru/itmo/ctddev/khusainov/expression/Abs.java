package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by user on 29.03.2017.
 */
public class Abs extends AbstractUnaryOperation {

    public Abs(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) {
        return Math.abs(x);
    }

}
