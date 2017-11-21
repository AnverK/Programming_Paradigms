package expression;

import expression.exceptions.*;

/**
 * Created by user on 11.04.2017.
 */
public class CheckedMod<T> extends AbstractBinaryOperation<T> {

    public CheckedMod(TripleExpression<T> operand1, TripleExpression<T> operand2, Operator<T> operator) {
        super(operand1, operand2, operator);
    }

    public T makeOperation(T x, T y) throws EvaluateException {
        return operator.mod(x, y);
    }
}
