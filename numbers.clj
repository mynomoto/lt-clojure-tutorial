;; +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;; An Introduction to Clojure for Light Table users
;; Numbers
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


(ns numbers)

;; In Clojure there are several types of numbers useful in different
;; situations. Here you can see some of this types. Several of them are just
;; java numbers.

;; Integers
;; ============================================================================
;; Integers in clojure are Java Long by default.


;; Long
;; ----------------------------------------------------------------------------

(type 1)

(type 4738724)

;; You can use octal numbers just stating with 0

(type 017)

(str 017)

;; And hexadecimal numbers stating with 0x

(type 0x1F)

(str 0x1F)

;; There is the r notation for bases until 36

(type 36rZ)

(str 36rZ)

(type 2r11)

(str 2r11)

;; You can find what is the minimum and maximum Long.

Long/MIN_VALUE

Long/MAX_VALUE

;; But what if you need a bigger or smaller number?
;; Clojure has `inc` that adds one to a number and `dec` that subtracts one.

(comment

  (inc Long/MAX_VALUE)

  (dec Long/MIN_VALUE)

  )

;; You get an exception: java.lang.ArithmeticException: integer overflow

;; You can coerce other numbers to Long.

(type (long 1N))


;; BigInt
;; ----------------------------------------------------------------------------
;; Clojure offers you a BigInt type that offer arbitrary precision.
;; Just add an `N` to the end of the number. Clojure BigInt is not
;; Java BigInteger.

(type 2N)

(str 2N)

;; You can coerce a Long in a BigInt with `bigint`. Notice the `N` in the end
;; of the result.

(bigint Long/MAX_VALUE)

;; Now we can increment that without and error.

(inc (bigint Long/MAX_VALUE))


;; Other Integers
;; ----------------------------------------------------------------------------
;; We can use `byte`, `short`, `int` and `biginteger` to coerce to those types.

(type (byte 127))

(type (short 10))

(type (int 1))

(type (biginteger 800))

;; There is also the interop option to get instances of these numbers.

(Byte. "70")

(type (Byte. "70"))

(Short. "10")

(type (Short. "10"))

(Integer. 4)

(type (Integer. 4))

(BigInteger. "4")

(type (BigInteger. "4"))


;; Is a number a integer?
;; ----------------------------------------------------------------------------
;; You can test with `integer?`

(integer? 1)

(integer? 1N)

(integer? (byte 127))

(integer? (short 10))

(integer? (int 1))

(integer? (biginteger 80))

;; Floats and ratios aren't integers.

(integer? 0.10)

(integer? 4/3)


;; Float
;; ============================================================================
;; Float numbers default to Java Doubles.


;; Double
;; ----------------------------------------------------------------------------

(type 1.5)

;; You can also use scientific notation.

(type 1E10)

(type 3.5E-6)

;; You can coerce to Double using `double`

(double 1)

;; Floats may do unexpected things.

(str 0.999999999999999999)

;; That happens because there are limitations on the precision of Floats and
;; Doubles. They are aproximations. If you need precision you can use the Ratio
;; or BigDecimal type. We'll talk about these two in a moment.


;; Float
;; ----------------------------------------------------------------------------
;; Floats are less precise than Doubles, but you can coerce to Float using
;; `float`.

(type (float 1.5))

;; There is also the interop.

(Float. "10")


;; Is a number a float?
;; ----------------------------------------------------------------------------
;; You can check if a number is a float (Double or Float) with `float?`

(float? 10.2)

(float? (float 10.2))

;; Integers and ratios are not floats.

(float? 1)

(float? 1/2)


;; Ratio
;; ============================================================================
;; Clojure has a Ratio type to represent rational numbers.

(type 3/4)

;; You can coerce to ratio with `rationalize`.

(rationalize 0.5)

;; You can get the numerator of the ratio with `numerator`. And the denominator
;; with `denominator`

(numerator 4/6)

(denominator 4/6)

;; What just happened? Clojure simplifies your ratio it's possible. So `4/6`
;; becomes `2/3`.

(str 4/6)


;; Is a number a ratio?
;; ----------------------------------------------------------------------------
;; You can check if a number is a ratio with `ratio?`

(ratio? 7/5)

;; Integers are not ratios. Even when they look like one.

(ratio? 1)

(ratio? 1N)

(ratio? 10/5)

(ratio? 4712389758912758912074901230/2)

;; `10/5` is not a Ratio and neither is `4712389758912758912074901230/2`. When
;; simplification is possible ratios will be coerced to Long or BigInt.

(type 10/5)

(type 4712389758912758912074901230/2)

;; Floats are not ratios as well

(ratio? 0.5)


;; BigDecimal
;; ============================================================================
;; Clojure has a BigDecimal type to represent rational numbers. It's a Java
;; BigDecimal. To create one, you add a `M` to the end of the number.

(type 13.5M)

(type 1M)

;; You can coerce a number to BigDecimal with `bigdec`.

(bigdec 1/2)

(bigdec 2)

;; You can define the precision and rounding in a BigDecimal math operation
;; with `with-precision`. Here is some examples.

(with-precision 0 (inc 1.3111111M))

(with-precision 1 (inc 1.3111111M))

(with-precision 2 (inc 1.3111111M))

(with-precision 3 (inc 1.3111111M))

(with-precision 2 :rounding UP (inc 1.3111111M))

(with-precision 2 :rounding DOWN (inc 1.3111111M))

;; We will see more about this in the math tutorial.


;; Is a number a BigDecimal?
;; ----------------------------------------------------------------------------
;; You can check if a number is a BigDecimal with `decimal?`

(decimal? 10M)

;; Integers, floats and ratios are not BigDecimal.

(decimal? 1)

(decimal? 0.5)

(decimal? 1/2)


;; Next
;; ============================================================================
;; In another tutorial we will see math operations, what happens when we
;; use different type of numbers in a operation and how to compare numbers.
