# Patterns and Principles—Bottlenecks and Improvements

In day01, we discussed software development in the age of AI and mentioned how Lisp and FP improve software development efficiency by reducing testing and debugging time. They are important because while AI accelerates coding, it makes "testing and debugging" a more significant bottleneck.

However, AI's impact extends far beyond that. When one bottleneck is resolved, new ones emerge. So, how can we, from a higher-level perspective, identify which knowledge remains important, or even gains more value, in the age of AI? I believe that the simple framework of the Theory of Constraints is sufficient to provide much inspiration.

## Theory of Constraints

The Theory of Constraints (ToC) is a management philosophy proposed by Israeli physicist Eliyahu M. Goldratt. Its core idea is simple: the performance of any system is determined by its weakest link.

Imagine a production line composed of three different machines connected in series. If one machine can only produce 100 parts per hour, while the other machines can produce 200 parts per hour, then the entire production line can produce at most 100 parts per hour. This machine that produces 100 parts per hour is the "constraint" of the system.

ToC posits that if one wants to improve the efficiency and output of the entire system, all resources and efforts must be concentrated on identifying and resolving the most critical constraint.

## The Biggest Constraint in the Age of AI

Let's review previous examples of using AI:

- In the WebSocket chapter, AI was used for **porting**, although human intervention was first used to clarify dependencies.
- In the CBOR chapter, AI was used to **infer** library usage, though it required structured thinking prompts to succeed.
- In the Jump to Definition chapter, AI was used for **architecture design**, although it was later significantly simplified manually.
- In the Tree-sitter chapter, AI was used to **generate queries**.

AI is not so much a tool itself; it's more like raw material for tools. Depending on the user's prompt, it can transform into various different tools.

If we view software development as a system, its ultimate output is a fully functional application. In the age of AI, many traditional bottlenecks, such as code writing, unit test generation, and even preliminary debugging, have seen significant improvements due to AI assistance.

However, according to the Theory of Constraints, when one bottleneck is resolved, new ones emerge. This is precisely the core challenge of software development in the age of AI.

I believe that for developers, the biggest constraint in software development is no longer simply "development speed" or "technical implementation ability," but rather "**the ability to continuously identify and adapt to productivity bottlenecks**."

A developer or team capable of accurately identifying "the most important current bottleneck" is more likely to focus AI, this versatile tool, on the most critical link, thereby yielding the greatest benefits. This also explains why knowledge areas like Lisp and FP, which lean more towards abstract thinking and architectural design, have increased in importance in the age of AI, because they train our ability to identify systemic bottlenecks, rather than merely code writing skills.

## Summary

In the age of AI, software development productivity bottlenecks are changing faster than before, thus making it even more necessary to cultivate the ability to continuously identify and adapt to these bottlenecks.

In the upcoming chapters, we will discuss some common challenges, as these challenges can easily become productivity bottlenecks if not handled properly. Furthermore, since the subsequent chapters will explore general challenges, they will no longer be tied to specific programming languages or application platforms.
