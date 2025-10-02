# Fennel Language Crash Course—Core Syntax

So how exactly do we use Fennel to get things done? Like all high-level languages, we at least need:

1.  Functions
2.  Local Variables
3.  Numbers and Strings
4.  Containers
5.  Loops
6.  Iteration
7.  Conditional Statements

## Functions

```
(fn print-and-add [a b c]
  (print a)
  (+ b c))
```

`fn` is the keyword for defining a function. The square brackets `[...]` contain the function's arguments, and the last expression will be the function's return value.

```
(fn print-and-add [a b c]
  "purpose/context of fn here."
  (print a)
  (+ b c))

; We use `;` as a comment
```

The `"purpose/context of fn here."` in the example above is a doc string, which is optional. Its position is on the line after the square brackets.

A semicolon `;` is used to denote a comment.

## Local Variables

*   `let` is used to create local variables that are valid within the `let` block, and their values cannot be modified after creation.

```
(let [x (+ 89 5.2)
      f (fn [abc] (print (* 2 abc)))]
  (f x))
```
In the example above, after the last `)`, the variables `x` and `f` no longer exist.

```
(let [x 19]
  ;; (set x 88) <- not allowed!
  (let [x 88]
    (print (+ x 2))) ; -> 90
  (print x)) ; -> 19
```

Variables created with `let` cannot be modified with `set`. However, you can nest a second `let` block inside, and within that second `let` block, create a variable with the same name to shadow the original variable.

*   `local` is used to create local variables valid within a single file, and their values cannot be modified after creation.

```
(local tau-approx 6.28318)
```

*   `var` is used to create local variables valid within a single file, and their values can be modified later using `set`.

```
(var x 19)
(set x (+ x 8))
(print x) ; -> 27
```

## Numbers and Strings

Basic arithmetic operations are supported: `+, -, *, /`. Of course, operators are written at the beginning of the expression.

A special note is about number types: except for Lua 5.3 and later, which has an integer type, numbers in other Lua versions are all of the `number` type, which is a double-precision floating-point number. The Lua used by Neovim is Luajit, which is compatible with Lua 5.1.

Strings are an immutable type. `..` is used for string concatenation.

```
(.. "hello" " world")  ; -> "hello world"
```

## Containers

Python has `list`, `dict` container types; Java has `ArrayList`, `HashMap` container types; Golang has `slice`, `map` container types. However, in both Lua and Fennel, `Table` is the only container type.

But even though there's only one, it can satisfy both dictionary and list uses.

### Dictionary Use

When used as a dictionary, **curly braces** are used to declare it. In the following, `"number"` is the key; `531` is the value.

```
{"key" value
 "number" 531
 "f" (fn [x] (+ x 2))}
```

*   A period `.` is used to retrieve the "value" corresponding to a "key" from a `Table`.

```
(let [tbl (function-which-returns-a-table)
      key "a certain key"]
  (. tbl key))
```

*   `tset` is used to write new "key/value" pairs to a `Table` or modify existing ones.

```
(let [tbl {}
      key1 "a long string"
      key2 12]
  (tset tbl key1 "the first value")
  (tset tbl key2 "the second one")
  tbl) 
; -> {"a long string" "the first value" 12 "the second one"}
```

### List Use

When a `Table` is used to store data with linear, sequential semantics, we need to set all **keys** of the `Table` to numerical values, incrementing from `1`.

For this use case, Fennel provides a new declaration syntax using **square brackets** `[`. In this case, you don't need to write the key values; they will be automatically generated starting from `1`.

```
["abc" "def" "xyz"] 
; equivalent to {1 "abc" 2 "def" 3 "xyz"}
```

`table.insert` has two uses:

-   One is to insert a new value without an index, in which case `table.insert` will append the new value to the end of the list.
-   The other is to pass an index and a new value, in which case `table.insert` will insert the new value at the corresponding index, and the indices of all other elements in the list will be automatically adjusted.

`table.remove` also has two uses, similar to `table.insert`, either with or without an index.

```
(local ltrs ["a" "b" "c" "d"])

(table.remove ltrs)       ; Removes "d"
(table.remove ltrs 1)     ; Removes "a"
(table.insert ltrs "d")   ; Appends "d"
(table.insert ltrs 1 "a") ; Prepends "a"

(. ltrs 2)                ; -> "b"
;; ltrs is back to its original value ["a" "b" "c" "d"]
```

In Fennel, we typically refer to `Table`s used for **dictionary purposes** as **General Tables**, and `Table`s used for **list purposes** as **Sequential Tables**.

### Container Length

`length` can be used to return the length of a list container or a string.

```
(let [tbl ["abc" "def" "xyz"]]
  (+ (length tbl)
     (length (. tbl 1)))) ; -> 6
```

## Loops

If you don't know how many times to execute but just know to keep going, the `while` loop syntax is typically used.

```
(while (keep-looping?)
  (do-something))
``` 

There is also the `for` loop.

*   Print from 1 to 10 

```
(for [i 1 10]
  (print i))
;; 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
```

*   Print from 1 to 10, stepping by 2.

```
(for [i 1 10 2]
  (print i))
;; 1, 3, 5, 7, 9
```

## Iteration

However, most of the time, iteration can express our intent more clearly:

-   `each` combined with `pairs` can iterate through a General Table.
-   `each` combined with `ipairs` can iterate through a Sequential Table.

```
(each [key value (pairs {"key1" 52 "key2" 99})]
  (print key value))

(each [index value (ipairs ["abc" "def" "xyz"])]
  (print index value))
```

To express the semantics of `map` or `filter` in functional programming, `collect` and `icollect` can be used. (The semantics of these two are closest to Python's Dictionary Comprehension and List Comprehension.)

-   `collect` can generate a General Table.
-   `icollect` can generate a Sequential Table.

```
(collect [_ s (ipairs [:greetings :my :darling])]
  s (length s))
;; -> {:darling 7 :greetings 9 :my 2}

(icollect [_ s (ipairs [:greetings :my :darling])]
  (if (not= :my s)
      (s:upper)))
;; -> ["GREETINGS" "DARLING"]
```

Note: The underscore `_` in the example is used as a placeholder for a variable. Because `ipair` will have two variables on the left, but since that variable will not be used later, the underscore symbol is used.

Additionally, to express the semantics of `reduce` in functional programming, `accumulate` can be used. [Reference](https://fennel-lang.org/reference#accumulate-iterator-accumulation).

## Conditional Statements

Fennel's `if` has two uses:

*   The first usage is similar to Lua's `if`.

```
(if (pass-the-exams?)
  (graduate)
  (repeat-the-grade))
```

*   The second usage's semantics are more like Lua's `if elseif elseif … else`, but the syntax is much simpler.

```
(let [x (math.random 64)]
  (if (= 0 (% x 2))
      "even"
      (= 0 (% x 9))
      "multiple of nine"
      "I dunno, something else"))
```

If a conditional statement does not have an `else` branch, `when` is typically used. Note that expressions contained within `when` often also have **side effects**.(Note)

```
(when (currently-raining?)
  (wear "boots")
  (deploy-umbrella))
```

## Summary

We explored Fennel's core syntax, much of which is directly adopted from Lua.

This section borrowed heavily from the code examples in the [official tutorial](https://fennel-lang.org/tutorial), but the official tutorial covers even more details. Interested readers may also want to refer to it.

---
Note: In software and programming, a side effect refers to a change in external state that occurs when a function, method, or expression is executed, in addition to returning a value. This concept is closely related to functional programming and will be discussed in more detail later.
