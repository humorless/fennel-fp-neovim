# Patterns and Principles—Tacit Knowledge

In many organizations, communication and collaboration are among the bottlenecks to productivity, and in the field of software development, the unique challenge of communication and collaboration lies in "tacit knowledge."

Once, I was pair programming with an engineer A. The first thing we needed to do was to install a command line program on my computer. Unfortunately, A's operating system was Linux, so the config file location he told me didn't work on my computer because my computer was macOS.

While I was struggling to find documentation, A calmly and patiently instructed me to use Dtrace commands to monitor the command line program via Dtrace, observing the system calls it made upon startup, and then to grep for a specific pattern to find the corresponding config file on macOS.

Knowledge like the example above, where one doesn't consult documentation but instead relies on a thorough understanding of the system to take shortcuts and solve problems, is typical tacit knowledge.

The difficulty in transferring tacit knowledge can be said to be one of the long-standing challenges in the software industry. This challenge doesn't only occur within organizations; it can be said that the entire industry is affected by this difficulty. The software industry periodically experiences large-scale "**reinventing the wheel**" phenomena, which are often necessary transformations driven by fundamental changes in the technological environment (e.g., shifting from on-premise deployment to cloud-native, or the emergence of new programming paradigms). However, the reason such transformations are costly and difficult to control in terms of risk lies in the difficulty of inheriting the tacit knowledge of previous generations. When a new project is initiated, it rarely gains full tacit knowledge of the previous generation's system, leading teams to hastily research the traces of the old system only when encountering significant difficulties or system failures. The discontinuity of tacit knowledge turns an already complex technical migration into a daunting challenge of starting from scratch.

## Approaches

The difficulty in transferring tacit knowledge can be broadly addressed in two ways:

1.  Design organizational processes and communication methods so that tacit knowledge gradually becomes explicit during the organization's daily operations.
2.  Design organizational structures, allowing different teams to focus on different tasks, thereby reducing the need for tacit knowledge transfer.

### Processes

Taking software project handovers as an example, this greatly requires the aid of processes. A good process can effectively help the person taking over the work to quickly navigate all pitfalls, ensuring that tacit knowledge can be transferred.

A simple project handover process could be:

1.  Ensure the newcomer can set up the project locally.
2.  Ensure the newcomer performs basic testing locally.
3.  Ensure the newcomer knows how to deploy the project.
4.  Ensure the newcomer knows how to debug in a production environment.

If standard processes require all of the above to be executed and recorded one by one, the handover process can avoid many old pitfalls or discover new ones early, thus reducing future risks.

In software, it often happens that neither the software nor the documentation has changed, but builds fail and deployments fail. This is because some dependency versions have been updated, or some operating systems used for deployment have reached end-of-life. When encountering these pitfalls, the original project developer's tacit knowledge immediately comes into play.

### Documentation (Communication Methods)

Beyond processes, the most direct way to make tacit knowledge explicit is to write documentation. However, documentation is often vague or unclear, the reason being a lack of awareness that the knowledge documentation aims to convey is to meet the needs of different situations.

