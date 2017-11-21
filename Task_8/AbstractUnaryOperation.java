package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 27.03.2017.
 */
public abstract class AbstractUnaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> operand;
    protected Operator<T> operator;

    protected AbstractUnaryOperation(TripleExpression<T> operand, Operator<T> operator) {
        this.operand = operand;
        this.operator = operator;
    }

    protected abstract T makeOperation(T x) throws OverflowException, EvaluateException;

    public T evaluate(T x, T y, T z) throws OverflowException, EvaluateException {
        return makeOperation(operand.evaluate(x, y, z));
    }
}
