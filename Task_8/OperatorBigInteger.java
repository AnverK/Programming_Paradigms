package expression;

import expression.exceptions.EvaluateException;

import java.math.BigInteger;

/**
 * Created by user on 11.04.2017.
 */
public class OperatorBigInteger implements Operator<BigInteger> {
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    public BigInteger divide(BigInteger x, BigInteger y) throws EvaluateException {
        if (y.equals(BigInteger.ZERO)) {
            throw new EvaluateException("/", x.toString(), y.toString(), "division by zero");
        }
        return x.divide(y);
    }

    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    public BigInteger parse(String s) {
        return new BigInteger(s);
    }

    public BigInteger abs(BigInteger x) {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            return x.negate();
        }
        return x;
    }

    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    public BigInteger mod(BigInteger x, BigInteger y) throws EvaluateException {
        if (y.equals(BigInteger.ZERO)) {
            throw new EvaluateException("mod", x.toString(), y.toString(), "mod by zero");
        }
        return x.remainder(y);
    }
}