[Diátaxis is a systematic approach to documentation](https://diataxis.fr/start-here/), which categorizes documentation types into four based on the reader's needs:

-   Tutorials: These documents aim to guide readers from scratch, step-by-step through a concrete, meaningful project or task. They answer the question, "How do I get started?"

-   How-to guides: When readers already know what goal they want to achieve but don't know the specific steps, these documents come in handy. They answer the question, "How do I solve a specific problem?"

-   Technical reference: These documents are like a dictionary or encyclopedia, detailing all functions, classes, interfaces, commands, or configuration options. They answer the question, "What is this?"

-   Explanation: These documents aim to provide macroscopic background knowledge and understanding, answering the question, "Why?"

By distinguishing these four documentation types, we can ensure that each document provides the most effective information for specific readers and situations. As a result, documentation will no longer be isolated fragments of knowledge, but will become a systematic knowledge base that helps teams continuously grow.

### RAG-based Knowledge Base

In addition to manual documentation, the rise of modern AI tools provides a powerful technical avenue for making tacit knowledge explicit. Traditionally, informal documents were easily scattered and prone to becoming outdated, but RAG (Retrieval-Augmented Generation) knowledge base tools have changed this situation.

The value of these tools (e.g., Google NotebookLM) lies in the fact that they allow development teams to import all unstructured knowledge fragments—including meeting transcripts, old code, design documents, and even Slack chat logs—to create a project-specific knowledge index.

This transforms the transfer of tacit knowledge from "human search and synthesis" to "AI-driven rapid retrieval and refinement":

*   Cross-document Q&A: New engineers no longer need to search for documents like finding a needle in a haystack; they can directly ask the AI: "What was the initial design intent for the `UserAuth` module in the project?" The AI will accurately cite code comments, design documents, or historical discussions from all sources to answer this "why" question.

*   Code and Knowledge Validation: RAG tools can not only organize informal discussions, but also compare this informal knowledge (e.g., a certain design decision) with the latest code status, pointing out potential semantic contradictions or outdated descriptions to ensure consistency between verbal theories and actual operation.

Through RAG-based knowledge bases, teams can, without interrupting daily development, effectively transform the "theories" originally scattered in developers' minds and never written down into queryable and verifiable explicit knowledge, thereby greatly reducing the cost and risk of tacit knowledge transfer.

### Organizational Structure

Considering the challenge of "uncertainty," Basecamp proposed a product development methodology [ShapeUp](https://basecamp.com/shapeup): one group is responsible for "shaping future projects"; while another group is responsible for "building projects" within a six-week cycle. We can also see it as: the team good at handling uncertainty challenges is the **Shaping Team**; and the team working within a six-week cycle is the **Cycle Team**.

Considering the challenge of "complexity," [Team Topology](https://teamtopologies.com/) advocates dividing teams into Stream-aligned Teams and Platform Teams. A Stream-aligned Team focuses on a single, valuable business process or product; they are the team closest to the customer, responsible for delivering value end-to-end. A Platform Team is responsible for providing internal platform services (e.g., infrastructure, deployment tools, monitoring systems, etc.) in an "as-a-Service" manner, allowing Stream-aligned Teams to use them self-service. The core purpose of this division of labor is to reduce the cognitive load of Stream-aligned Teams. By entrusting complex underlying technical details to specialized Platform Teams, Stream-aligned Teams can focus more on business logic and rapidly delivering customer value, thereby addressing the challenge of increasingly complex software systems.

Considering the challenge of "change propagation," many teams have architect roles who are responsible for various planning or technology selections early in the project, so that even if change propagation occurs later, it is still easy to correct or mitigate.

Through specialization of labor, the need for tacit knowledge transfer can be effectively reduced, thereby achieving what management theory calls "enabling ordinary people to do extraordinary things."

## Related Theories

Peter Naur's famous 1985 article **Programming as Theory Building**. The core argument is: the primary asset of a successful software system is not physical artifacts like code or documentation, but rather a "Theory" existing in the minds of the development team.

### The Nature of Theory: Tacit Knowledge in Software Development

The "Theory" Naur refers to is not a scientific law, but rather a complete Mental Grasp by the development team of the following two aspects:

1.  The principles of how the code works: how different parts of the program are designed, how they interact, and how it interacts with the machine during execution.

2.  The relationship between the program and its environment: how the code reflects real-world requirements and constraints, and what changes in the environment might necessitate code adjustments.

This "Theory" is what Naur referred to as tacit knowledge in software development. It cannot be fully written into documentation or code comments, and can only be acquired and maintained through experience, dialogue, and hands-on practice.

Therefore, Naur's argument provides the philosophical foundation for the "processes" and "documentation" strategies mentioned in this article: the true purpose of these measures is to maximize the explicit representation and structuring of the team's shared theory, and to ensure that it can be continuously reconstructed, reviewed, and transferred among team members.

## Summary

Even though software engineers possess above-average linguistic and cognitive abilities, the difficulties in communication and collaboration persist. This is because the true core asset of a software system, as Peter Naur stated, is not the code or documentation itself, but rather the **Theory** residing in the developers' minds—that is, the ineffable tacit knowledge.

The transmission of tacit knowledge is far more complex than a mere exchange of documents. Precisely because of its difficulty in being made explicit, it has become a major bottleneck and source of risk affecting productivity in software development. Therefore, we must approach this from multiple dimensions: standardizing knowledge pathways through **processes**, collectively structuring knowledge through **systematic documentation** or **RAG tools**, and utilizing **organizational structures** to reduce cognitive load and the need for tacit knowledge transfer, to effectively manage this intangible challenge.
