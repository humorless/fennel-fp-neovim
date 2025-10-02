# Fennel Language Crash Course—Lisp Syntax

More than two decades ago, computer books often liked to be titled "C++ in 24 Hours". Of course, just as there's no sun in a sun cake and no wife in a wife cake, "C++ in 24 Hours" likely couldn't be finished in 24 hours.

Since C++ is one of the most complex languages, if you want to learn a programming language and get started in 24 hours, choosing C++ is the first mistake. On the other hand, Lua is the simplest among the widely used high-level languages in production environments. In other words, if you want to quickly master a programming language suitable for production, Lua is the top choice.

The following diagram provides an overview of Fennel: Lisp, Hosted on Lua VM, Minimalism. Among these, Minimalism can be said to be its essence and characteristic. Clojure, for example, is also a Lisp running on another language's runtime environment (JVM). However, Clojure has its own standard library and its own semantics. Fennel is different; it only uses Lisp syntax, and even its semantics heavily borrow from Lua's semantics.

![](image/Fennel/Fennel.png)

Since Fennel borrows Lua's runtime, library, and most of its semantics, merely adding Lisp's syntax, the difficulty of mastering Fennel is essentially not far from mastering Lua. It truly can be a crash course!

## Let's Talk About Syntax First

Earlier, we discussed the terms syntax and semantics and clearly pointed out their differences. Here's an example to explain it more clearly:

 * Fennel's Hello World.
 
```fennel
(print "hellow world")
```

* Lua's Hello World.

```lua
print("hellow world") 
```

Both of the above code snippets are Hello World. In terms of presentation (syntax), Fennel uses Lisp syntax, so the placement of parentheses is special. However, in terms of operational behavior (semantics), both call the same `print` function, so they will be identical.

Let's look at a more complex example, such as calculating 1 + 2 * 3.

* Fennel

```fennel
(+ 1 (* 2 3))
```

* Lua

```lua
1 + 2 * 3
```

Compared to the **infix notation** used by most general programming languages, where the operator is in the middle, Lisp syntax adopts **prefix notation**, where the operator is at the very beginning, and many more parentheses are added.

You might think: "If I already know how to write Lua, after writing Lua, can I just change the **parentheses** and the **operator positions** to write Fennel?"

It's not that simple. There are two levels of difference. The first level is: "Things Fennel can do that Lua cannot."

1.  Operator precedence: Fennel developers don't need to memorize operator precedence (the order of operations is entirely determined by parentheses), while Lua developers still must understand it.
2.  Development style: Fennel supports interactive development and S-expression editing, while Lua does not.
3.  Metaprogramming (Note): Fennel can write Lisp Macros, while Lua cannot.

The second level is: "Fennel's development style inherited from the Clojure/Lisp community."

* Expression-oriented.
* Functional programming (Note).

### Expression-Oriented

In imperative languages, line count is often used as a unit to measure code quantity, and we usually think of ourselves as writing code line by line. Each line of code is a statement, and a statement can be a function call, variable assignment, and so on. This thinking model can be called "statement-oriented". The statement-oriented thinking model is especially valid during debugging, particularly when using a Debugger. Because when debugging, we tell the Debugger to step through: "Execute one line of code, then immediately stop."

Lisp programmers, however, use a different **mental model** for coding: they consider themselves to be writing code through a series of **expressions**. Expressions are connected in a tree-like manner, forming an **abstract syntax tree**. At the same time, a module (usually a source code file) is often composed of multiple syntax trees. Every expression, after evaluation, will have a return value. Even function call expressions like `(print xxx)`, which only have side effects, will have a `nil` return value.

Furthermore, due to the nature of interactive development, Lisp programmers almost always have a Debugger open. The operating principle of interactive development is: when the user issues an editor command like `Evaluate Code`, the editor automatically grabs an expression, sends it to a running interpreter for evaluation, and then sends the result back to the editor. In other words, when developing software, a Lisp programmer always has a running piece of software whose internal state is constantly changing according to their intentions. This effect is almost equivalent to what a non-Lisp programmer achieves when operating a Debugger: "There is a running program, and the engineer can control which line it executes, while simultaneously modifying and monitoring its internal state."

