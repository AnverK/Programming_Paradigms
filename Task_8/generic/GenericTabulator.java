package expression.generic;

import expression.*;
import expression.exceptions.EvaluateException;
import expression.exceptions.WrongExpressionException;
import expression.parser.*;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    private String expression;

    private <T> Object[][][] countTable(Operator<T> op, int x1, int x2, int y1, int y2, int z1, int z2) throws WrongExpressionException {
        TripleExpression<T> exp = new ExpressionParser<>(op).parse(expression);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = exp.evaluate(op.parse(Integer.toString(i)), op.parse(Integer.toString(j)), op.parse(Integer.toString(k)));
                    } catch (Exception e) {
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return result;
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws EvaluateException, WrongExpressionException {
        this.expression = expression;
        Operator<?> operator;

        switch (mode) {
            case "i":
                operator = new OperatorIntegerCheckOverflow();
                break;
            case "d":
                operator = new OperatorDouble();
                break;
            case "bi":
                operator = new OperatorBigInteger();
                break;
            case "u":
                operator = new OperatorInteger();
                break;
            case "b":
                operator = new OperatorByte();
                break;
            case "f":
                operator = new OperatorFloat();
                break;
            default:
                throw new WrongExpressionException("This mode (" + mode + ") is unknown");
        }
        return countTable(operator, x1, x2, y1, y2, z1, z2);
    }
}
