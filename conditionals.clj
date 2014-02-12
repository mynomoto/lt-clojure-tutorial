;; +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;; An Introduction to Clojure for Light Table users
;; Conditionals
;; +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

;; Instructions
;; ============================================================================
;; To begin, open this file on Light Table.

;; To evaluate a form place the cursor in the form and type ctrl-enter.
;; The first eval will take several seconds. Light Table is creating a
;; connection to a REPL. After the first it will be fast.

;; The result of each eval will apear right next to the form. Some forms like
;; the ns declaration return nil. This is ok. Also, sometimes, the result will
;; be cropped. You can click on it to see the full result.

;; You can put the cursor over some function and press ctrl-shift-d to see the
;; documentation of the function. Press ctrl-shift-d again to make it go away.

;; When you find a comment form you need to select the expressions inside and
;; them eval them. Usually they will cause an error.

(ns conditionals)


;; True, false, if and when
;; ============================================================================

;; Let's start with something basic. What's true and false in Clojure? When
;; you are dealing with conditionals there are only two values that are false:
;; `nil` and `false`. Everything else is true.

;; If takes three arguments: a test, the expression if the test is true and the
;; expression if the test if false. Try to guess the result before evaluating
;; the form.

(if false
  "The test is true."
  "The test is false.")

(if nil
  "The test is true."
  "The test is false.")

(if true
  "The test is true."
  "The test is false.")

(if [false]
  "The test is true."
  "The test is false.")

(if []
  "The test is true."
  "The test is false.")

(if "false"
  "The test is true."
  "The test is false.")

;; In Clojure only `nil` and `false` are considered logically false. Everything
;; else is true, including empty collections.

;; You don't need to give `if` the third argument. If you don't, it defaults to
;; `nil`. The following are the same.

(if false
  "The test is true.")

(if false
  "The test is true."
  nil)

;; But doing that is not idiomatic. You should use `when` in this case. `when`
;; takes a test and the value if the test is true. If the test is false, it
;; returns `nil`.

(when false
  "The test is true.")

(when true
  "The test is true.")

;; `when` also accepts more arguments. All extra argument are called when the
;; test is true. But only the last one is returned.

(when true
  (println "Look, the test is true")
  "The test is true.")

;; Check your console. To do that you can click in the number on the left of
;; the line number in the bottom bar, right side. Or you can type ctrl-space,
;; start typing `toggle console` and press enter when `Console: Toggle console`
;; is highlighted.
;; The following line will be there: `conditionals.clj:	Look, this is true`
;; `println` is a function that prints to the console by default.


;; Predicates
;; ============================================================================
;; But passing `true` or `false` to `if` and `when` is not fun. In Clojure,
;; functions that return true or false are known as predicates. Let's see
;; some of them. The function names are intuitive in general and usually,
;; predicates end in `?`. This is not a rule, it's a convention.

;; Is the number zero?
(zero? 0)

(zero? 4)

;; Is the number positive?
(pos? 10)

(pos? -5)

;; Is the number negative?
(neg? -5)

(neg? 10)

;; Is the integer even?
(even? 2)

(even? 3)

;; Is the integer odd?
(odd? 3)

(odd? 2)

;; Is the argument a number?
(number? 8)

(number? "ABC")

;; Is the argument a string?
(string? 8)

(string? "ABC")

;; Is the arguments distinct?
(distinct? 1 1 3)

(distinct? 1 2 3)

;; Is the collection empty?
(empty? [nil])

(empty? [])

;; Is the argument nil?
(nil? nil)

(nil? [])

;; There are many predicates in Clojure. To learn more go to the cheat sheet:
;; http://clojure.org/cheatsheet and find functions with `?` in the name.


;; Functions to compare
;; ============================================================================
;; Clojure also has functions used to compare arguments.

;; Let's start with `=`

;; With only one argument, `=` always returns true
(= 1)

(= false)

;; With two or more arguments, `=` returns true if every argument is equal.
(= 10 (* 2 5))

(= 10 (* 2 5) (- 15 5))

(= 10 (* 2 5) (- 15 5) (/ 20 2) (+ 3 7))

(= 10 (* 2 6))

;; But the number with different types are false to `=`.
(= 2 2.0)

(= 0.5 1/2)

