"use strict"

var varNames = {
    "x": 0,
    "y": 1,
    "z": 2
};

var constNames = {
    "pi": Math.PI,
    "e": Math.E
};

var variable = function (name) {
    return function () {
        return arguments[varNames[name]];
    }
};

var cnst = function (value) {
    return function () {
        return value;
    }
};

for (var name in constNames) {
    this[name] = cnst(constNames[name]);
}

for (var name in varNames) {
    this[name] = variable(name);
}

var operator = function (myFunction) {
    return function () {
        var args = arguments;
        return function () {
            var operands = [];
            for (var i = 0; i < args.length; i++) {
                operands.push(args[i].apply(null, arguments));
            }
            return myFunction.apply(null, operands);
        }
    }
};

var add = operator(function (a, b) {
    return a + b;
});

var subtract = operator(function (a, b) {
    return a - b;
});

var multiply = operator(function (a, b) {
    return a * b;
});
var divide = operator(function (a, b) {
    return a / b;
});

var negate = operator(function (a) {
    return -a;
});

var min3 = operator(function () {
    return Math.min.apply(null, arguments);
});

var max5 = operator(function () {
    return Math.max.apply(null, arguments);
});

var getNextToken = function (string, index) {
    var reg = /\s/, end = index;
    while ((end < string.length) && (!reg.test(string.charAt(end)))) {
        end++;
    }
    return string.substring(index, end);
};


var parse = function (string) {
    var stack = [];
    var OPERATIONS = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
        "negate": negate,
        "min3": min3,
        "max5": max5
    };
    var NUMBER_OF_ARGUMENTS = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "min3": 3,
        "max5": 5
    };
    for (var i = 0; i < string.length; i++) {
        var reg = /\s/;
        while ((i < string.length) && (reg.test(string.charAt(i)))) {
            i++;
        }
        if (i === string.length) {
            break;
        }
        var token = getNextToken(string, i);
        i += token.length;
        if (token in OPERATIONS) {
            var args = [];
            for (var j = 0; j < NUMBER_OF_ARGUMENTS[token]; j++) {
                args.push(stack.pop());
            }
            args.reverse();
            stack.push(OPERATIONS[token].apply(null, args));
        } else if (token in varNames) {
            stack.push(variable(token));
        } else if (token in constNames) {
            stack.push(cnst(constNames[token]));
        } else {
            stack.push(cnst(parseInt(token)));
        }


    }
    return stack.pop();
};

// var expr = parse("x 2 3 min3")
// var expr = min3(cnst(1), cnst(2), cnst(3));
// console.log(expr(1));

