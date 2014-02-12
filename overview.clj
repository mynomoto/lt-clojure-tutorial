;; +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;; An Introduction to Clojure for Light Table users
;; Overview
;; +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

;; Credits
;; ============================================================================
;; This overview is a port of the Light Table ClojureScript Tutorial by David
;; Nolen (https://github.com/swannodette/lt-cljs-tutorial) with some changes.


;; Basics
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

;; IMPORTANT: You must evaluate the very first form, the namespace
;; definition.

;; Declaring a namespaces
;; ----------------------------------------------------------------------------

;; Clojure supports modularity via namespaces. They allow you to group
;; logical definitions together.

(ns overview
  (:require [clojure.string :as string]))

;; :require is how you can import functionality from a different namespace into
;; the current one. Here we are requiring `clojure.string` and giving it an
;; alias `string`. We could write the following:

(clojure.string/blank? "")

;; Here `blank?` is a function on the `clojure.string` namespace. But since we
;; gave it an alias let's use it because it's less verbose.

(string/blank? "")

;; You could use a smaller name after :as like [clojure.string :as s].
;; In this case the expression above should be (s/blank? "").


;; Using functions
;; ----------------------------------------------------------------------------

;; Going back a little, to call a function on clojure you put it in the
;; first spot of a list. The rest of the list are the argument of the function.
;; A list is something between `(` and `)`. So `(a-function arg1 arg2)` is a
;; function call with two arguments.

(str "Hello " "Clojure!")

;; Here `str` is our function, `"Hello "` is the fist argument and `"Clojure!"`
;; is the second argument.


;; Comments
;; ----------------------------------------------------------------------------

;; There are three ways to create comments in Clojure. The first way is
;; by preceding a line with a semi-colon, just like the lines you are reading
;; now. This doesn't exist to Clojure, in the sense that it doesn't return
;; anything, not even nil.

;; The second way is by preceding a form with `#_`. This causes Clojure
;; to skip the evaluation of only the form immediately following, without
;; affecting the evaluation of the surrounding forms. This form will also
;; not exist for clojure.

;; Try to reveal the secret message below:

(str "The secret word is " #_(string/reverse "erujolC"))

;; You can also compose `#_`. So `#_#_` will ignore the next two forms.
;; Just compose it at will.

(str #_#_(str "Ignoring this...") (str "and this...") (str "but not this."))

;; Finally, you can also create a comment using the `comment` macro. One common
;; technique is to use the `comment` macro to include code to be evaluated in a
;; REPL, but which you do not normally want to be included in the compiled
;; source.

;; For example, try placing your cursor after the last `)` below and type
;; ctrl-ENTER:

(comment

  (string/upper-case "This is only a test...")

  )

;; The `comment` macro makes the whole form return `nil`. Now go back and
;; highlight just the middle line, then type ctrl-enter. In this way
;; you can include code samples or quick tests in-line with the rest of
;; your code. In this tutorial, you will find some code commented in this way.
;; You should select and run the commented code.


;; Definitions
;; ----------------------------------------------------------------------------

;; Once you have a namespace, you can start creating top level definitions in
;; that namespace.

;; You can define a top level with `def`.

(def x 1)

x

;; And you can define a local with `let`.

(let [x 2]
  x)

(let [a 1
      b 4]
  [a b])

;; The locals are defined inside the `[]`. And the scope is the let form.

;; You can also refer to top level definitions by fully qualifying them.

overview/x

;; This means top levels can never be really shadowed by locals and function
;; parameters.

(let [x 2]
  overview/x)

;; One way to define a function is like this.

(def y (fn [] 1))

(y)

;; Defining functions in Clojure is common enough that `defn` sugar is
;; provided and idiomatic.

(defn z [] 1)

(z)


;; Function literals
;; ----------------------------------------------------------------------------

;; Clojure also supports a shorthand function literal  `#()` which is useful.
;; The two examples below are equivalent.

((fn [] (str "A function")))

(#(str "A function"))

;; You can use the % and %N placeholders to represent function arguments.

((fn [x] (str "One argument: " x)) 1)

(#(str "One argument: " %) 1)

;; With two arguments.

((fn [x y] (str "Two arguments: " x " and " y)) 3 4)

(#(str "Two arguments: " %1 " and " %2) 3 4)


;; Literal data types
;; ----------------------------------------------------------------------------

;; Clojure comes out of the box with the usual useful data literals. These are
;; in several cases java literals. Here are some examples.

;; Booleans

(def a-boolean true)
(class a-boolean)

;; Strings

(def a-string "Hello!")
(class a-string)

;; Regular Expressions

(def a-regexp #"\d{3}-?\d{3}-?\d{4}")
(class a-regexp)

;; Numbers (Integer)

(def a-number 1)
(class a-number)

;; Numbers (Float)

(def another-number 1.0)
(class another-number)

;; Keywords

(def a-keyword :a)
(class a-keyword)

;; Keywords are an example of a clojure literal.


;; Clojure data types
;; ============================================================================

;; Unless there is a good reason, you should generally write your Clojure
;; programs with Clojure data types. They have many advantages over
;; Java data types - they present a uniform API and they are immutable.

;; Vectors
;; ----------------------------------------------------------------------------

;; Instead of arrays, Clojure programmers use persistent vectors. They are
;; like arrays - they support efficient random access, efficient update
;; and efficient addition to the end.

(def a-vector [1 2 3 4 5])

;; We can get the length of a vector in constant time via `count`.

(count a-vector)

;; We can add an element to the end.

(def another-vector (conj a-vector 6))

;; Note this does not mutate the array! `a-vector` will be left
;; unchanged.

a-vector

another-vector

;; Hallelujah! Here is where some Clojure magic
;; happens. `another-vector` appears to be a completely new vector
;; compared to `a-vector`. But it is not really so. Internally, the new
;; vector efficiently shares the `a-vector` structure. In this way, you
;; get the benefits of immutability without paying in performance.

;; We can access any element in a vector with `nth`. The following
;; will return the second element.

(nth a-vector 1)

(nth ["foo" "bar" "baz"] 1)

;; Or with `get`...

(get a-vector 0)

;; ...which allows you to return an alternate value when the index is
;; out-of bounds.

(get a-vector -1 :out-of-bounds)
(get a-vector (count a-vector) :out-of-bounds)

;; Surprisingly, vectors can be treated as functions. This is actually
;; a very useful property for associative data structures to have as
;; we'll see below with sets.

(a-vector 1)

(["foo" "bar" "baz"] 1)


;; Maps
;; ----------------------------------------------------------------------------

;; Along with vectors, maps are the most common data type in Clojure.
;; Clojure maps are immutable and flexible.

;; Let's define a simple map. Note `:foo` is a Clojure keyword.
;; Clojure programmers prefer to use keywords for keys instead
;; of strings. They are more distinguishable from the rest of the
;; code, more efficient than plain strings, and they can be used in
;; function position (i.e. first position after the open parens), as
;; we'll see in a moment.

(def a-map {:foo "bar" :baz "woz"})

;; We can get the number of key-value pairs in constant time.

(count a-map)

;; We can access a particular value for a key with `get`.

(get a-map :foo)

;; and return an alternative value when the key is not present

(get a-map :bar :not-found)

;; We can add a new key-value pair with `assoc`.

(def another-map (assoc a-map :noz "goz"))

;; Again a-map is unchanged! Same magic as before for vectors

a-map

another-map

;; We can remove a key-value pair with `dissoc`.

(dissoc a-map :foo)

;; Again a-map is unchanged!

a-map

;; Like vectors, maps can act like functions.

(a-map :foo)

;; However Clojure keywords themselves can act like functions and the
;; following is more idiomatic.

(:foo a-map)

;; We can check if a map contains a key, with `contains?`.

(contains? a-map :foo)

;; We can get all the keys in a map with `keys`.

(keys a-map)

;; And all of the values with `vals`.

(vals a-map)

;; We can put a lot of things in a map, even other maps
(def a-nested-map {:customer-id 1e6
                   :preferences {:nickname "Bob"
                                 :avatar "http://en.gravatar.com/userimage/0/0.jpg"}
                   :services {:alerts {:daily true}}})

;; and navigate its keys to get the nested value you're interested in

(get-in a-nested-map [:preferences :nickname])
(get-in a-nested-map [:services :alerts :daily])

;; or just find a top level key-value pair (i.e. MapEntry) by key

(find a-nested-map :customer-id)
(find a-nested-map :services)

;; There are many cool ways to create maps.

(zipmap [:foo :bar :baz] [1 2 3])

(hash-map :foo 1 :bar 2 :baz 3)

(apply hash-map [:foo 1 :bar 2 :baz 3])

(into {} [[:foo 1] [:bar 2] [:baz 3]])

;; Clojure maps support complex keys.

(def complex-map {[1 2] :one-two [3 4] :three-four})

(get complex-map [3 4])


;; Keyword digression
;; ----------------------------------------------------------------------------

;; Let's take a moment to digress about keywords as they are so ubiquitous
;; in Clojure code.

(identity :foo)

;; If you add an additional preceding colon you'll get a namespaced keyword.

(identity ::foo)

;; What good is this for? It allows you to put data into collections without
;; fear of namespace clashes without the tedium of manual namespacing them
;; in your source.

(identity {:user/foo ::foo})

;; Namespaced keywords are essential to Light Table's modularity.


;; Sets
;; ----------------------------------------------------------------------------

;; Clojure also supports sets.

(def a-set #{:cat :dog :bird})

;; `:cat` is already in `a-set`, so it will be unchanged.

(conj a-set :cat)

;; But `:zebra` isn't.

(conj a-set :zebra)

;; If you haven't guessed already, `conj` is a "polymorphic" function that adds
;; an item to a collection. This is some of the uniformity we alluded to
;; earlier.

;; `contains?` works on sets just like it does on maps.

(contains? a-set :cat)

;; Like vectors and maps, sets can also act as functions. If the argument
;; exists in the set it will be returned, otherwise the set will return nil.

(#{:cat :dog :bird} :cat)

;; This is powerful when combined with conditionals.

(defn check [x]
  (if (#{:cat :dog :bird} x)
    :valid
    :invalid))

(check :cat)
(check :zebra)


;; Lists
;; ----------------------------------------------------------------------------

;; A less common Clojure data structure is lists. This may be
;; surprising as Clojure is a Lisp, but maps, vectors and sets
;; are the 'go-to' data structures for most applications. Still, lists are sometimes
;; useful—especially when dealing with code (i.e. code is data).

(def a-list '(:foo :bar :baz))

;; `conj` is "polymorphic" on lists as well, and it's smart enough to
;; add the new item in the most efficient way on the basis of the
;; collection type.
(conj a-list :front)

;; and lists are immutable as well

a-list

;; You can get the first element of a list

(first a-list)

;; or the tail of a list

(rest a-list)

;; which allows you to easly verify how Clojure shares data
;; structure instead of inefficiently copying data for supporting
;; immutability.

(def another-list (conj a-list :front))

another-list

a-list

(identical? (rest another-list) a-list)

;; `identical?` checks whether two things are represented by the same
;; thing in memory.


;; Equality
;; ============================================================================

;; In Clojure equality is always deep equality.

(= {:foo "bar" :baz "woz"} {:foo "bar" :baz "woz"})

;; Maps are not ordered.

(= {:foo "bar" :baz "woz"} {:baz "woz" :foo "bar"})

;; For sequential collections, equality just works.

(= [1 2 3] '(1 2 3))

;; Again, it is possible to check whether two things are represented
;; by the same thing in memory with `identical?`.

(def my-vec [1 2 3])
(def your-vec [1 2 3])

(identical? my-vec your-vec)


;; Control
;; ============================================================================

;; In order to write useful programs, we need to be able to express
;; control flow.

;; if
;; ----------------------------------------------------------------------------

;; 0 is not a false-y value.

(if 0
  "Zero is not false-y"
  "Yuck")

;; Nor is the empty string.

(if ""
  "An empty string is not false-y"
  "Yuck")

;; the empty vector

(if []
  "An empty vector is not false-y"
  "Yuck")

;; the empty list

(if ()
  "An empty list is not false-y"
  "Yuck")

;; the empty map

(if {}
  "An empty map is not false-y"
  "Yuck")

;; the empty set

(if #{}
  "An empty set is not false-y"
  "Yuck")

;; and even the empty regexp

(if #""
  "An empty regexp is not false-y"
  "Yuck")

;; The only false-y values in Clojure are `nil` and `false`.


;; cond
;; ----------------------------------------------------------------------------

;; Nesting `if` tends to be noisy and hard to read so Clojure
;; provides a `cond` macro to deal with this.

(cond
  nil "Not going to return this"
  false "Nope not going to return this either"
  :else "Default case")


;; loop/recur
;; ----------------------------------------------------------------------------

;; The most primitive looping construct in Clojure is `loop`/`recur`.
;; Like `let`, `loop` establishes bindings and allows you to set their initial values.
;; Like `let`, you may have a sequence of forms for the body. In tail
;; positions, you may write a `recur` statement that will set the bindings for
;; the next iteration of the `loop`. Using `loop`/`recur` is usually considered bad
;; style if a reasonable functional solution via `map`/`filter`/`reduce` or a list
;; comprehension is possible.

;; In Clojure you would write `loop`/`recur` like so:

(loop [i 0 ret []]
  (if (< i 10)
    (recur (inc i) (conj ret i))
    ret))

;; Again avoid `loop`/`recur` unless you really need it. The loop above would
;; be better expressed as the following:

(into [] (range 10))


;; Moar functions
;; ============================================================================

;; Functions are the essence of any significant Clojure program, so
;; we will dive into features that are unique to Clojure functions that
;; might be unfamiliar.

;; Here is a simple function that takes two arguments and adds them.

(defn foo1 [a b]
  (+ a b))

(foo1 1 2)

;; Functions can have multiple arities.

(defn foo2
  ([a b] (+ a b))
  ([a b c] (* a b c)))

(foo2 3 4)
(foo2 3 4 5)

;; Multiple arities can be used to supply default values.

(defn defaults
  ([x] (defaults x :default))
  ([x y] [x y]))

(defaults :explicit)
(defaults :explicit1 :explicit2)

;; Functions support rest arguments.

(defn foo3 [a b & d]
  [a b d])

(foo3 1 2)
(foo3 1 2 3 4)

;; You can apply functions.

(apply + [1 2 3 4 5])

;; This is the same as.

(+ 1 2 3 4 5)


;; multimethods
;; ----------------------------------------------------------------------------

;; Often when you need some polymorphism, and performance isn't an issue,
;; multimethods will suffice. Multimethods are functions that allow open
;; extension, but instead of limiting dispatch to type, dispatch is controlled
;; by whatever value the dispatch fn originally supplied to `defmulti` returns.

;; Here is the simplest multimethod you can write. It simply dispatches on
;; the value received.

(defmulti simple-multi identity)

;; Now we can define methods for particular values.

(defmethod simple-multi 1
  [value] "Dispatched on 1")

(simple-multi 1)

(defmethod simple-multi "foo"
  [value] "Dispatched on foo")

(simple-multi "foo")

;; However we haven't defined a case for "bar". This will give you an error.

(comment

  (simple-multi "bar")

  )


;; Here is a function that takes a list. It dispatches on the first element
;; of the list!

(defmulti parse (fn [[f & r :as form]] f))

(defmethod parse 'if
  [form] {:op :if})

(defmethod parse 'let
  [form] {:op :let})

(parse '(if a b c))
(parse '(let [x 1] x))


;; Scoping
;; ============================================================================

;; Clojure has lexical scoping.

(def some-x 1)

(let [some-x 2]
  some-x)

some-x

;; Closures
;; ----------------------------------------------------------------------------

;; Could a language with such a name miss closures? Surely it can't.

(let [a 1e3]
  (defn foo []
    (* a a))
  (defn bar []
    (+ (foo) a)))

;; Above we defined `foo` and `bar` functions inside the scope of a
;; `let` form and they both know about `a` (i.e. they close over `a`)
;; Note, even if defined inside a `let`, `foo` and `bar` are available
;; in the outer scope. This is because all `def` expressions are always
;; top level. See the footnote at the end of this section.


(foo)
(bar)

;; And Nobody else.

(comment

  (defn baz []
    (type a))
  (baz)

  )

;; That's why some people say that closures are the poor man's objects.
;; They encapsulate the information as well.

;; But in Clojure, functions' parameters and let bindings' locals
;; are not mutable! That goes for loop locals, too!

(let [fns (loop [i 0 ret []]
            (if (< i 10)
              (recur (inc i) (conj ret (fn [] i)))
              ret))]
  (map #(%) fns))


;; FOOTNOTE:
;;
;; `def` expressions (including `defn`) are always top level. People familiar
;; with Scheme or other Lisps often mistakenly write the following in Clojure:

(defn not-scheme []
  (defn no-no-no [] "Noooo"))

;; Calling `not-scheme` wiil cause `no-no-no` to be defined on the top level:

(not-scheme)

(no-no-no)

;; This is almost always incorrect. If you need to write a local function just
;; do it with a let binding.

(defn outer-fn []
  (let [inner-fn (fn [])]))

;; This time, calling `outer-fn` won't cause `inner-fn` to be defined:

(outer-fn)

(comment

  (inner-fn)

  )

;; Destructuring
;; ============================================================================

;; In any serious Clojure program, there will be significant amounts of
;; data manipulation. Again, we will see that Clojure's uniformity
;; pays off.

;; In Clojure anywhere bindings are allowed (like `let` or function
;; parameters), destructuring is allowed.


;; Sequence destructuring
;; ----------------------------------------------------------------------------

;; Destructuring sequential types is particularly useful.

(let [[f & r] '(1 2 3)]
  f)

(let [[f & r] '(1 2 3)]
  r)

(let [[r g b] [255 255 150]]
  g)

;; _ is just a convention for saying that you are not interested at the
;; item in the corresponding position. it has no other special meaning.
;; Here we're only interested at the third local variable named `b`.

(let [[_ _ b] [255 255 150]]
  b)

;; destructuring function arguments works just as well. Here we are
;; only intersted at the second argument `g`.

(defn green [[_ g _]] g)

(green [255 255 150])


;; Map destructuring
;; ----------------------------------------------------------------------------

;; Map destructuring is also useful. Here we destructure the value for the
;; `:foo` key and bind it to a local `f`, and the value for `:baz` key
;; and bind it to a local `b`.

(let [{f :foo b :baz} {:foo "bar" :baz "woz"}]
  [f b])

;; If we don't want to rename, we can just use `:keys`.

(let [{:keys [first last]} {:first "Bob" :last "Smith"}]
  [first last])

;; The above map destructuring form is very useful when you need to
;; define a function with optional, non positional and defaulted
;; arguments.

(defn magic [& {:keys [k g h]
                :or {k 1
                     g 2
                     h 3}}]
  (hash-map :k k
            :g g
            :h h))

(magic)
(magic :k 10)
(magic :g 100)
(magic :h 1000)
(magic :k 10 :g 100 :h 1000)
(magic :h 1000 :k 10 :g 100)

;; Sequences
;; ============================================================================

;; We said that Clojure data structures are to be preferred as they
;; provide a uniform interface. All Clojure collections satisfy
;; the ISeqable protocol, which means iteration is uniform
;; (i.e. polymorphic) for all collection types.


;; Map / Filter / Reduce
;; ----------------------------------------------------------------------------

;; Clojure supports the same bells and whistles out of the box that you may
;; be familiar with from other functional programming languages.

(map inc [0 1 2 3 4 5 6 7 8 9])

(filter even? (range 10))

(remove odd? (range 10))

;; Clojure's `map` and `filter` operations are lazy. You can stack up
;; operations without getting too concerned about multiple traversals.

(map #(* % %) (filter even? (range 20)))

(reduce + (range 100))


;; List comprehensions
;; ----------------------------------------------------------------------------

;; Clojure supports the list comprehensions you might know from various
;; languages. List comprehensions are sometimes more natural or more readable
;; than a chain of `map` and `filter` operations.

(for [x (range 1 10)
      y (range 1 10)]
  [x y])

(for [x (range 1 10)
      y (range 1 10)
      :when (and (zero? (rem x y))
                 (even? (quot x y)))]
  [x y])

(for [x (range 1 10)
      y (range 1 10)
      :let [prod (* x y)]]
  [x y prod])


;; Seqable collections
;; ----------------------------------------------------------------------------

;; Most Clojure collections can be coerced into sequences.

(seq {:foo "bar" :baz "woz"})
(seq #{:cat :dog :bird})
(seq [1 2 3 4 5])
(seq '(1 2 3 4 5))

;; Many Clojure functions will call `seq` on their arguments in order to
;; provide the expected behavior. The following demonstrates that you can
;; uniformly iterate over all the Clojure collections!

(first {:foo "bar" :baz "woz"})
(rest {:foo "bar" :baz "woz"})

(first #{:cat :dog :bird})
(rest #{:cat :dog :bird})

(first [1 2 3 4 5])
(rest [1 2 3 4 5])

(first '(1 2 3 4 5))
(rest '(1 2 3 4 5))


;; Metadata
;; ============================================================================

;; All of the Clojure standard collections support metadata. Metadata
;; is a useful way to annotate data without affecting equality. The
;; Clojure compiler uses this language feature to great effect.

;; You can add metadata to a Clojure collection with `with-meta`. The
;; metadata must be a map.

(def plain-data [0 1 2 3 4 5 6 7 8 9])

(def decorated-data (with-meta plain-data {:url "http://lighttable.com"}))

;; Metadata has no effect on equality.

(= plain-data decorated-data)

;; You can access metadata with `meta`.

(meta decorated-data)


;; Error Handling
;; ============================================================================

;; Error handling in Clojure is relatively straightforward and more or
;; less similar to what is offered in Java.

;; You can construct an error like this.
(Exception. "Oops")

;; You can throw an error like this.

(comment

  (throw (Exception. "Oops"))

  )

;; You can catch an error like this.

(try
  (throw (Exception. "Oops"))
  (catch Exception e
    e))


;; Mutation
;; ============================================================================

;; Atoms
;; ----------------------------------------------------------------------------

;; A little bit of mutability goes a long way. Clojure does not offer
;; any traditional mutable data structures, however it does support identities
;; that can evolve over time via `atom`.

(def x (atom 1))

;; You can dereference the value of an atom with `@`.

@x

;; This is equivalent to calling `deref`.

(deref x)

;; If you want to change the value of an atom you can use `reset!` which returns
;; the new value. It's idiomatic to add the bang char `!` at the end of function
;; names mutating objects.

(reset! x 2)

x

@x

;; swap!
;; ------------------------------------------------------------------------------

;; If you want to change the value of an atom on the basis of its current value,
;; you can use `swap!`. In its simplest form `swap!` accept as first argument
;; the atom itself and as a second argument an updating function of one argument
;; which will be instantiated with the current value of the atom. `swap!` returns
;; the new value of the atom.

(swap! x inc)

x

@x

;; If your updating function needs extra arguments to calculate the new value, you
;; have to pass them as extra arguments to `swap!` after the updating function
;; itself.

(swap! x (fn [old extra-arg]
           (+ old extra-arg)) 39)

x

@x

;; As usual when anonymous functions are simple enough, it's idiomatic to use
;; the condensed form.

(swap! x #(- %1 %2) 42)

x

@x

;; Note that the updating function has to be free of side-effects because a
;; waiting writer could call it more than once in a spin loop.

;; Refs
;; ----------------------------------------------------------------------------

;; Refs are a coordinated reference type. They allow you to change multiple
;; identities at the same time. Let's see an example.

(def bucket-1 (ref 0))

;; You can dereference the value of a ref with `@` or `deref`.

@bucket-1

(deref bucket-1)

;; Since refs are coordinated, we need something to tell Clojure whats in a
;; transaction, meaning what needs to happen at the same time. This is a
;; macro called `dosync`.

;; The first function we are going to use to change the refs values is `alter`.
;; It works like `swap!` does in atoms, but it needs to be inside `dosync`.
;; Outside `dosync` it causes an exception.

(comment

  (alter bucket-1 + 3)

  )

;; But this works. If you run it more than once, the ref value will keep
;; increasing.

(dosync (alter bucket-1 + 3))

@bucket-1

;; Another function similar to `reset!` for atoms is `ref-set`. It also needs
;; to be inside `dosync` to work.

(dosync (ref-set bucket-1 0))

;; To see how coordinated changes work, we need another ref.

(def bucket-2 (ref 10))

;; Now let's create a function to move things from one bucket to another.

(defn move-content [origin destination quantity]
  (dosync
    (alter origin - quantity)
    (alter destination + quantity)))

(move-content bucket-2 bucket-1 3)

@bucket-1

@bucket-2

;; The changes only happen to the refs if all changes are succesful.
;; You can other things with refs but those will be explained in another
;; tutorial.


;; Agents
;; ----------------------------------------------------------------------------

;; Agents are a uncoordinated and asynchronous reference type. You can create
;; one with `agent`.

(def counter (agent 0))

;; To get the value in an agent you can use `@` or `deref` again.

@counter

(deref counter)

;; To change the value in an agent you can use `send`. It works like `swap!`
;; works in atoms.

(send counter inc)

@counter

(send counter + 8)

@counter

;; When you use `send` it always returns the agent immediately. Later the agent
;; will have its new value set. You can always access the state of the agent.
;; Run the next code and then run the `@counter` several times. It should keep
;; showing the value of the last `@counter` for about 5 seconds and then
;; increase.

(send counter (fn [i]
                (Thread/sleep 5000)
                (inc i)))
@counter

;; There is more to talk about agents like validations, watches and dealing
;; with errors but those are topics for another tutorial.


;; The Clojure Standard Library
;; ============================================================================

;; Here are some highlights and patterns that newcomers to Clojure might
;; find useful. Remember you can type ctrl-shift-d at anytime to bring up
;; the documentation panel to see what any of these function do.

(apply str (interpose ", " ["Bob" "Mary" "George"]))

((juxt :first :last) {:first "Bob" :last "Smith"})

(def people [{:first "John" :last "McCarthy"}
             {:first "Alan" :last "Kay"}
             {:first "Joseph" :last "Licklider"}
             {:first "Robin" :last "Milner"}])

(map :first people)

(take 5 (repeat "red"))

(take 5 (repeat "blue"))

(take 5 (interleave (repeat "red") (repeat "blue")))

(take 10 (cycle ["red" "white" "blue"]))

(partition 2 [:a 1 :b 2 :c 3 :d 4 :e 5])

(partition 2 1 [:a 1 :b 2 :c 3 :d 4 :e 5])

(take-while #(< % 5) (range 10))

(drop-while #(< % 5) (range 10))


;; Protocols
;; ============================================================================

;; The Clojure language is constructed on a rich set of protocols. The
;; same uniformity provided by Clojure collections can be extended to
;; your own types or even types that you do not control!

;; A lot of the uniform power we saw early was because the Clojure
;; collections are implemented in terms of protocols. Collections can be
;; coerced in sequences because they implement ISeqable. You can use `get`
;; on vectors and maps because they implement ILookup.

(get {:foo "bar"} :foo)
(get [:cat :bird :dog] 1)

;; Map destructing actually desugars into `get` calls. That means if you extend
;; your type to ILookup it will also support map destructuring!


;; extend-type
;; ----------------------------------------------------------------------------

;; Clojure supports custom extension to types that avoid many of the
;; pitfalls that you encounter in other languages. For example imagine we have
;; some awesome polymorphic functionality in mind.

(defprotocol MyProtocol (awesome [this]))

;; Now imagine we want Strings to participate. We can do this
;; simply.

(extend-type String
  MyProtocol
  (awesome [_] "Totally awesome!"))

(awesome "Is this awesome?")


;; extend-protocol
;; ----------------------------------------------------------------------------

;; Sometimes you want to extend several types to a protocol at once.

(extend-protocol MyProtocol
  java.util.Date
  (awesome [_] "Having an awesome time!")
  Number
  (awesome [_] "I'm an awesome number!"))

(awesome #inst "2014")
(awesome 5)


;; reify
;; ----------------------------------------------------------------------------

;; Sometimes it's useful to make an anonymous type which implements some
;; various protocols.

;; For example say we want a Java point to support ILookup. Now we don't want to
;; blindly `extend-type java.awt.Point`, that would pollute the behavior of Java
;; points for everyone. And we want to use keywords to get the points.

;; Instead we can provide a helper function that takes an object and returns
;; something that provides this functionality.

(defn ->lookup [obj]
  (reify
    clojure.lang.ILookup
    (valAt [this k not-found]
      (cond
       (= :x k) (.x obj)
       (= :y k) (.y obj)
       :else not-found))
    (valAt [this k] (.valAt this k nil))))


;; We can then selectively make Java points work with `get`.

(import java.awt.Point)

(get (->lookup (Point. 3 4)) :x)

;; But this also means we get destructuring on Java points.

(def some-point (Point. 5 6))

(let [{:keys [x y]} (->lookup some-point)]
  [x y])


;; Types & Records
;; ============================================================================

;; deftype
;; ----------------------------------------------------------------------------

;; Sometimes a map will simply not suffice, in these cases you will want to
;; make your own custom type.

(deftype Foo [a b])

;; It's idiomatic to use CamelCase to name a deftype. You can instantiate a
;; deftype instance using the same constructor pattern we've already discussed.

(Foo. 1 2)

;; You can access fields of a deftype instance using field access syntax.

(.a (Foo. 1 2))

;; You can implement protocol methods on a deftype. Note that the first
;; argument to any deftype or defrecord method is the instance itself.
;; Notice what happens when we try to count `(Foo. 1 2)` before and after
;; implement the `clojure.lang.Counted` protocol.

(comment

  (count (Foo. 1 2))

  )

(deftype Foo [a b]
  clojure.lang.Counted
  (count [this] 2))

(count (Foo. 1 2))

;; Sometimes it's useful to implement methods directly on the deftype.

(deftype Foo [a b]
  Object
  (toString [this] (str a ", " b)))

(.toString (Foo. 1 2))

;; Let's create a interface to change the fields of a instance of Foo

(definterface IFoo
  (setA [val])
  (setB [val]))

;; deftype field are immutable unless specified. The following will not compile.

(comment

  (deftype Foo [a ^:volatile-mutable b]
    IFoo
    (setA [this val] (set! a val)))

  )

;; The following will compile.

(deftype Foo [a ^:volatile-mutable b]
  IFoo
  (setB [this val] (set! b val)))


;; defrecord
;; ----------------------------------------------------------------------------

;; deftype doesn't provide much out of the box. Often what you want to do is
;; have a domain object that acts more or less like a map. This is what
;; defrecord is for.

;; Like for deftype, it's idiomatic to use CamelCase to name a defrecord.

(defrecord Person [first last])

;; You can construct an instance in the usual way.

(Person. "Bob" "Smith")

;; Or you can use the provided constructors.

(->Person "Bob" "Smith")

(map->Person {:first "Bob" :last "Smith"})

;; It's considered idiomatic and even recommended to define a factory function
;; which returns the created instance of a defrecord/deftype. It's idiomatic to use
;; dash-case for factories names.

(defn person [first last]
  (->Person first last))

;; records work like maps

(seq (person "Bob" "Smith"))

(:first (person "Bob" "Smith"))

(keys (person "Bob" "Smith"))

(vals (person "Bob" "Smith"))

;; both deftype and defrecord are open to dynamic extensions (i.e. open class)

(keys (assoc (person "Bob" "Smith") :age 18))


;; Java Interop
;; ============================================================================

;; Field Access
;; ----------------------------------------------------------------------------
(import java.awt.Point)

(def a-point (Point. 1 2))

;; You can access fields with the `.` field access syntax.

(.x a-point)
(.y a-point)

;; You can set public mutable fields with the `set!` function.

(set! (.x a-point) 0)
(.x a-point)


;; Method Calls
;; ----------------------------------------------------------------------------

;; Methods can be invoked with the `.` syntax.

(.getLocation a-point)

;; The above desugars into the following.

(. a-point getLocation)

;; For example, you can check if two points are equal.

(def another-point (Point. 10 3))

(. a-point (equals another-point))


;; Primitive Array Operations
;; ----------------------------------------------------------------------------

;; When writing performance sensitive code sometimes dealing with mutable
;; arrays is unavoidable. Clojure provides a variety of functions for
;; creating and manipulating Java arrays.

;; You can make an array of specific size with `make-array`. The first argument
;; is the class of the array contents.

(make-array Object 32)

;; There are also functions to create arrays of specific types

(def a-int-array (int-array [1 2 3]))

;; You can access an element of an array with `aget`.

(aget a-int-array 1)

;; You can set the contents of an array with aset.

(def yucky-stuff (to-array [1 2 3]))

(aset yucky-stuff 1 4)

yucky-stuff


;; Other resources to learn
;; ============================================================================

;; The Clojure cheat sheet is incredible useful.
;; http://clojure.org/cheatsheet

;; One excelent source of guides is CDS (Clojure Doc). There you will find
;; guides for essentials, core language, ecosystem and tutorials.
;; http://clojure-doc.org/

;; You may look down on Clojuredocs because it has only docs for Clojure 1.3.0.
;; You really shouldn't, since it's one of the best sources of examples of how
;; to use Clojure. And in most cases, the docs haven't changed since 1.3.0.
;; http://clojuredocs.org

;; There is a #clojure channel on freenode for IRC users. It's the best way to
;; get some immediate assistance.

;; There is also a group: http://groups.google.com/group/clojure
