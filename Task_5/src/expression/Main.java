package expression;

import ru.itmo.ctddev.khusainov.*;

/**
 * Created by Anver on 19.03.2017.
 */

public class Main {
    public static void main(String[] args) {
        double value = Integer.parseInt(args[0]);

        System.out.println(
                new Add(
                        new Subtract(
                                new Multiply(
                                        new Variable("x"),
                                        new Variable("x")
                                ),
                                new Multiply(
                                        new Const(2),
                                        new Variable("x")
                                )
                        ),
                        new Const(1)
                ).evaluate(value)
        );
    }
}
