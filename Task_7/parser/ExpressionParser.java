package expression.parser;

import expression.exceptions.*;
import expression.*;

/**
 * Created by Anver on 26.03.2017.
 */
public class ExpressionParser implements Parser {

    private String expression, identifier;
    private int index, constant, seqBalance;
    private TokenValue curToken;
    private String var;

    private enum TokenValue {
        MUL, DIV, PLUS, MINUS, VAR, NUM, LP, RP, LOG, POW, START, ABS, SQRT, MIN, MAX, END;
    }

    private StringBuilder makeServiceString(int beg, int end) {
        StringBuilder s = new StringBuilder(end);
        for (int i = 0; i < beg; i++) {
            s.append(' ');
        }
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

    private void checkOperation(int shift) throws WrongExpressionException {
        index -= shift;
        if (curToken == TokenValue.RP || curToken == TokenValue.NUM || curToken == TokenValue.VAR) {
            throw new WrongExpressionException("Missing binary operator at index: ", expression, index, index + shift, makeServiceString(index, index));
        }
        index += shift;
    }

    private void getToken() throws WrongExpressionException {
        skipWhiteSpace();
        if (index >= expression.length()) {
            if (curToken == TokenValue.END) {
                throw new WrongExpressionException("Missing an operand at index: ", expression, index, index, makeServiceString(index, index));
            }
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
                    throw new WrongExpressionException("Missing an operand at index: ", expression, index, index, makeServiceString(index, index));
                }
                if (Character.isDigit(expression.charAt(index)) && (curToken == TokenValue.START || (curToken != TokenValue.NUM && curToken != TokenValue.VAR && curToken != TokenValue.RP))) {
                    break;
                }
                curToken = TokenValue.MINUS;
                return;
            case '(':
                checkOperation(1);
                curToken = TokenValue.LP;
                seqBalance++;
                return;
            case ')':
                curToken = TokenValue.RP;
                seqBalance--;
                if (seqBalance < 0) {
                    throw new WrongExpressionException("Excessive closing parenthesis at index: ", expression, index, index + 1, makeServiceString(index, index));
                }
                return;
        }

        if (Character.isDigit(ch) || ch == '-') {
            if (ch != '-') {
                checkOperation(1);
            }
            curToken = TokenValue.NUM;
            index--;
            constant = readNum();
            return;
        }
        index--;
        identifier = getIdentifier();
        if (identifier.length() == 0) {
            throw new WrongExpressionException("Unknown symbol at index: ", expression, index, min(index + 1, expression.length()), makeServiceString(index, index));
        }

        for (int i = 0; i < ops.length; i++) {
            if (identifier.equals(ops[i].name)) {
                checkOperation(ops[i].name.length());
                curToken = ops[i].token;
                if (curToken == TokenValue.VAR) {
                    var = ops[i].name;
                }
                return;
            }
        }

        switch (identifier) {
            case "min":
                curToken = TokenValue.MIN;
                return;
            case "max":
                curToken = TokenValue.MAX;
                return;
            default:
                throw new WrongExpressionException("Unknown identifier at index: ", expression, index - identifier.length(), min(index + 1, expression.length()), makeServiceString(index - identifier.length(), index));
        }
    }

    private class Op {
        final String name;
        final TokenValue token;

        Op(String name, TokenValue token) {
            this.name = name;
            this.token = token;
        }
    }

    private Op[] ops = new Op[]{
            new Op("log2", TokenValue.LOG), new Op("pow2", TokenValue.POW), new Op("sqrt", TokenValue.SQRT),
            new Op("abs", TokenValue.ABS), new Op("x", TokenValue.VAR), new Op("y", TokenValue.VAR),
            new Op("z", TokenValue.VAR)
    };

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    private int readNum() throws WrongExpressionException {
        int beg = index;
        if (expression.charAt(index) == '-') {
            index++;
        }
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        try {
            return Integer.parseInt(expression.substring(beg, index));
        } catch (NumberFormatException e) {
            throw new WrongExpressionException("Constant is out of range of int at index: ", expression, beg, index, makeServiceString(beg, index));
        }
    }

    private TripleExpression prim() throws WrongExpressionException {
        int beg = index;
        getToken();
        TripleExpression exp;
        switch (curToken) {
            case VAR:
                exp = new Variable(var);
                getToken();
                break;
            case NUM:
                exp = new Const(constant);
                getToken();
                break;
            case MINUS:
                exp = new CheckedNegate(prim());
                break;
            case LOG:
                exp = new CheckedLog(prim());
                break;
            case POW:
                exp = new CheckedPow(prim());
                break;
            case ABS:
                exp = new CheckedAbs(prim());
                break;
            case SQRT:
                exp = new CheckedSqrt(prim());
                break;
            case LP:
                exp = minPriorityOper();
                getToken();
                break;
            default:
                throw new WrongExpressionException("Missing an operand at index: ", expression, beg, index, makeServiceString(beg, index - identifier.length()));
//                throw new WrongExpressionException("Unexpected symbol at index: " + (index - 1));
        }
        return exp;
    }

    private TripleExpression mulPriorityOper() throws WrongExpressionException {
        TripleExpression left = prim();
        while (true) {
            switch (curToken) {
                case MUL:
                    left = new CheckedMultiply(left, prim());
                    break;
                case DIV:
                    left = new CheckedDivide(left, prim());
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression addPriorityOper() throws WrongExpressionException {
        TripleExpression left = mulPriorityOper();
        while (true) {
            switch (curToken) {
                case PLUS:
                    left = new CheckedAdd(left, mulPriorityOper());
                    break;
                case MINUS:
                    left = new CheckedSubtract(left, mulPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression minPriorityOper() throws WrongExpressionException {
        TripleExpression left = addPriorityOper();
        while (true) {
            switch (curToken) {
                case MIN:
                    left = new CheckedMin(left, addPriorityOper());
                    break;
                case MAX:
                    left = new CheckedMax(left, addPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    public TripleExpression parse(String expression) throws WrongExpressionException {
        this.expression = expression;
        seqBalance = 0;
        index = 0;
        curToken = TokenValue.START;
        TripleExpression exp = minPriorityOper();
        if (seqBalance != 0) {
            throw new WrongExpressionException("Wrong sequence of parenthesises: number of opening parenthesises is more than number of closing");
        }
        return exp;
    }
}
