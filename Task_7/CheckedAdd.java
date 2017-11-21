package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedAdd extends AbstractBinaryOperation {

    public CheckedAdd(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) throws OverflowException {
        if (x >= 0 && y <= 0 || x <= 0 && y >= 0) {
            return x + y;
        }
        if (x > 0 && y > 0) {
            if (x + y < y) {
                throw new OverflowException("Overflow during the operation " + x + " + " + y);
            } else {
                return x + y;
            }
        }
        if (x + y > x) {
            throw new OverflowException("Overflow during the operation " + x + " + " + y);
        } else {
            return x + y;
        }
    }
}
