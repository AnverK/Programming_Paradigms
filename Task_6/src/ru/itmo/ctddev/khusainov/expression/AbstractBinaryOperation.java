package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 19.03.2017.
 */
public abstract class AbstractBinaryOperation implements TripleExpression {

    private TripleExpression operand1, operand2;

    protected AbstractBinaryOperation(TripleExpression operand1, TripleExpression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    protected abstract int makeOperation(int x, int y);

    public int evaluate(int x, int y, int z) {
        return makeOperation(operand1.evaluate(x, y, z), operand2.evaluate(x, y, z));
    }
}
