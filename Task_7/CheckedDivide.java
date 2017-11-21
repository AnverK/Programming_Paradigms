package expression;

import expression.exceptions.*;

/**
 * Created by Anver on 01.04.2017.
 */
public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) throws OverflowException, EvaluateException {
        if (y == 0) {
            throw new EvaluateException("/", x, y, "division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Overflow during the operation " + x + " / " + y);
        }
        return x / y;
    }
}
