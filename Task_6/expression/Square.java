package ru.itmo.ctddev.khusainov.expression;

/**
 * Created by user on 29.03.2017.
 */
public class Square extends AbstractUnaryOperation {

    public Square(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) {
        return x*x;
    }
}