package expression.exceptions;

/**
 * Created by user on 05.04.2017.
 */
public class EvaluateException extends Exception {
    public EvaluateException() {
        super("Evaluate exception");
    }

    public EvaluateException(String op, String x, String y, String comment) {
        super("Illegal arguments for operation: " + x + " " + op + " " + y + " (" + comment + ")");
    }

    public EvaluateException(String op, String x, String comment) {
        super("Illegal arguments for operation: " + op + " " + x + " (" + comment + ")");
    }
}
