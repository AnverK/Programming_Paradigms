package ru.itmo.ctddev.khusainov.expression;


/**
 * Created by Anver on 19.03.2017.
 */
public class Add extends AbstractBinaryOperation {

    public Add(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) {
        return x + y;
    }

}
