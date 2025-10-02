# Fennel Language Crash Course—nfnl Library

In Fennel's official documentation, there's a passage:

> There's also a Cljlib library, that implements a lot of functions from clojure.core namespace and has a set of macros to make writing code more familiar to Clojure programmers

For those who want to write code in a Clojure style, [Cljlib](https://gitlab.com/andreyorst/fennel-cljlib) is considered a more complete solution. A more convenient way is to use the [nfnl library](https://github.com/Olical/nfnl/tree/main). Since Conjure also bundles nfnl, if you previously installed Conjure according to the steps in day02, then the nfnl library is already available for use.

## nfnl Examples

Open the previously downloaded auto-conjure project.

```
cd auto-conjure
```

Create a `fnl/auto-conjure/temp.fnl` file.

```
nvim fnl/auto-conjure/temp.fnl
```
Paste the following content into `temp.fnl`, and evaluate each top-level expression using `,ee`.
 
```
(local core (require :conjure.nfnl.core))

(fn update-keys [t f]
  "for every key in t, apply `(f key)`."
  (->> (core.seq t)
       (core.reduce (fn [acc v]
                      (core.assoc acc (f (core.first v)) (core.second v)))
                    {})))

(fn change-keyword [k]
  {:key k})

;; (update-keys {:code "(+ 1 2)" :op :eval} change-keyword)
```

When evaluating with `,ee`, the result should be similar to:

```
; eval (current-form): (local core (require :conju...
; eval (current-form): (fn update-keys [t f] "for ...
#<function: 0x01059a1a80>
; eval (current-form): (fn change-keyword [k] {:ke...
#<function: 0x01057ac5d8>
```

A brief explanation:

- The return value of the first line `(local ... )` is `nil`, so nothing is displayed.
- The return values of the third and tenth lines `(fn ...)` are non-`nil`, so we can see results similar to `#<function: 0x01057ac5d8>` in the adjacent window.

### Comments can serve as tests

Move the cursor to position (13, 4), which is the **left parenthesis** within `;; (update-keys`, press `,ee` to evaluate, and you will still get a result.

Here's a key point: "Even if it's a comment, you can still evaluate it." Convenient, isn't it? Comments are tests.

### Table keys can also be Tables

Here's an explanation of what the code does:

> I defined a function called `update-keys`. It can apply an operation to all keys of a Table using the function provided as its second argument.

> In the comment on line 13, after `{:code "(+ 1 2)" :op :eval}` is operated on, each key will change from a string to a Table.

Keys can use types other than strings or numbers? That's right, Lua's Table provides this kind of semantics.

### Where is the source code?

If readers look for the source code corresponding to `:conjure.nfnl.core` in Conjure's github repo, they won't find it. This is because Conjure only includes the version already compiled into Lua.

nfnl has its own repo, and I find the following libraries particularly useful:

- [core](https://github.com/Olical/nfnl/blob/main/fnl/nfnl/core.fnl): This is a concise version of the Clojure standard library imitation.
- [notify](https://github.com/Olical/nfnl/blob/main/fnl/nfnl/notify.fnl): A log library.
- [macros](https://github.com/Olical/nfnl/blob/main/fnl/nfnl/macros.fnl): Provides a time function.

## Clojure-style Programming

This piece of code from the previous example:

```
(fn update-keys [t f]
  "for every key in t, apply `(f key)`."
  (->> (core.seq t)
       (core.reduce (fn [acc v]
                      (core.assoc acc (f (core.first v)) (core.second v)))
                    {})))
```

If the nfnl library is discarded, it would change to:

* Imperative programming style

```
(fn update-keys [t f]
  "for every key in t, apply `(f key)`."
  (let [result {}]
    (each [k v (pairs t)]
      (tset result (f k) v))
    result))
```

* Functional programming style

```
(fn update-keys [t f]
  "for every key in t, apply `(f key)`."
  (collect [k v (pairs t)]
    (f k) v))
```

### Expressiveness vs. Performance

One of the characteristics of the Clojure library is that "almost every expression has a return value." When writing code, there's no need to overthink; everything simply has a return value. Furthermore, with interactive development, you don't need to rack your brain too much—just write a little code, observe how the return value changes, and keep adjusting until you reach the goal.

Therefore, when I use `reduce`, inside the anonymous function passed to it, I call `assoc`, which also has a return value—it returns the modified Table. In this context, `tset` has similar semantics to `assoc`, both modifying a Table, but `tset`'s return value is `nil`, so if `tset` were used instead of `assoc`, it would result in an error.

No choice, `tset` is a Lua function, after all.

Fennel, after all, is also designed as a functional programming language, so it naturally has functional constructs, hence `collect`. However, there are subtle differences at its core: the syntax provided by Fennel is implemented using macros; on the other hand, `:conjure.nfnl.core` mainly consists of functions.

On one hand, macros do not possess the rich **expressiveness** of functions; for instance, macros cannot be passed as function arguments. On the other hand, precisely because they are macros, when this code is compiled into Lua, there is almost no redundant variable copying, which is very resource-efficient, and can be seen as a form of **performance optimization**.

## Summary

When Fennel was designed, considering the context of embedded language applications, performance was often a significant concern. Therefore, some expressiveness was sacrificed in exchange for performance. In other words, if performance is not an issue, writing Clojure-style Fennel is also a viable option.
