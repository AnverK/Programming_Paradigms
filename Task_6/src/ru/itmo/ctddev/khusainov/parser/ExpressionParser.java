package ru.itmo.ctddev.khusainov.parser;

import ru.itmo.ctddev.khusainov.expression.*;
//import ru.itmo.ctddev.khusainov.parser.*;
/**
 * Created by Anver on 26.03.2017.
 */
public class ExpressionParser implements Parser {

    //    private StringBuilder expression;
    private String expression;
    private int index, constant;
    private TokenValue curToken;
    private char var;

    private enum TokenValue {
        MUL, DIV, PLUS, MINUS, VAR, NUM, LP, RP, LSH, RSH, XOR, OR, AND, ABS, SQUARE;
    }

//    private void deleteWhiteSpace() {
//        int i = 0;
//        while (i < expression.length()) {
//            if (Character.isWhitespace(expression.charAt(i))) {
//                expression.deleteCharAt(i);
//            } else {
//                i++;
//            }
//        }
//    }

    private void skipWhiteSpace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void getToken() {
        skipWhiteSpace();
        if (index >= expression.length()) {
            return;
        }
        char ch = expression.charAt(index);
        index++;
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
                curToken = TokenValue.MINUS;
                return;
            case '(':
                curToken = TokenValue.LP;
                return;
            case ')':
                curToken = TokenValue.RP;
                return;
            case '&':
                curToken = TokenValue.AND;
                return;
            case '|':
                curToken = TokenValue.OR;
                return;
            case '^':
                curToken = TokenValue.XOR;
                return;
        }
        if (Character.isDigit(ch)) {
            curToken = TokenValue.NUM;
            index--;
            constant = readNum();
            return;
        }
        if (ch == 'x' || ch == 'y' || ch == 'z') {
            var = ch;
            curToken = TokenValue.VAR;
            return;
        }
        if (expression.substring(index - 1, index + 1).equals("<<")) {
            curToken = TokenValue.LSH;
            index++;
            return;
        }
        if (expression.substring(index - 1, index + 1).equals(">>")) {
            curToken = TokenValue.RSH;
            index++;
            return;
        }
        if (expression.substring(index - 1, index + 2).equals("abs")) {
            curToken = TokenValue.ABS;
            index += 2;
            return;
        }
        if (expression.substring(index - 1, index + 5).equals("square")) {
            curToken = TokenValue.SQUARE;
            index += 5;
        }
    }

    private int readNum() {
        int beg = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        return Integer.parseUnsignedInt(expression.substring(beg, index));
    }

    private TripleExpression prim() {
        getToken();
        TripleExpression exp;
        switch (curToken) {
            case VAR:
                exp = new Variable(Character.toString(var));
                getToken();
                break;
            case NUM:
                exp = new Const(constant);
                getToken();
                break;
            case MINUS:
                exp = new Minus(prim());
                break;
            case ABS:
                exp = new Abs(prim());
                break;
            case SQUARE:
                exp = new Square(prim());
                break;
            default:                                                        // case LP:
                exp = shiftPriorityOper();
                getToken();
        }
        return exp;
    }


    private TripleExpression mulPriorityOper() {
        TripleExpression left = prim();
        while (true) {
            switch (curToken) {
                case MUL:
                    left = new Multiply(left, prim());
                    break;
                case DIV:
                    left = new Divide(left, prim());
                    break;
                default:
                    return left;
            }
        }
    }


    private TripleExpression addPriorityOper() {
        TripleExpression left = mulPriorityOper();
        while (true) {
            switch (curToken) {
                case PLUS:
                    left = new Add(left, mulPriorityOper());
                    break;
                case MINUS:
                    left = new Subtract(left, mulPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression andPriorityOper() {
        TripleExpression left = addPriorityOper();
        while (true) {
            switch (curToken) {
                case AND:
                    left = new BitAnd(left, addPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression xorPriorityOper() {
        TripleExpression left = andPriorityOper();
        while (true) {
            switch (curToken) {
                case XOR:
                    left = new BitXor(left, andPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression orPriorityOper() {
        TripleExpression left = xorPriorityOper();
        while (true) {
            switch (curToken) {
                case OR:
                    left = new BitOr(left, xorPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    private TripleExpression shiftPriorityOper() {
        TripleExpression left = orPriorityOper();
        while (true) {
            switch (curToken) {
                case LSH:
                    left = new ShiftLeft(left, orPriorityOper());
                    break;
                case RSH:
                    left = new ShiftRight(left, orPriorityOper());
                    break;
                default:
                    return left;
            }
        }
    }

    public TripleExpression parse(String expression) {
//        this.expression = new StringBuilder(expression);
//        deleteWhiteSpace();
        this.expression = expression;
        index = 0;
        return shiftPriorityOper();
    }
}
