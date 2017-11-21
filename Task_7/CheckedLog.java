package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 04.04.2017.
 */
public class CheckedLog extends AbstractUnaryOperation {
    public CheckedLog(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) throws OverflowException, EvaluateException {
        if (x <= 0) {
            throw new EvaluateException("log2", x, "not positive argument");
        }
        int l = -1, r = 31;
        while (l < r - 1) {
            int m = (l + r) / 2;
            if ((1 << m) <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
