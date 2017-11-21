package ru.itmo.ctddev.khusainov.parser;

import ru.itmo.ctddev.khusainov.expression.*;
/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression);
}