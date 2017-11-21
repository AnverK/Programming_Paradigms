package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 27.03.2017.
 */
public class Minus extends AbstractUnaryOperation {
    public Minus(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) {
        return -x;
    }
}
