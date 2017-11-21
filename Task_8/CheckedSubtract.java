package expression;

import expression.exceptions.*;


/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedSubtract<T> extends AbstractBinaryOperation<T> {
    public CheckedSubtract(TripleExpression<T> operand1, TripleExpression<T> operand2, Operator<T> operator) {
        super(operand1, operand2, operator);
    }

    public T makeOperation(T x, T y) throws OverflowException {
        return operator.subtract(x, y);
    }
}
