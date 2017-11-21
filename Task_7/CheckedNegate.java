package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(TripleExpression operand) {
        super(operand);
    }

    public int makeOperation(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow during the operation -" + x);
        }
        return -x;
    }
}
