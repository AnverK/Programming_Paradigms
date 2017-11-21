package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 04.04.2017.
 */
public class CheckedPow extends AbstractUnaryOperation {
    public CheckedPow(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) throws OverflowException, EvaluateException {
        if (x < 0) {
            throw new EvaluateException("pow2", x, "negative argument");
        }
        if (x >= 31) {
            throw new OverflowException("Overflow during the operation pow2" + x);
        }
        return 1 << x;
    }
}