;; Lists and vectors are equal if they have the same elements in the same order.
(= '(:a :b :c) [:a :b :c])

;; There is `==` if you want to know if number of different types have the same
;; value.
(== 2 2.0)

(== 0.5 1/2)

;; `not=` is the complement of `=`. That means that if `=` returns true, with
;; the same arguments `not=` returns false.
(not= 1)

(not= 10 (* 2 5))

(not= 10 (* 2 6))

;; I think you can guess what `<` and `>` do.

(> 1)

(> 2 1)

(> 1 3)

(> 7 4 3 1)

(> 7 4 1 1)

(< 0)

(< 2 3)

(< 3 2)

(< 1 4 9)

(< 1 4 9 9)

;; And `<=` and `>=`.

(>= 7)

(>= 7 7)

(>= 7 7 7 7 3 3)

(<= 1 4 9 9)

;; Now you know how to compare numbers. How do you compose this?

;; `and` and `or`
;; ============================================================================
;; `and` will take expressions and evaluate them from left to right one at a
;; time. If it find a false result it will return `false`. Else it will return
;; the value of the last expression`.

(and :a 1 3 (< 3 8) 30)

(and :a 1 3 (< 8 4))

;; `or` will take expressions and evaluate them from left to right one at a
;; time. If it finds a true result it will return the result. Else it will return
;; `false`.

(or nil 10)

(or nil false (< 10 2))

;; Let's go back to the conditionals.

;; when-not and if-not
;; ============================================================================
;; To make our lives easier Clojure gives us `if-not` and `when-not`. Take a
;; look.

(if-not (> 3 4)
  "The test is false."
  "The test is true.")

(if-not (< 1 2)
  "The test is false."
  "The test is true.")

(when-not (empty? [1 2 3])
  "The test is false.")

(when-not (nil? nil)
  "The test is false.")


;; cond and case
;; ============================================================================
;; But what should we do when we want more options? Clojure has `cond`.

(cond
 (string? 10) "This is a string."
 (nil? 10) "This is nil."
 (number? 10) "This is a number."
 :else "This is not a string, nil or a number.")

;; `cond` has pairs of arguments. Each pair is a test and the result if the
;; test is true. You can use `:else` as the last test and it will always be true
;; (remember that only `nil` and `false` are false).

(cond
 (string? :a) "This is a string."
 (nil? :a) "This is nil."
 (number? :a) "This is a number."
 :else "This is not a string, nil or a number.")

;; There is also `case`. It takes one expression and pairs of expression and
;; results. You can also give a last result (witout the expression) and it
;; will be the default result. Case compares the first expression with the
;; expression in each pair using `=`. If this returns `true` then the
;; correspondent result is returned

(case 10
  1 "The number is 1"
  2 "The number is 2"
  "The number is not 1 or 2")

;; You should give a default to case or it will give an error when it doesn't
;; match any pair.

(comment

  (case 10
    1 "The number is 1"
    2 "The number is 2")

  )

;; If you want to give the same answer to some matches you can use a list.

(case 4
  (2 4) "The number is even."
  (1 3) "The number is odd."
  "What???")

;; And your expression may be a collection.

(case [3 4]
  [1 2] "Vector [1 2]."
  [3 4] "Vector [3 4]."
  "Another vector.")

;; More complex stuff
;; ============================================================================
;; `condp` is more complex. Let's look an example and you should check the
;; docs.

(condp > 99
  10 "Smaller than 10"
  50 "Smaller than 50"
  100 "Smaller than 100"
  "Bigger than 100")

;; `condp` takes a function that accepts two arguments, a value, clauses and
;; an optional default value (like `case`). The clauses may be binary like in
;; the example above. In this case `condp` will test `(> x 99)` where x is
;; the first value in the binary clause. If this is true it returns the second
;; value in the clause.

;; Clauses may also be ternary. An example.

;; First lets create a function that accepts two arguments: a list of numbers
;; a number. It returns the list with only numbers bigger than the second
;; argument. If there is no numbers in the list bigger tha the argument,
;; it returns nil.

(defn filter-bigger [l n]
  (when (seq (filter (partial < n) l))
          (filter (partial < n) l)))

(condp filter-bigger 99
  [1 3 4 5] :>> #(map inc %)
  [1 3 4 105 120] :>> #(map inc %)
  "Bigger than 100")

;; In this case, condp will test (filter-bigger x 99) where x is the first
;; value in the ternary clause. If this is true, it will use the function (the
;; last argument in the ternary clause) and return the result of the function
;; with the test result as argument.

;; In the first clause `[1 3 4 5] :>> #(map inc %)` there is no number bigger,
;; than 99 in the list so the test is nil and that is not true.
;; In the second clause `[1 3 4 105 120] :>> #(map inc %)` the test returns
;; `(105 120)` which is true. The result becomes `(#(map inc %) (105 120))`
;; or (106 121).

;; `condp` may be complex, so take a while to digest it.
