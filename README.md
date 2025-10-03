# fennel-fp-neovim

A series of Neovim Plugin Development introduction articles, using Fennel as the programming language to explain Lisp, FP, and advanced programming ideas.

## Exploring Fennel and Functional Programming in Neovim

In the AI era, developers need to think about new development paradigms: "AI helps us quickly generate code, but debugging and verification still require active developer intervention." This series of articles will start with Neovim + Fennel, guiding readers into the new world of "Interactive Development" and "Functional Programming".

Content includes:

* Fennel Language: Syntax, commonly used libraries.
* Lisp Thinking: S-expression editing, interactive development.
* Functional Programming: Pure functions, practical techniques like map/filter/reduce.
* Neovim Plugin Development: From simple scripts to complete plugins.
* Patterns and Principles: The rules that guide us to success.

Let's explore smarter and more elegant ways of program development together in the AI era.

## Acknowledgments

The completion of this article series is largely inspired by my work at [Gaiwan](https://gaiwan.co/) and [LambdaIsland](https://lambdaisland.com/). 

This series was first published in Taiwan, and then I translated the original [Traditional Chinese version](https://ithelp.ithome.com.tw/users/20161869/ironman/8497) into English using [markdown-translator](https://github.com/playcanvas/markdown-translator).

If you have any thoughts after reading this series, feel free to reach out to [me](https://replware.dev/).

## Table of contents

* [Preface: What Should You Learn After AI Accelerates Coding?](./day01.md)
  * Software Development in the AI Era
  * Fennel Lowers the Entry Barrier for Interactive Development / Functional Programming
  * Summary
* [Fennel Brief History and Development Environment](./day02.md)
  * From Individual to Community: The Birth of Fennel
  * Development Environment - Installation
  * Development Environment - Plugin Introduction
  * Development Environment - Automatic Formatting
  * Interactive Development
  * S-expression Editing
  * Summary
* [Fennel Language Crash Course—Lisp Syntax](./day03.md)
  * Let's Talk About Syntax First
  * Summary
* [Fennel Language Crash Course—Core Syntax](./day04.md)
  * Functions
  * Local Variables
  * Numbers and Strings
  * Containers
  * Loops
  * Iteration
  * Conditional Statements
  * Summary
* [Fennel Language Crash Course—Lua](./day05.md)
  * Lua and Minimalism
  * Lua Overview
  * Other Uses of Lua's Table
  * Fennel Syntax Extensions for Tables
  * Summary
* [Fennel Language Crash Course—LuaRocks](./day06.md)
  * The Problem of Multiple Interpreters
  * LuaRocks
  * Summary
* [Fennel Language Crash Course—nfnl Library](./day07.md)
  * nfnl Examples
  * Clojure-style Programming
  * Summary
* [Lisp In-depth—Interactive Development](./day08.md)
  * Other Commands for Interactive Development
  * Leveraging Interactive Development
  * Summary
* [Lisp In-depth—S-expression Editing](./day09.md)
  * Parenthesis Pairing Issues
  * Parenthesis Editing Issues
  * Navigating and Editing within the Syntax Tree
  * Summary
* [Lisp In-depth—Macro](./day10.md)
  * What Macros Does Lisp Provide?
  * Fennel's Reader Macro
  * Reconsidering Lisp Macros
  * Summary
* [Lisp In-depth—Data-Oriented Programming](./day11.md)
  * The Troubles of Tree Traversal
  * Clojure's Unique Flavor
  * Summary
* [Demystifying Functional Programming (FP)—High-Level Semantics](./day12.md)
  * Why is FP considered a high-level semantic? Starting from Accounting
  * Summary
* [Demystifying Functional Programming (FP)—The Challenge of Definition](./day13.md)
  * What is Functional Programming (FP)? What is its definition?
  * Separating Concept from Implementation
  * Summary
* [Demystifying Functional Programming (FP)—Common Mechanisms](./day14.md)
  * Higher-Order Functions
  * Value Copying
  * Summary
* [Demystifying Functional Programming (FP)—Advanced Topics](./day15.md)
  * Functional Programming Idioms (FP idioms)
  * FP and Code Reuse (code reuse)
  * Summary
* [Neovim Plugin Development—Getting Started](./day16.md)
  * Why the Learning Failed
  * A Relatively Reasonable Learning Strategy
  * Neovim Runtime
  * Summary
* [Neovim Plugin Development—Hello World](./day17.md)
  * Hello World Plugin
  * Summary
* [Neovim Plugin Development—Standard Plugin](./day18.md)
  * Plugin Development
  * Module Imports
  * Summary
* [Neovim Plugin Development—How to debug?](./day19.md)
  * Inspecting Internal State
  * Internal Execution Order
  * Summary
* [Project Discussion—auto-conjure](./day20.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Project Discussion—Conjure Piglet Client](./day21.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Project Discussion—WebSocket](./day22.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Project Discussion—CBOR](./day23.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Project Discussion—Fennel's Jump to Definition](./day24.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Project Discussion—Tree-sitter Behind Jump to Definition](./day25.md)
  * Problem Overview
  * Solution Architecture
  * Summary
* [Patterns and Principles—Bottlenecks and Improvements](./day26.md)
  * Theory of Constraints
  * The Biggest Constraint in the Age of AI
  * Summary
* [Patterns and Principles—Uncertainty](./day27.md)
  * Ways to Respond
  * Related Theory
  * Summary
* [Patterns and Principles—Complexity](./day28.md)
  * The Way to Respond
  * Related Theory
  * Summary
* [Patterns and Principles—Modification Propagation](./day29.md)
  * The Way to Respond
  * Related Theories
  * Summary
* [Patterns and Principles—Tacit Knowledge](./day30.md)
  * The Way to Respond
  * Related Theories
  * Summary

## License

Copyright @ Laurence Chen

Licensed under the term of [the Creative Commons Attribution 4.0 International License](https://creativecommons.org/licenses/by/4.0/
).