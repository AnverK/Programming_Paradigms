package expression.parser;

import expression.*;
import expression.exceptions.*;

/**
 * Created by Anver on 26.03.2017.
 */
public class ExpressionParser<T> implements Parser<T> {

    private String expression, identifier;
    private int index, balance;
    private T constant;
    private Operator<T> operator;
    private TokenValue curToken;
    private String var;

    public ExpressionParser(Operator<T> operator) {
        this.operator = operator;
    }

    private enum TokenValue {
        MUL, DIV, PLUS, MINUS, VAR, NUM, LP, RP, LOG, POW, START, ABS, SQRT, MIN, MAX, MOD, SQR, END;
    }

    private StringBuilder makeServiceString(int beg, int end) {
        StringBuilder s = new StringBuilder(end);
        for (int i = 0; i < beg; i++)
            s.append(' ');
        s.append('^');
        for (int i = beg + 1; i < end; i++) {
            s.append('-');
        }
        return s;
    }

    private void skipWhiteSpace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private String getIdentifier() {
        int beg = index;
        if (Character.isLetter(expression.charAt(index))) {
            index++;
            while (index < expression.length() && Character.isLetterOrDigit(expression.charAt(index))) {
                index++;
            }
        }
        return expression.substring(beg, index);
    }

    private void checkOper(int shift) throws WrongExpressionException {
        index -= shift;
        if (curToken == TokenValue.RP || curToken == TokenValue.NUM || curToken == TokenValue.VAR) {
            throw new WrongExpressionException("Missing binary operator at index: ", expression, index, index + shift, makeServiceString(index, index));
        }
        index += shift;
    }

    private void getToken() throws WrongExpressionException {
        skipWhiteSpace();
        if (index >= expression.length()) {
            if (curToken == TokenValue.END)
                throw new WrongExpressionException("Missing the operand at index: ", expression, index, index, makeServiceString(index, index));
            curToken = TokenValue.END;
            return;
        }
        char ch = expression.charAt(index);
        index++;
        identifier = " ";
        switch (ch) {
            case '*':
                curToken = TokenValue.MUL;
                return;
            case '/':
                curToken = TokenValue.DIV;
                return;
            case '+':
                curToken = TokenValue.PLUS;
                return;
            case '-':
                if (index >= expression.length()) {
                    throw new WrongExpressionException("Missing the operand at index: ", expression, index, index, makeServiceString(index, index));
                }
                if (Character.isDigit(expression.charAt(index)) && (curToken == TokenValue.START || (curToken != TokenValue.NUM && curToken != TokenValue.VAR && curToken != TokenValue.RP))) {
                    break;
                }
                curToken = TokenValue.MINUS;
                return;
            case '(':
                checkOper(1);
                balance++;
                curToken = TokenValue.LP;
                return;
            case ')':
                balance--;
                if (balance < 0) {
                    throw new WrongExpressionException("Excessive closing parenthesis at index: ", expression, index, index + 1, makeServiceString(index, index));
                }
                curToken = TokenValue.RP;
                return;
        }
        if (Character.isDigit(ch) || ch == '-') {
            if (ch != '-') {
                checkOper(1);
            }
            curToken = TokenValue.NUM;
            index--;
            constant = readNum();
            return;
        }

        index--;
        identifier = getIdentifier();

        if (identifier.length() == 0) {
            throw new WrongExpressionException("Unknown symbol at index: ", expression, index, Math.min(index + 1, expression.length()), makeServiceString(index, index));
        }

        for (int i = 0; i < ops.length; i++) {
            if (identifier.equals(ops[i].name)) {
                checkOper(ops[i].name.length());
                curToken = ops[i].token;
                if (curToken == TokenValue.VAR) {
                    var = ops[i].name;
                }
                return;
            }
        }

        switch (identifier) {
            case "mod":
                curToken = TokenValue.MOD;
                return;
            default:
                throw new WrongExpressionException("Unknown identifier at index: ", expression, index - identifier.length(), Math.min(index + 1, expression.length()), makeServiceString(index - identifier.length(), index));
        }
    }

    private class Op<OtherT> {
        final String name;
        final TokenValue token;

        Op(String name, TokenValue token) {
            this.name = name;
            this.token = token;
        }
    }

    private Op[] ops = new Op[]{
            new Op("square", TokenValue.SQR),
            new Op("abs", TokenValue.ABS),
            new Op("x", TokenValue.VAR),
            new Op("y", TokenValue.VAR),
            new Op("z", TokenValue.VAR)
    };

    private T readNum() throws WrongExpressionException {
        int beg = index;
        if (expression.charAt(index) == '-') {
            index++;
        }
        while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.' || expression.charAt(index) == 'e')) {
            index++;
        }
        try {
            return operator.parse(expression.substring(beg, index));
        } catch (WrongExpressionException e) {
            throw new WrongExpressionException("Constant is out of range of int at index: ", expression, beg, index, makeServiceString(beg, index));
        }
    }

    private TripleExpression<T> prim() throws WrongExpressionException {
        int beg = index;
        getToken();
        TripleExpression<T> exp;
        switch (curToken) {
            case VAR:
                exp = new Variable<T>(var);
                getToken();
                break;
            case NUM:
                exp = new Const<T>(constant);
                getToken();
                break;
            case MINUS:
                exp = new CheckedNegate<T>(prim(), operator);
                break;
            case ABS:
                exp = new CheckedAbs<T>(prim(), operator);
                break;
            case SQR:
                exp = new CheckedSquare<T>(prim(), operator);
                break;
            case LP:
                exp = addPriorityOper();
                if (curToken != TokenValue.RP) {
                    throw new WrongExpressionException("Wrong sequence of parenthesises: number of opening parenthesises is more than number of closing");
                }
                getToken();
                break;
            default:
                throw new WrongExpressionException("Missing the operand at index: ", expression, beg, index, makeServiceString(beg, index - identifier.length()));
        }
        return exp;
    }

    private TripleExpression<T> mulPriorityOper() throws WrongExpressionException {
        TripleExpression<T> left = prim();
        while (true) {
            switch (curToken) {
                case MUL:
                    left = new CheckedMultiply<T>(left, prim(), operator);
                    break;
                case DIV:
                    left = new CheckedDivide<T>(left, prim(), operator);
                    break;
                case MOD:
                    left = new CheckedMod<T>(left, prim(), operator);
                default:
                    return left;
            }
        }
    }

    private TripleExpression<T> addPriorityOper() throws WrongExpressionException {
        TripleExpression<T> left = mulPriorityOper();
        while (true) {
            switch (curToken) {
                case PLUS:
                    left = new CheckedAdd<T>(left, mulPriorityOper(), operator);
                    break;
                case MINUS:
                    left = new CheckedSubtract<T>(left, mulPriorityOper(), operator);
                    break;
                default:
                    return left;
            }
        }
    }

    public TripleExpression<T> parse(String expression) throws WrongExpressionException {
        this.expression = expression;
        index = 0;
        balance = 0;
        curToken = TokenValue.START;
        return addPriorityOper();
    }
}
