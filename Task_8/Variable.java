package expression;

/**
 * Created by Anver on 19.03.2017.
 */
public class Variable<T> implements TripleExpression<T> {

    private String name;

    public Variable(String s) {
        name = s;
    }

    public T evaluate(T x, T y, T z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return z;
        }
    }
}
