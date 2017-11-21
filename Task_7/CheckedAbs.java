package expression;

import expression.exceptions.*;

/**
 * Created by user on 05.04.2017.
 */
public class CheckedAbs extends AbstractUnaryOperation {
    public CheckedAbs(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE)
            throw new OverflowException("Overflow during the operation abs" + x);
        if (x < 0)
            return -x;
        return x;
    }
}
