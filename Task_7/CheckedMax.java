package expression;

/**
 * Created by user on 05.04.2017.
 */
public class CheckedMax extends AbstractBinaryOperation {
    public CheckedMax(TripleExpression operand1, TripleExpression operand2) {
        super(operand1, operand2);
    }

    public int makeOperation(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }
}
