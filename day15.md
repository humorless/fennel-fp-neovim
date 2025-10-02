# Demystifying Functional Programming (FP)—Advanced Topics

Finally, we've reached the fourth article on FP. This is the last in the FP series, and we'll discuss some advanced FP topics:

- Functional Programming Idioms
- FP and Code Reuse (code reuse)

## Functional Programming Idioms (FP idioms)

Functional programming idioms primarily include mapcat and if patterns.

### mapcat

In Clojure, `mapcat` is a combination of `map` and `concat`. Its purpose is to apply a function to each element in a collection, and then concatenate all the returned sequences into a single sequence. This is particularly useful when we need to generate a new collection from each element in a collection and then merge these small collections into one large collection.

For example, suppose we have a collection containing multiple sub-collections, and we want to flatten them into a single sequence.

```
(def data '((1 2) (3 4) (5 6)))

;; Using map and concat
(apply concat (map identity data))
;; => (1 2 3 4 5 6)

;; Using mapcat
(mapcat identity data)
;; => (1 2 3 4 5 6)
```

Another more common application scenario is when we need to process a collection and generate a new sequence containing multiple values from each element.

```
(def users '("Alice" "Bob" "Charlie"))

;; Suppose we have a function that can generate social media accounts related to a username
(defn get-social-media-accounts [user]
  (condp = user
    "Alice" '("twitter/alice" "github/alice")
    "Bob" '("twitter/bob")
    "Charlie" '("twitter/charlie" "linkedin/charlie")))

;; Use mapcat to get all social accounts for all users and merge them into a single sequence
(mapcat get-social-media-accounts users)
;; => ("twitter/alice" "github/alice" "twitter/bob" "twitter/charlie" "linkedin/charlie")
```

### if patterns

if patterns are a class of idioms with a great deal of variation, and the discussion is quite lengthy. Interested readers can refer to [this article](https://replware.substack.com/p/if-pattern).

## FP and Code Reuse (code reuse)

Since Object-Oriented Programming (OOP) is much more popular than FP, when people discuss the advantages of OOP, they often mention that OOP can support software reuse (code reuse) because OOP provides loose coupling mechanisms (polymorphisms) such as Interfaces.

What about FP? Does FP also support code reuse?

Before discussing code reuse, let's first discuss loose coupling mechanisms.

The ultimate loose coupling mechanism should be a **Protocol**, because even if the two parties to be interfaced have different Runtimes, as long as they communicate over the network and can transmit data to each other, following the transmission protocol, different systems can be interfaced.

### OOP's Loose Coupling Mechanism

If loose coupling mechanisms are in object-oriented programming languages, including Java and Lua, they are often implemented through Java interfaces or Lua metatables, often as a Service Provider Interface. In the future, if we need to replace an old module coupled via this mechanism, we just need to develop a new module that implements the same Java interface or registers the same Lua metatable functions, and the new module can smoothly replace the old one.

Therefore, we can say that the loose coupling mechanism provided by object-oriented programming languages is a kind of: "function signature as a transmission protocol".

### FP's Loose Coupling Mechanism

What about FP? In typical FP programs, we don't create many objects because there's no need. For data modeling, most of the time, we can simply use data structures such as lists or dictionaries. Therefore, the codebase will primarily consist of a large number of data structures and functions.

Are there loose coupling mechanisms then? Yes, there are.

Firstly, data structures themselves are an excellent loose coupling mechanism. Recall that in the previous example of transmission protocols, a transmission protocol simply dictates that the communicating parties transmit data of the correct shape to each other, nothing more. In other words, we can say that the loose coupling mechanism provided by functional programming languages is a kind of: "data structure as a transmission protocol".

Additionally, there is a very important concept: many dynamic languages actually feature Duck Typing. Duck Typing itself can also be regarded as an implementation of "function signature as a transmission protocol".

## Summary

This article discussed functional programming idioms and code reuse.

Regarding idioms, mapcat is very commonly used; for if patterns, if you use them and others don't understand, don't be too hard on them.

Regarding code reuse, we analyzed it from the perspective of loose coupling mechanisms, discussing three types: "transmission protocol", "function signature as a transmission protocol", and "data structure as a transmission protocol". Clearly, FP makes it very easy to achieve code reuse.
