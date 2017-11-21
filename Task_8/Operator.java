package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;
import expression.exceptions.WrongExpressionException;

/**
 * Created by user on 11.04.2017.
 */
public interface Operator<T> {
    T add(T x, T y) throws OverflowException;

    T subtract(T x, T y) throws OverflowException;

    T multiply(T x, T y) throws OverflowException;

    T divide(T x, T y) throws OverflowException, EvaluateException;

    T negate(T x) throws OverflowException;

    T parse(String s) throws WrongExpressionException;

    T abs(T x) throws OverflowException;

    T square(T x) throws OverflowException;

    T mod(T x, T y) throws EvaluateException;
}