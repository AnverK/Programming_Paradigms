(defn operator [action]
  (fn [& ops]
    (fn [args] (apply action (map (fn [x] (x args)) ops)))
    )
  )

;(defn safeOperator [action, exception]
;  (fn [& ops]
;    (fn [args]
;      (try
;        (apply action (map (fn [x] (x args)) ops))
;        (catch Exception e exception)
;        ))
;    )
;  )

(defn constant [v]
  (fn [vars] (double v)))

(defn variable [name]
  (fn [vars] (double (get vars name))))


(def add (operator +))
(def subtract (operator -))
(def multiply (operator *))
(def divide (operator (fn [x y] (/ (double x) (double y)))))
;(def divide (safeOperator /, (/ 1.0 0.0)))

(def negate (operator -))

(def sin (operator (fn [x] (Math/sin (double x)))))
(def cos (operator (fn [x] (Math/cos (double x)))))
(def sinh (operator (fn [x] (Math/sinh (double x)))))
(def cosh (operator (fn [x] (Math/cosh (double x)))))
(def sqrt (operator (fn [x] (Math/sqrt (Math/abs (double x) )))))
(def square (operator (fn [x] (* (double x) (double x)))))

(def op {'+    add, '- subtract, '* multiply, '/ divide, 'negate negate,
         'sin  sin, 'cos cos,
         'sinh sinh, 'cosh, cosh
         'sqrt sqrt, 'square square
         })

(defn parseFunction [expression]
  (cond
    (string? expression) (parseFunction (read-string expression))
    (seq? expression) (apply (get op (first expression)) (map parseFunction (rest expression)))
    (float? expression) (constant expression)
    (symbol? expression) (variable (str expression))
    )
  )