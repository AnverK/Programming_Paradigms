package expression;

import expression.exceptions.*;

/**
 * Created by user on 05.04.2017.
 */
public class CheckedSqrt extends AbstractUnaryOperation {
    public CheckedSqrt(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) throws OverflowException, EvaluateException {
        if (x < 0) {
            throw new EvaluateException("sqrt", x, "negative argument");
        }
        int l = -1, r = 46341;
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (m * m <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
