package expression.exceptions;

/**
 * Created by Anver on 01.04.2017.
 */
public class WrongExpressionException extends Exception {
    public WrongExpressionException() {
    }

    public WrongExpressionException(String message) {
        super(message);
    }

    public WrongExpressionException(String message, String expression, int beg, int end, StringBuilder service) {
        super(message + beg + '\n' + expression.substring(0, end) + '\n' + service);

    }

}
