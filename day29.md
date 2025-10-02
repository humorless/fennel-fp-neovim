# Patterns and Principles—Modification Propagation

Time can be said to be the archenemy of software.

After even a short period, countless external forces will pressure software systems to undergo modifications. User requirements might change, a security risk might be discovered in an application package necessitating replacement, a dependent third-party service might go offline, or a database might need to be replaced to meet new performance demands, and so on.

From an ordinary person's perspective, modifying software can be done by simply typing on a computer, and theoretically, it should be much simpler than replacing a toilet—at least without needing to connect water, electricity, waste pipes, or apply cement. However, because modifications often propagate, sometimes a change to one function can lead to a domino effect where multiple parts of the entire system that depend on that function must also be modified. This makes modifying software far more difficult than many physical engineering tasks.

## The Way to Respond

We cannot make the external forces that compel software modifications disappear; however, we can try to make modifications less prone to propagation. If we carefully consider the concept of modification propagation, we will find that propagation means: "A modification infects other modules by crossing module boundaries."

Precisely for this reason, when designing module boundaries, we should design them to prevent modification propagation. Boundaries that prevent modification propagation typically come in two forms:

1.  **For the consumer, the boundary allows replacing the producer**.
2.  **For the producer, the boundary allows injecting new implementations**.

Regardless of which of the above, both can be called **loose coupling** mechanisms. To prevent modification propagation, increase software stability, and reduce the cost of relearning, we must make good use of loose coupling mechanisms.

Below, we explore some common loose coupling mechanisms.

### Transport Protocol as an Interface

When we use a transport protocol (e.g., HTTP, gRPC) as an interface between different services or modules, it provides a natural isolation layer. Service A only needs to know how to communicate with Service B according to the agreed-upon transport protocol; it doesn't need to care about how Service B is implemented internally.

If today Service B needs to be rewritten from Node.js to Go language due to performance issues, as long as the transport protocol it provides to Service A remains unchanged, Service A will not need any modifications at all. This is the best example of "**For the consumer, the boundary allows replacing the producer**." Service B's internal implementation changed, but for Service A, the boundary (transport protocol) is stable, and the modification did not propagate. This mechanism is especially common in microservice architectures, allowing each service to be developed, deployed, and scaled independently.

This concept is not limited to communication between services. In monolithic applications, an **interface** can play the same role. When a module (consumer) depends on an interface rather than on a concrete class implementation (producer), you can freely replace the underlying implementation without affecting the consumer. This is precisely the structural basis of Dependency Injection.

### Extensible Data Formats and Strategy Pattern

EDN (Extensible Data Notation) is an extensible data format commonly used in the Clojure ecosystem. It is similar to JSON or XML, but its most crucial advantage is that it allows you to extend data types using tags.

For example, you can customize a `#date` tag in EDN format to represent a date, which, when data is transmitted, will appear in a format similar to `#date "2025-09-24"`. The receiving end, upon seeing this tag, knows it is a date and can restore it to the corresponding data type.

This mechanism perfectly embodies the concept of "**For the producer, the boundary allows injecting new implementations**."

Suppose your system needs to handle a new data type today; you don't need to modify the existing architecture or data format. You only need to define a new tag in EDN format and add code to handle this tag at the receiving end. This way, new data types can be added through minor changes without significantly altering the system architecture, allowing you to gradually extend functionality without major overhauls of the old system, effectively preventing modification propagation.

Similar concepts also appear in the **Strategy Pattern**. In this pattern, the core part of the system (e.g., a payment processing module) defines an interface, but specific payment methods (such as credit card, PayPal, Apple Pay) are implemented as different classes. When a new payment method needs to be added, you only need to add a new implementation class without modifying the code of the core processing module.

## Related Theories

Taleb, in his book "Antifragile," proposes that when systems face shocks, they can be fragile, robust, or antifragile. Antifragile systems, in particular, not only withstand shocks but also progressively strengthen through small-scale disturbances.

If software is viewed as a long-evolving system, then "modifications" are the inevitable shocks it must endure. If poorly designed, modifications propagate rapidly, making the system fragile; if boundaries are well-designed, modifications can be absorbed locally, or even become an opportunity to further strengthen the system.

The core of this strengthening mechanism lies in the idea that "**Errors are information**." These errors, such as test failures, small bugs, or refactoring challenges, provide valuable knowledge about where the system is fragile and what designs don't work. Therefore, antifragility encourages actively embracing this information, learning from small errors, and optimizing.

The antifragility perspective reminds us that blindly avoiding small modifications can easily lead to large-scale collapses. The common "complete system rewrite" in software history often results from a lack of capacity to accommodate local modifications and the long-term suppression of accumulated issues. Conversely, many designs that resist modification propagation were not fully present from the outset but evolved gradually through repeatedly obtaining information from errors and undertaking refactoring.

This also echoes Taleb's statement that "Time is a filter for fragility." Protocols or data formats that survive long-term use and evolution are often more resilient than concrete implementations because they have passed the test of time. A major wisdom for software engineers in technology selection is to strike a balance between "new technological developments" and "time-tested solutions":

*   New technologies may bring higher efficiency or expressiveness, but they also entail more unknown risks.
*   While older technologies may not be cutting-edge, their longevity demonstrates relative security and reliability.

Therefore, from an antifragility perspective, modifications should not only be viewed as risks but rather as nourishment for system growth. Small-scale, controlled modification propagation allows systems to gradually accumulate adaptability through continuous evolution; and the choices in technology selection are also part of this process of continuous testing, filtering, and ultimately retaining structures that can survive over time.

## Summary

Many software projects ultimately collapse precisely because of a lack of effective management of "**modification propagation**." The **loose coupling mechanisms** articulated in this article provide two key paths to limit the scope of change:

*   For the consumer, the boundary allows replacing the producer.
*   For the producer, the boundary allows injecting new implementations.

From an **antifragility** perspective, the role of these mechanisms is to allow the system to absorb the impact of each modification and transform it into local optimizations, thereby avoiding accumulation into a final large-scale collapse. A software system capable of long-term evolution does not primarily hinge on zero modifications but on its ability to handle small-scale changes and continuous evolution.

Therefore, when designing module boundaries, we must consciously imbue them with loose coupling characteristics. Only careful design can ensure that systems benefit from constant changes rather than being harmed by them.
