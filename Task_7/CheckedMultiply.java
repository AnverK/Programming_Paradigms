package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) throws OverflowException {
        if (x == 0 || y == 0) {
            return 0;
        }
        if (x > 0 && y > 0 && x > Integer.MAX_VALUE / y) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x > 0 && y < 0 && y < Integer.MIN_VALUE / x) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x < 0 && y > 0 && x < Integer.MIN_VALUE / y) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x < 0 && y < 0 && x < Integer.MAX_VALUE / y) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        return x * y;
    }
}
