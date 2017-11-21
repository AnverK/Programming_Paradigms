package expression;

import expression.exceptions.*;
import expression.parser.*;

/**
 * Created by Anver on 03.04.2017.
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(Character.isLetterOrDigit(' '));
        try {
            TripleExpression t = new ExpressionParser().parse("(x+1) x");
//            System.out.println(Integer.parseInt("-2147483648"));
            System.out.println(t.evaluate(-1, 0, 0));
//            System.out.println(-1615063843 * (-1826918656));
        } catch (WrongExpressionException e) {
            e.printStackTrace();
        } catch (OverflowException e) {
            e.printStackTrace();
        } catch (EvaluateException e) {
            e.printStackTrace();
        }

    }
}
