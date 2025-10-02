# Demystifying Functional Programming (FP)—The Challenge of Definition

Many FP newcomers, shortly after encountering FP, have two major questions:

> 1. I can use map, filter, and reduce. Does that mean I know FP?
> 2. What exactly is the definition of FP?

These two questions above are actually two sides of the same coin. If the definition of FP is just map, filter, and reduce, then one could say they've learned it. If it's not that simple, then...

## What is Functional Programming (FP)? What is its definition?

Unfortunately, this definition doesn't have a simple answer. Compared to the definition of the Lisp language, the definition of Functional Programming is relatively much more ambiguous. First of all, programming languages referred to as functional languages are at least divided into two major language systems:

1.  The language family represented by Haskell, featuring strong static typing, type inference, Monads, and so on.
2.  The language family represented by Clojure, featuring dynamic typing, along with Lisp.

Therefore, the definition of Functional Programming varies from person to person, and opinions differ. Some might consider the intersection of these two language systems to be FP. Others might believe that without type inference, it doesn't count.

In other words, the definition of Functional Programming is as controversial as [whether Lisp should actively use Macros](https://ithelp.ithome.com.tw/articles/10378722).

## Separating Concept from Implementation

Regarding the issue of definition, I believe that Functional Programming (FP) should be viewed as two parts: concept and implementation.

-   Concept: Computation is primarily achieved by repeatedly applying **data transformations without side effects**. The system's **state** can thus be minimized because the input and output of computations are **immutable** values.
-   Implementation: This part is diverse; the implementation consists of various mechanisms that can assist in achieving the aforementioned concept. They include concepts such as higher-order functions, functions as first-class citizens, Monad, Immutable Collection, and so on.

Based on this viewpoint of separating concept and implementation, I would argue that:

1.  The focus of Functional Programming lies in the "**concept**." Because by satisfying this concept, the semantics of computation become [higher-level](https://ithelp.ithome.com.tw/articles/10379221), which can achieve the effect of reducing bugs, and potentially consuming more system resources.
2.  Mechanisms like higher-order functions and functions as first-class citizens, although these indeed resemble common characteristics of many functional programming languages, they are essentially also implementations. In other words, if a certain language can achieve the concept of Functional Programming, even if it doesn't provide features like higher-order functions or functions as first-class citizens, I would still consider it to be Functional Programming.
3.  To take it a step further: I also believe that certain ways of writing Linux Shell pipes, data transformations within a [Modern Data Stack](https://ithelp.ithome.com.tw/users/20161869/ironman/6057), and [Event sourcing designs like Datomic](https://ithelp.ithome.com.tw/users/20161869/ironman/7432) are all manifestations of the Functional Programming concept.

## Summary

This article explored the definition of Functional Programming (FP), pointing out that the definition of FP is controversial due to different language systems, but if we distinguish between concept and implementation, a new perspective can be gained.

The core concept of FP lies in using data transformations without side effects for computation, minimizing system state, and using immutable values. While higher-order functions, Monads, etc., are merely implementation mechanisms that help achieve this concept. Therefore, when determining if something belongs to FP, one should focus on whether it embodies the core spirit of no side effects and immutability, rather than being fixated on specific implementation tools.
