# Lisp In-depth—Data-Oriented Programming

There's a comment about Clojure that goes like this: "Clojure is a Lisp, but because of its unique flavor, it's hard to categorize it as a traditional Lisp."

Basically, any programming language that uses S-expressions for its syntax, we call it a Lisp. This definition makes a lot of sense, because once S-expressions are used, it means three things become possible:

1. Interactive Development
2. S-expression Editing
3. Lisp Macro

So what does Clojure's unique flavor look like? Let's look at some of Clojure's unique syntax first:

```
[] -> Vector
{} -> Dictionary
#{} -> Set
#() -> Anonymous Function
```

## The Troubles of Tree Traversal

Before learning Lisp, I had been programming for over ten years. However, I had never used the Interpreter Pattern mentioned in the Design Pattern book. The main reason is that I found tree traversal to be very troublesome.

Many programming languages have libraries for handling tree-like structures, but not only do tree traversal libraries differ across different programming languages, even within the same programming language, tree traversal libraries often vary greatly. Whether it's the tree structure, or the tree traversal API, none of it is consistent.

Therefore, I could never remember any single tree traversal library, and the Interpreter Pattern consequently never made it into my toolkit.

In the Lisp community, tree traversal is a piece of cake, almost everyone knows how to do it, because code itself is an S-expression, and Lisp is inherently very suitable for processing tree-like structures.

If using Lisp, what data structure would be used to represent the tree below?

```
     祖父
    /    \
  父親    伯父
 /   \
我    妹妹
```

Of course, S-expressions.

```
(祖父 (父親 (我)
          (妹妹))
     (伯父))
```

Lisp's S-expressions allow engineers to easily express '**data structures**' (the term "data structure" here does not refer to data structures in algorithms, but rather to representations similar to JSON or XML). Precisely because of this, Lisp programmers can be said to be among the first to discover the value of JSON representation.

## Clojure's Unique Flavor

In the previous examples, I believe readers have already felt the importance of "representation format." If data can have a simple, readable, and easy-to-understand representation, it will greatly enhance code readability.

However, Clojure goes one step further; it attempts to achieve two goals:

1. Make data structures look like data structures.
2. Allow logic to be expressed through data structures.

### Make Data Structures Look Like Data Structures

If only S-expressions were used to construct vectors and sets, it would be written like this:

```
(vector 1 2 3)
(set 1 2 3)
```

However, Clojure provides a more concise syntax that makes data structures look more like data:

```
[1 2 3]   ;=> Represents a vector containing 1, 2, 3.
#{1 2 3}  ;=> Represents a set containing 1, 2, 3. Elements within a set must be unique.
```

After being appropriately modified with symbols, isn't the syntax more concise, yet still easy to understand?

### Allow Logic to be Expressed Through Data Structures

Traditional programming languages typically handle data and logic separately. Data is data, functions are functions; they are independent of each other. However, in Clojure, **logic** can often be expressed through certain data structures, and it's even easier to understand.

What does this mean? Let's look at a simple example.

In Clojure, you can use a vector to represent a "path." For example, the vector `[:a :b :c]` can represent a sequential **logical operation**: first enter `:a`, then enter `:b`, and finally reach `:c`.

When you pass this vector to the `get-in` function, this vector is no longer just simple data; it transforms itself into the logic that "tells the function how to execute."

```
(def data {:a {:b {:c 100}}})

(get-in data [:a :b :c])
;; => 100
```
Here, the vector `[:a :b :c]` clearly expresses the logic of "sequentially retrieving the values corresponding to `:a`, `:b`, and `:c` from the `data` dictionary."

Similarly, a set can also be used to express logic. Imagine you have a data sequence `data-seq`, and you want to filter out all elements that contain `:a` or `:b`. You don't need to write a lengthy anonymous function; simply pass the set `#{:a :b}` directly to the `filter` function.

```
(def data-seq [:a :c :b :d])

(filter #{:a :b} data-seq)
;; => (:a :b)
```

In this example, the `filter` function will sequentially pass each element of `data-seq` (`:a`, `:c`, `:b`, `:d`) to `#{:a :b}` which acts as a function. This set is not just data; it also defines the filtering rules.

This is one of Clojure's core ideas: expressing logic through simple, readable data structures, which makes the code more concise and easier to understand. It can also be said that in these examples, data structures are a form of DSL (Domain-Specific Language), because they are endowed with new semantics.

This data-oriented thinking model allows Clojure programmers, on one hand, to inherit the Lisp programmer tradition: "defining new languages (DSL) for specific problems, ultimately making the code an excellent tool for expressing its intent." On the other hand, it skillfully avoids the drawback that using too many Macros can make programs difficult to debug.

Readers might want to ask: "What if I want to endow data structures with very complex semantics, how should I design them?"

This is when you can write a treewalk interpreter.

### Code is Data; Data is Code

One of traditional Lisp's classic sayings, "Code is Data," can be understood as follows: because code is an S-expression, Code is also an Abstract Syntax Tree, and an Abstract Syntax Tree is a data structure (Data). That is, code is a data structure. And Lisp Macros can transform S-expressions, thus adding new semantics. In other words, "Code is Data" is talking about **Lisp Macro**.

Another classic saying is "Data is Code," which can be understood as: within Lisp code, there are some S-expressions that are not functions, not macros, but rather data structures (Data). And these data structures can often be endowed with new semantics, so they are actually DSLs, which is another form of Code. If more complex semantics are to be endowed, a treewalk interpreter is needed. In other words, "Data is Code" is talking about **data structures as DSLs**.

Compared to traditional Lisp which emphasizes the "Code is Data" philosophy; Clojure, in addition to using the "Code is Data" philosophy, also actively uses the "Data is Code" philosophy.

## Summary

This article introduced the key distinction between traditional Lisp and Clojure: Data-Oriented Programming.

In summary, Lisp can bring four powerful characteristics:

1. Interactive Development
2. S-expression Editing
3. Lisp Macro
4. Data-Oriented Programming

The interesting thing is, although non-Lisp languages rarely possess all four of these characteristics simultaneously, these characteristics are often found in the Modern Data Stack, and in some API designs that use JSON/YAML as a scripting language.

The philosophy of Lisp is always rediscovered by generation after generation of engineers, isn't it?
