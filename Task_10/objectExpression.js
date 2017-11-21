"use strict";

var varNames = {
    "x": 0,
    "y": 1,
    "z": 2
};

function Const(value) {
    this.value = value;
}
Const.ZERO = new Const(0);
Const.ONE = new Const(1);
Const.TWO = new Const(2);

Const.prototype.evaluate = function () {
    return this.value;
};
Const.prototype.toString = function () {
    return this.value.toString();
};
Const.prototype.diff = function () {
    return Const.ZERO;
};

function Variable(name) {
    this.name = name;
    this.index = varNames[name];
}
Variable.prototype.evaluate = function () {
    return arguments[this.index];
};
Variable.prototype.toString = function () {
    return this.name;
};
Variable.prototype.diff = function (x) {
    return x === this.name ? Const.ONE : Const.ZERO;
};

function AbstractOperator() {
    this.operands = [].slice.call(arguments);
}
AbstractOperator.prototype.evaluate = function () {
    var args = arguments;
    var res = this.operands.map(function (value) {
        return value.evaluate.apply(value, args)
    });
    return this.action.apply(null, res);
};
AbstractOperator.prototype.toString = function () {
    return this.operands.join(" ") + " " + this.operator;
};
AbstractOperator.prototype.diff = function (x) {
    return this.diffRule.apply(this,
        this.operands.concat(
            this.operands.map(function (value) {
                return value.diff(x)
            })));
};

function OperatorFactory(operands, operator, action, diff) {
    this.operands = operands;
    this.operator = operator;
    this.action = action;
    this.diffRule = diff;
    AbstractOperator.apply(this, operands);
}

function Add() {
    OperatorFactory.call(this, arguments, "+",
        function (a, b) {
            return a + b;
        },
        function (a, b, da, db) {
            return new Add(da, db);
        }
    );
}

function Subtract() {
    OperatorFactory.call(this, arguments, "-",
        function (a, b) {
            return a - b;
        },
        function (a, b, da, db) {
            return new Subtract(da, db);
        }
    );
}

function Multiply() {
    OperatorFactory.call(this, arguments, "*",
        function (a, b) {
            return a * b;
        },
        function (a, b, da, db) {
            return new Add(
                new Multiply(da, b),
                new Multiply(db, a));
        }
    );
}

function Divide() {
    OperatorFactory.call(this, arguments, "/",
        function (a, b) {
            return a / b;
        },
        function (a, b, da, db) {
            return new Divide(
                new Subtract(
                    new Multiply(da, b),
                    new Multiply(db, a)),
                new Multiply(b, b));
        }
    );
}


function Negate() {
    OperatorFactory.call(this, arguments, "negate",
        function (a) {
            return -a;
        },
        function (a, da) {
            return new Negate(da);
        }
    );
}

function Sinh() {
    OperatorFactory.call(this, arguments, "sinh",
        function (a) {
            return ((Math.exp(a) - Math.exp(-a)) / 2);
        },
        function (a, da) {
            return new Multiply(new Cosh(a), da);
        }
    );
}

function Cosh() {
    OperatorFactory.call(this, arguments, "cosh",
        function (a) {
            return ((Math.exp(a) + Math.exp(-a)) / 2);
        },
        function (a, da) {
            return new Multiply(new Sinh(a), da);
        }
    );
}

function Square() {
    OperatorFactory.call(this, arguments, "square",
        function (a) {
            return a * a;
        },
        function (a, da) {
            return new Multiply(Const.TWO, new Multiply(a, da));
        }
    );
}

function Abs() {
    OperatorFactory.call(this, arguments, "abs",
        function (a) {
            return Math.abs(a);
        },
        function (a, da) {
            return new Multiply(
                new Divide(a, new Abs(a)),
                da
            );
        }
    );
}

function Sqrt() {
    OperatorFactory.call(this, arguments, "sqrt",
        function (a) {
            return Math.pow(Math.abs(a), 0.5);
        },
        function (a, da) {
            return new Multiply(
                new Divide(a, new Multiply(
                    Const.TWO, new Multiply(
                        new Abs(a), new Sqrt(new Abs(a))))), da);
        }
    );
}

var myFunctions = [Add, Subtract, Multiply, Divide, Negate, Sinh, Cosh, Square, Sqrt, Abs];

for (var i = 0; i < myFunctions.length; i++) {
    myFunctions[i].prototype = Object.create(AbstractOperator.prototype);
}

var parse = function (string) {
    var stack = [];
    var OPERATIONS = {
        "+": Add,
        "-": Subtract,
        "*": Multiply,
        "/": Divide,
        "negate": Negate,
        "sinh": Sinh,
        "cosh": Cosh,
        "square": Square,
        "sqrt": Sqrt,
        "abs": Abs
    };
    var NUMBER_OF_ARGUMENTS = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "sinh": 1,
        "cosh": 1,
        "square": 1,
        "sqrt": 1,
        "abs": 1
    };
    var arr_token = string.trim().split(/\s+/);
    for (var i = 0; i < arr_token.length; i++) {
        var token = arr_token[i];
        if (token in OPERATIONS) {
            var args = stack.splice(-NUMBER_OF_ARGUMENTS[token], NUMBER_OF_ARGUMENTS[token]);
            var operator = Object.create(OPERATIONS[token].prototype);
            OPERATIONS[token].apply(operator, args);
            stack.push(operator);
        }
        else {
            if (token in varNames) {
                stack.push(new Variable(token));
            } else {
                stack.push(new Const(parseInt(token)));
            }
        }
    }
    return stack.pop();
};