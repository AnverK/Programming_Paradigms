package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedNegate<T> extends AbstractUnaryOperation<T> {
    public CheckedNegate(TripleExpression<T> operand, Operator<T> operator) {
        super(operand, operator);
    }

    public T makeOperation(T x) throws OverflowException {
        return operator.negate(x);
    }
}
