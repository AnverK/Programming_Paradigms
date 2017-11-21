package expression;

import expression.exceptions.OverflowException;

/**
 * Created by user on 11.04.2017.
 */
public class CheckedAbs<T> extends AbstractUnaryOperation<T> {
    public CheckedAbs(TripleExpression<T> operand, Operator<T> operator) {
        super(operand, operator);
    }

    public T makeOperation(T x) throws OverflowException {
        return operator.abs(x);
    }
}
