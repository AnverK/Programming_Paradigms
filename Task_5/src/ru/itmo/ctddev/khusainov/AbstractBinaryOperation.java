package ru.itmo.ctddev.khusainov;

/**
 * Created by Anver on 19.03.2017.
 */
public abstract class AbstractBinaryOperation implements MultiExpression {

    private MultiExpression operand1, operand2;

    protected AbstractBinaryOperation(MultiExpression operand1, MultiExpression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    protected abstract double makeOperation(double x, double y);

    protected abstract int makeOperation(int x, int y);

    public int evaluate(int x, int y, int z) {
        return makeOperation(operand1.evaluate(x, y, z), operand2.evaluate(x, y, z));
    }

    public double evaluate(double x) {
        return makeOperation(operand1.evaluate(x), operand2.evaluate(x));
    }

    public int evaluate(int x) {
        return makeOperation(operand1.evaluate(x), operand2.evaluate(x));
    }

}
