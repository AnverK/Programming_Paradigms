package expression.exceptions;

/**
 * Created by Anver on 01.04.2017.
 */
public class OverflowException extends Exception {
    public OverflowException() {
        super("Overflow");
    }

    public OverflowException(String message) {
        super(message);
    }
}
