package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 19.03.2017.
 */
public abstract class AbstractBinaryOperation<T> implements TripleExpression<T> {

    private TripleExpression<T> operand1, operand2;
    protected Operator<T> operator;

    protected AbstractBinaryOperation(TripleExpression<T> operand1, TripleExpression<T> operand2, Operator<T> operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    protected abstract T makeOperation(T x, T y) throws OverflowException, EvaluateException;

    public T evaluate(T x, T y, T z) throws OverflowException, EvaluateException {
        return makeOperation(operand1.evaluate(x, y, z), operand2.evaluate(x, y, z));
    }
}
