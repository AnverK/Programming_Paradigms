package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by Anver on 27.03.2017.
 */
public class Multiply extends AbstractBinaryOperation {
    public Multiply(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) {
        return x * y;
    }
}

