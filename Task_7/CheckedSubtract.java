package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) throws OverflowException {
        if (y > 0 && x < Integer.MIN_VALUE + y) {
            throw new OverflowException("Overflow during the operation " + x + " - " + y);
        }
        if (y < 0 && x > Integer.MAX_VALUE + y) {
            throw new OverflowException("Overflow during the operation " + x + " - " + y);
        }
        return x - y;
    }
}
