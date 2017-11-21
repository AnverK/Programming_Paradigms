package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by user on 12.04.2017.
 */
public class OperatorInteger implements Operator<Integer> {
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    public Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    public Integer multiply(Integer x, Integer y) {
        return x * y;
    }

    public Integer divide(Integer x, Integer y) throws EvaluateException {
        if (y == 0) {
            throw new EvaluateException("/", x.toString(), y.toString(), "division by zero");
        }
        return x / y;
    }

    public Integer negate(Integer x) {
        return -x;
    }

    public Integer parse(String s) {
        return Integer.parseInt(s);
    }

    public Integer abs(Integer x) {
        if (x.compareTo(0) < 0) {
            return -x;
        }
        return x;
    }

    public Integer square(Integer x) {
        return x * x;
    }

    public Integer mod(Integer x, Integer y) throws EvaluateException {
        if (y.equals(0)) {
            throw new EvaluateException("mod", x.toString(), y.toString(), "mod by zero");
        }
        return x % y;
    }
}
