package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by user on 12.04.2017.
 */
public class OperatorByte implements Operator<Byte> {

    private Byte intToByte(int num) {
        Integer buf = num & 255;
        return buf.byteValue();
    }

    public Byte add(Byte x, Byte y) {
        return intToByte(x + y);
    }

    public Byte subtract(Byte x, Byte y) {
        return intToByte(x - y);
    }

    public Byte multiply(Byte x, Byte y) {
        return intToByte(x * y);
    }

    public Byte divide(Byte x, Byte y) throws EvaluateException {
        if (y.equals(Byte.valueOf("0"))) {
            throw new EvaluateException("/", x.toString(), y.toString(), "division by zero");
        }
        return intToByte(x / y);
    }

    public Byte negate(Byte x) {
        return intToByte(-x);
    }

    public Byte parse(String s) {
        return Byte.parseByte(s);
    }

    public Byte abs(Byte x) {
        if (x.compareTo(Byte.valueOf("0")) < 0) {
            return intToByte(-x);
        }
        return x;
    }

    public Byte square(Byte x) {
        return intToByte(x * x);
    }

    public Byte mod(Byte x, Byte y) throws EvaluateException {
        if (y.equals(Byte.valueOf("0"))) {
            throw new EvaluateException("mod", x.toString(), y.toString(), "mod by zero");
        }
        return intToByte(x % y);
    }
}
