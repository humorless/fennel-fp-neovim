# Demystifying Functional Programming (FP)—Common Mechanisms

In the previous article, we discussed the concepts of functional programming and the separation of concerns between concept and implementation. So, what FP mechanisms can we use in general programming, whether it's Neovim plugin development, frontend development, backend development, etc.?

Let's start with a very common FP code snippet (using Fennel as an example).

```
;; Use Clojure inspired library nfnl
(local core (require "conjure.nfnl.core"))

;; Define a function is-even?
(fn is-even? [n]
  (= (% n 2) 0))

;; map + filter
(core.filter (fn [y] (is-even? y))
  (core.map (fn [x] (+ 1 x)) [1 2 3 4 5]))
;; => [2 4 6]
```

Explanation:

- `core.map` will transform `[1 2 3 4 5]` into `[2 3 4 5 6]`
- `core.filter` will extract the even numbers from `[2 3 4 5 6]`, turning it into `[2 4 6]`

Here, there is an **explicit mechanism** supporting FP: higher-order functions; and an **implicit mechanism**: value copying.

## Higher-Order Functions

The most commonly used higher-order functions are, naturally, `map, filter, reduce`. So, beginners often ask what other essential ones there are, as if worrying that their code isn't "FP enough" if they miss a few higher-order functions.

Regarding this question, I once pondered whether it would be worthwhile to statistically analyze function usage frequency across numerous GitHub repos to identify the top ten most common higher-order functions besides `map, filter, reduce`. Considering that frequently used higher-order functions would naturally follow an 80/20 distribution, it's possible that mastering just the top ten would mean rarely encountering unfamiliar higher-order functions in the future.

However, I later realized that while the frequency analysis concept mentioned above is theoretically feasible, practically, choosing which GitHub repos would be sufficiently representative is another challenge. For instance, some GitHub repos provide many Macros, and it's highly likely that `partition` would be used in such libraries because `partition` is particularly useful when writing Macros.

Finally, I made a rough generalization: besides the most commonly used `map, filter, reduce`, the next 6 most common higher-order functions should be:

- concat
- thread first (->)
- some (used for scenarios handling early return from loop)
- partition (used for macros)
- take
- unpack

## Value Copying

If you look closely at the initial example, without using the nfnl library, the original code could be rewritten as:

```
;; Define a function is-even?
(fn is-even? [n]
  (= (% n 2) 0))

(icollect [k v (ipairs [1 2 3 4 5])]
  (when (is-even? (+ 1 v))
    (+ 1 v)))
```

This approach is equivalent to **directly iterating** the original array `[1 2 3 4 5]`, then performing `(+ 1 v)` for each element within the loop and checking if it's even. In this case, no intermediate array is created. In contrast, the previous `map` + `filter` approach first used `map` to create a new array `[2 3 4 5 6]`, and then `filter` created another new array `[2 4 6]` based on this new array.

This is a very common and important issue when FP deals with collections: value copying. Because FP emphasizes immutability, when you perform an operation on a collection, such as `map` or `filter`, it doesn't modify the original collection in place; instead, it creates a new collection to store the result. This ensures the purity of functions and avoids many side effect issues.

However, this extensive copying can lead to performance considerations, especially for large datasets. To address this problem, the FP community has developed different strategies:

1. Using Macros to eliminate value copying
2. Using Immutable Collection

### Using Macros to eliminate value copying

Fennel, as a Lisp that compiles to Lua, heavily utilizes Macros to solve this problem. A Macro is a tool that expands code at compile time. You can think of it as a "code generator."

For example, Fennel's `icollect` is actually a Macro. When you write `(icollect ...)` it gets expanded into a Lua for loop at compile time. This loop operates directly on the data without generating intermediate arrays.

The advantage of this is that at the syntactic level, you can still enjoy the elegance and abstraction provided by FP syntax, but at the low level, it is transformed into more performant imperative code, significantly reducing value copying.

### Using Immutable Collection

Clojure is another leader in FP, and its solution to the extensive copying problem is Immutable Collection. What is an Immutable Collection?

It is a special data structure that cannot be modified once created. If you need to "modify" it, you will get a new Immutable Collection, but this new collection and the old one will share most of the underlying structure.

Taking Clojure's vector as an example, it uses the Persistent Vector data structure. The principle behind it is a tree structure. When you perform an `assoc` (add or modify element) operation on a vector, it doesn't copy the entire vector, but only the path from the root node to the leaf node that needs to be modified. This makes the cost of each "modification" very small, approximately log_32(n), rather than O(n).

The advantages of this design are:

- Performance Optimization: Significantly reduces the overhead of data copying, especially for large collections.
- Memory Efficiency: Old and new collections share most of the memory space, reducing memory consumption.
- Purity Preservation: Still adheres to FP's principle of immutability, making code safer and easier to reason about.

In summary, when dealing with value copying, FP effectively solves potential performance issues by using Macros or special Immutable Collection structures, all while maintaining the core concept of immutability. This is one of the important foundations that enable FP to be practical in modern software development.

## Summary

FP has two major mechanisms that are commonly used and important in practice: **higher-order functions** and **value copying**.

Higher-order functions like `map`, `filter`, and `reduce` help us process data concisely, while others like `thread first` also have their own uses.

At the same time, FP's immutability can lead to extensive value copying. For the value copying problem, we discussed two common solutions: Fennel Macros convert FP syntax into efficient underlying code at compile time; Clojure, on the other hand, utilizes special immutable data structures to share underlying memory, significantly reducing copying overhead.
