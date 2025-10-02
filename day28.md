# Patterns and Principles—Complexity

The complexity of software development is arguably, among its many characteristics, the one that least needs special proof. Even a simple 5-10 line function, if not written well, can be difficult for engineers to understand.

So, what methods do engineers have to understand code?

Primarily, there are only two ways: understanding through "reading source code" or "observing input/output behavior as a black box", which correspond to "informal reasoning" and "testing" in the article [Out of the Tar Pit](https://curtclifton.net/papers/MoseleyMarks06a.pdf), respectively.

Since the Lisp language offers interactive development, this effectively allows engineers to arbitrarily test any section of code, naturally providing tremendous help for engineers to understand complex code.

On the other hand, if "reading source code" is considered a method of understanding, the quantity of code, mutable states, unintuitive logic, inappropriate naming, etc., will effectively increase complexity, leading to distress for engineers.

## The Way to Respond

When reading a specific section of code, what answers are engineers looking for? Nine times out of ten, they are trying to understand:

1.  What is it? (what)
2.  How is it implemented? (how)

Based on this observation, we can infer that if code is written in a way that effectively places "what it is" at a higher level and "how it is implemented" at a lower level, then the burden on engineers to understand the code can be effectively reduced.

Because when an engineer wants to understand "what it is", they can just read the higher-level code, where the amount of code will be much smaller. When an engineer wants to understand "how it is implemented", they can first select the section they are most interested in from the higher-level code, and then look for the corresponding implementation. In other words, **separating what/how** is an effective strategy for understanding through "divide and conquer", and this strategy has many manifestations in software development.

In daily practice, there is a rule of thumb that can help us get started: "**If the same thing appears three times, extract it into a function.**" This rule of thumb is simple and actionable, and repetition may also suggest that the code section is consistent with the "how to implement" concept and should be encapsulated. However, to truly achieve what/how separation, relying solely on this mechanical "three times" rule is not enough; it still requires consciously thinking about the difference between "what is it?" and "how is it implemented?", and actively designing abstraction layers.

### Container/Presenter Pattern

The Container/Presenter Pattern is a common design approach in React frontend development. Its core idea is to separate "what to do" from "how to do it", making the code structure clearer and easier to maintain.

*   Container
    Responsible for "what to display" and "what to do after an interaction occurs". It manages state, handles logic, and determines which events need to be triggered. In other words, the Container answers the question of What to display.

*   Presenter
    Focuses on "how the screen should be displayed". It renders the UI solely based on props and passes events to injected handlers; as for data sources and event semantics, it is completely unconcerned. The Presenter answers the question of How to display.

When a complex UI is split into these two roles, the benefits of what/how separation become very intuitive:

*   To understand "what data the screen needs, and what happens after a click" — look at the Container.
*   To understand "how this data is presented, and what the buttons look like" — look at the Presenter.

This separation simplifies the responsibilities of each component, thereby greatly reducing the difficulty of understanding the code.

### DSL + Interpreter Pattern

The combination of Domain Specific Language (DSL) and Interpreter Pattern is another classic manifestation of what/how separation. This is like designing a specialized language (DSL) to describe a problem, and then using an interpreter to execute it.

Tree-sitter is a library for parsing code syntax, and it perfectly embodies this concept.

*   what (Query Language): Tree-sitter provides a powerful query language that allows you to describe what you want to find in an Abstract Syntax Tree (AST) using syntax similar to CSS Selectors. For example, you might write `(function_declaration name: (identifier) @name)` to describe "I want to find all function declarations and capture their names." This query itself is a DSL; it clearly defines what you want without needing to care about the underlying search algorithm.

*   how (Execution Engine): Tree-sitter's core engine is responsible for interpreting and executing your queries. It knows how to traverse the syntax tree, how to match node types, how to handle named captures (`@name`), and so on. For the user, when the query language always functions correctly, they can completely ignore the implementation of the execution engine.

In other words, when you use Tree-sitter, you only focus on describing your needs (what), i.e., what information you want to extract from the code. As for how this need is realized (how), it is entirely handled by the underlying engine. This pattern allows you to easily complete complex code analysis tasks with just a few lines of queries, without needing to delve into the details of the underlying algorithms.

## Related Theory

This pattern of decomposing complex systems into smaller, more manageable subsystems, organized hierarchically, coincides with the concept of hierarchical complexity proposed by Herbert Alexander Simon in his work The Architecture of Complexity.

Simon illustrates this concept through a famous story about two watchmakers, Hora and Tempus. Hora's method of watchmaking was to first assemble all the parts, and then fasten them all together, but he was often interrupted before completion, causing all work to start from scratch. Tempus, on the other hand, adopted a different approach: he first assembled parts into small sub-units, then combined these sub-units into larger sub-units, and finally assembled them into a complete clock. When he was interrupted, he only needed to reassemble the last completed sub-unit, rather than starting all over again.

In this analogy, Tempus's method of work represents a "decomposable hierarchical system". Such systems are composed of independent or nearly independent subsystems, with each subsystem in turn composed of smaller subsystems, and so on, in a hierarchical fashion. This structure ensures that changes within a single subsystem are unlikely to affect other subsystems.

Applying this concept to software development, we can see that what/how separation is an effective method for building hierarchical systems. In code, "what" (是什麼) is at a higher level of abstraction, describing the goal of the functionality, while "how" (如何實現) is at a lower level, containing concrete implementation details.

This allows us, like Tempus, to modify and understand at a single level without having to comprehend the vast details of the entire system. When we want to understand a function, we can start at the "what" level, much like examining one of a clock's "sub-units"; if a deeper understanding of its operating principles is needed, we then delve down to the "how" level. This hierarchical organization not only makes code easier to understand but also makes maintenance and modification more efficient, as the scope of changes is limited to specific subsystems.

## Summary

Complexity is the most widely recognized challenge in software development, but the ways to address it are relatively less known. This article introduces the core general pattern of "what/how separation" and provides two sub-patterns:

-   Container/Presenter Pattern
-   DSL + Interpreter Pattern

These patterns also align with the theory of hierarchical complexity proposed by Herbert Alexander Simon. While in practice we can take the first step by following the simple rule "if the same thing appears three times, extract it," achieving true what/how separation still requires designers to consciously apply abstraction. The outcome of these patterns is that when modifications or understanding are needed, one only needs to focus on a specific level, without starting from scratch.