> In 2019, I went to a company, L Corp, to develop software. At that time, being a contractor, L Corp assigned me a Macbook Air for office work. I developed software (Clojure on JVM) in my usual way, and then, about once a month, after writing some code, the Macbook Air would force shut down and restart because the memory was completely consumed by interactive development.
> 
> Imagine if you could only write code on a 1975 MITS Altair 8800, would you choose C or Lisp? You wouldn't need to choose, because advanced Lisp couldn't even run on it.

### How to Read Fennel Code

Earlier, we mentioned that Lisp programmers don't write line by line, but expression by expression. So how do they read code?

When reading code, it's essential to do some "informal reasoning". If parts of the code cannot be intuitively understood, you must first run it in your mind and then try to deduce its behavior. In other words, you become a living interpreter.

Fennel code evaluation order has only three rules:

1.  Inside-Out
2.  Top-Down
3.  According to Macro Implementation

### Inside-Out

This rule means that when we encounter a nested expression, we must first evaluate the innermost expression, and then recursively move outwards. This is like a Russian doll; you must open it layer by layer to see the innermost one.

For example:

```
(+ 1 (* 2 3))
```

When reading this code, we cannot directly calculate `(+ 1 ...)`, because we don't know what the second parameter is. We must first enter the innermost expression `(* 2 3)`. In our mind, we first evaluate `(* 2 3)`, which gives us `6`. Then, we use this value to replace the original expression, and the entire code snippet becomes `(+ 1 6)`. Finally, we evaluate this outer expression to get `7`.

### Top-Down

This rule applies to any situation containing multiple expressions that are not nested within each other, whether within a file or inside a function. When there are multiple expressions in the code, the evaluation order proceeds from the topmost, leftmost expression, sequentially downwards.

### According to Macro Implementation

Anything whose execution order cannot be explained by the first two rules is a Macro, and we will discuss Macros in detail in later chapters. For beginners, you only need to know that `let` and `if` are Macros, as they are the most commonly used.

* Execution Order of `if`

First, evaluate `A-expression`. If it is true, then evaluate `B-expression`; if false, then evaluate `C-expression`.

```
(if A-expression
   B-exression
   C-expression)
```

* Execution Order of `let`

First, evaluate `B-expression`, then temporarily bind its result to `A-symbol`. Next, evaluate `D-expression`, then temporarily bind its result to `C-symbol`. Finally, evaluate `E-expression` and `F-expression`.

```
(let [A-symbol B-expression
      C-symbol D-expression]
   E-expression
   F-expression)
```

### Return Value

After we just saw the assignment syntax like `let`, a question arises: "Does a `let` expression have a return value?"

Yes, its return value is the return value of the last expression within it, which is the return value of `F-expression`.

Another construct very similar to `let` is `fn`, which is the syntax for defining functions. Below, `print-and-multiply` is a function. When it is called, it executes the expressions within the function definition and uses the return value of the last expression as the return value for the entire function.

```
(fn print-and-multiply [a b]
  (print (+ a b))
  (print (- a b))
  (* a b))
```

Taking the code above as an example, the return value of `print-and-multiply` is the result of the `(* a b)` expression, because it is also the last expression evaluated within the entire function definition.

This point is significantly different from function return values in most imperative languages. In many imperative languages, you must explicitly use the `return` keyword to specify the return value; otherwise, the function might have no return value or return `nil`. In Fennel, the return value of the last expression is automatically treated as the return value for the entire function. 

## Summary

This article introduced why Fennel can be a crash course and its three key aspects: Lisp, Hosted on Lua VM, Minimalism. Among these, Fennel's Lisp syntax portion was also discussed in detail.

Next, we will continue to discuss Fennel's semantics in future chapters.

--- 
Note: "Metaprogramming" and "Functional Programming" will be discussed in detail in later chapters.
