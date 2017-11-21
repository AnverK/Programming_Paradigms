package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;
import expression.exceptions.WrongExpressionException;
import expression.parser.ExpressionParser.*;

/**
 * Created by user on 11.04.2017.
 */
public class OperatorIntegerCheckOverflow implements Operator<Integer> {
    public Integer add(Integer x, Integer y) throws OverflowException {
        if (y.compareTo(0) > 0 && x.compareTo(Integer.MAX_VALUE - y) > 0) {
            throw new OverflowException("Overflow during the operation " + x + " + " + y);
        }
        if (y.compareTo(0) < 0 && x.compareTo(Integer.MIN_VALUE - y) < 0) {
            throw new OverflowException("Overflow during the operation " + x + " + " + y);
        }
        return x + y;
    }

    public Integer subtract(Integer x, Integer y) throws OverflowException {
        if (y.compareTo(0) > 0 && x.compareTo(Integer.MIN_VALUE + y) < 0) {
            throw new OverflowException("Overflow during the operation " + x + " - " + y);
        }
        if (y.compareTo(0) < 0 && x.compareTo(Integer.MAX_VALUE + y) > 0) {
            throw new OverflowException("Overflow during the operation " + x + " - " + y);
        }
        return x - y;
    }

    public Integer multiply(Integer x, Integer y) throws OverflowException {
        if (x.compareTo(0) == 0 || y.compareTo(0) == 0)
            return 0;
        if (x.compareTo(0) > 0 && y.compareTo(0) > 0 && x.compareTo(Integer.MAX_VALUE / y) > 0) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x.compareTo(0) > 0 && y.compareTo(0) < 0 && y.compareTo(Integer.MIN_VALUE / x) < 0) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x.compareTo(0) < 0 && y.compareTo(0) > 0 && x.compareTo(Integer.MIN_VALUE / y) < 0) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        if (x.compareTo(0) < 0 && y.compareTo(0) < 0 && x.compareTo(Integer.MAX_VALUE / y) < 0) {
            throw new OverflowException("Overflow during the operation " + x + " * " + y);
        }
        return x * y;
    }

    public Integer divide(Integer x, Integer y) throws OverflowException, EvaluateException {
        if (y.equals(0)) {
            throw new EvaluateException("/", x.toString(), y.toString(), "division by zero");
        }
        if (x.equals(Integer.MIN_VALUE) && y.equals(-1)) {
            throw new OverflowException("Overflow during the operation " + x + " / " + y);
        }
        return x / y;
    }

    public Integer negate(Integer x) throws OverflowException {
        if (x.equals(Integer.MIN_VALUE))
            throw new OverflowException("Overflow during the operation -" + x);
        return -x;
    }


    public Integer parse(String s) throws WrongExpressionException {
        int index = 0;
        if (s.charAt(0) == '-') {
            index++;
        }
        while (index < s.length() && Character.isDigit(s.charAt(index))) {
            index++;
        }
        try {
            return Integer.parseInt(s.substring(0, index));
        } catch (NumberFormatException e) {
            throw new WrongExpressionException();
        }
    }

    public Integer abs(Integer x) throws OverflowException {
        if (x.equals(Integer.MIN_VALUE)) {
            throw new OverflowException("Overflow during the operation abs " + x);
        }
        if (x.compareTo(0) < 0) {
            return -x;
        }
        return x;
    }

    public Integer square(Integer x) throws OverflowException {
        if (x.compareTo(46340) > 0 || x.compareTo(-46340) < 0) {
            throw new OverflowException("Overflow during the operation square " + x);
        }
        return x * x;
    }

    public Integer mod(Integer x, Integer y) throws EvaluateException {
        if (y.equals(0)) {
            throw new EvaluateException("mod", x.toString(), y.toString(), "mod by zero");
        }
        return x % y;
    }
}
