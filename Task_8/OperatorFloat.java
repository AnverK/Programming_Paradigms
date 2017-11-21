package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by user on 12.04.2017.
 */
public class OperatorFloat implements Operator<Float> {
    public Float add(Float x, Float y) {
        return x + y;
    }

    public Float subtract(Float x, Float y) {
        return x - y;
    }

    public Float multiply(Float x, Float y) {
        return x * y;
    }

    public Float divide(Float x, Float y) throws EvaluateException {
        return x / y;
    }

    public Float negate(Float x) {
        return -x;
    }

    public Float parse(String s) {
        return Float.parseFloat(s);
    }

    public Float abs(Float x) {
        if (x.compareTo(Float.valueOf("0")) < 0) {
            return -x;
        }
        return x;
    }

    public Float square(Float x) {
        return x * x;
    }

    public Float mod(Float x, Float y) throws EvaluateException {
        return x % y;
    }
}
