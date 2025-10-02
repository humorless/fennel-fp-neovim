# Project Discussion—Fennel's Jump to Definition

During the development of the auto-conjure and Conjure Piglet Client projects, the most painful thing for me was the lack of a jump to definition feature. Therefore, for the third project, I decided to implement this feature. Moreover, I considered implementing it directly within Conjure Fennel Client and then submitting a [Pull Request](https://github.com/Olical/conjure/pull/704) to Conjure.

## Problem Overview

To clarify how to implement this feature, I asked an LLM to explain how "jump to definition in an editor" is typically implemented. The LLM responded:

> How does a language server implement "jump to definition"? It performs the following steps:

> 1. Code Parsing: The language server reads your Fennel code and parses it into an Abstract Syntax Tree (AST). This tree-like structure accurately represents the structure and semantics of your code.

> 2. Symbol Analysis: During parsing, the server identifies all important symbols, such as: variables, functions, macros, key-value pairs.

> 3. Definition-Reference Linking: The server creates a mapping table that links each symbol's usage location to its definition location. For example, when you call (my-function x) in your code, the server finds the line with (defn my-function ...) and links them.

> 4. Provide to Editor: When you press the "jump to definition" shortcut in the editor, the editor sends a request to the language server, telling it: "The user is currently at line 10, column 5 of file A. Please tell me where the symbol definition is here."

> 5. Return Result: The language server, based on its internal mapping table, returns the definition's location (file, line number, column number), and then the editor automatically jumps to that location.

## Solution Architecture

Considering that I am the user of this feature, I can decide the software specifications myself. And I decided to simplify the feature as much as possible to get the software online sooner.

I made three simplifications:

1. I chose to use Tree-sitter for code parsing, rather than Fennel's own parser. This way, my jump to definition won't be able to handle Macros. However, the probability of jumping to a Macro is much lower than jumping to a function, and Tree-sitter is much simpler.
2. I abandoned using the Language Server Protocol and implemented it directly within Conjure. This also makes it much simpler, but this feature of mine will not be able to be used across editors.
3. I abandoned 'pre-emptive code parsing' in favor of 'real-time code parsing', which simplified the problem even further.

It works as follows:

1. Upon receiving the "jump to definition" command, Conjure Fennel Client triggers the `def-str-util.search-and-jump` function.
2. Inside `search-and-jump`, it uses Tree-sitter to parse the code, further parse the module's file location, and finally find the position to jump to.
3. Jump.

## Summary

During development, for many problems, I refer to what LLMs say; and I like to ask about strategy/execution separately.

However, at the strategy level, LLMs often provide standard solutions, which doesn't necessarily mean that the standard solution is best suited for your specific use case. Therefore, over-reliance on LLMs can sometimes lead to taking a longer path.
