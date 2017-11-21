package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by user on 11.04.2017.
 */
public class OperatorDouble implements Operator<Double> {
    public Double add(Double x, Double y) {
        return x + y;
    }

    public Double subtract(Double x, Double y) {
        return x - y;
    }

    public Double multiply(Double x, Double y) {
        return x * y;
    }

    public Double divide(Double x, Double y) throws EvaluateException {
        return x / y;
    }

    public Double negate(Double x) {
        return -x;
    }

    public Double parse(String s) {
        return Double.parseDouble(s);
    }

    public Double abs(Double x) {
        if (x.compareTo(0.0) < 0) {
            return -x;
        }
        return x;
    }

    public Double square(Double x) {
        return x * x;
    }

    public Double mod(Double x, Double y) throws EvaluateException {
        return x % y;
    }
}
