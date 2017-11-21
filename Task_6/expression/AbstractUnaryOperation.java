package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 27.03.2017.
 */
public abstract class AbstractUnaryOperation implements TripleExpression {
    private TripleExpression operand;

    protected AbstractUnaryOperation(TripleExpression operand) {
        this.operand = operand;
    }

    protected abstract int makeOperation(int x);

    public int evaluate(int x, int y, int z) {
        return makeOperation(operand.evaluate(x, y, z));
    }
}
