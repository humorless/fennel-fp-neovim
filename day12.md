# Demystifying Functional Programming (FP)—High-Level Semantics

Generally speaking, in the industry, there are two main reasons advocated for using **Functional Programming (FP)**:

1.  Fast development speed.
2.  Fewer bugs.

On the other hand, there are also two main reasons against using Functional Programming:

1.  The number of engineers who understand this concept is much smaller. People are hard to find and difficult to train, which leads to maintainability issues in the future.
2.  It usually comes at the cost of machine performance.

From my perspective, when summing up the two reasons: "fast development speed" and "people are hard to find, difficult to train, and maintainability issues," the final outcome is related to the external environment. It involves the external environment and business decisions, rather than purely technical discussions.

For example, an individual might use FP, resulting in fast development speed. However, when comparing this individual to a team that doesn't use FP, even though the individual might be a bit slower, the overall team speed might be faster. Or, if we consider "fast individual development" as low development labor costs. Then if we outsource software development to third-world countries, and these countries all use imperative programming, their costs would still be lower.

In summary, "FP can accelerate development speed" might indeed be offset by the industry reality that "FP practitioners are hard to find"; thus, the main considerations for whether to use FP become three:

1.  Potentially increases or decreases human development costs (?) ;; Depends on the external environment
2.  Fewer bugs
3.  Comes at the cost of machine performance

I wonder if readers find the simultaneous appearance of "fewer bugs" and "at the cost of machine performance" familiar? That's right, this is a phenomenon often caused by typical **high-level semantics** solutions.

## Why is FP considered a high-level semantic? Starting from Accounting

Imagine you need to keep accounts, but "**you only need to know the correct total balance each day**", and don't need to produce financial reports like a typical company.

| Date | Item | Income (+) | Expense (-) | Notes |
| :--- | :--- | :--- | :--- | :--- |
| 9/1 | Salary | + $35,000 | | Salary deposited |
| 9/2 | Breakfast | | - $65 | Bought sandwich and coffee |
| 9/3 | Paid Rent | | - $15,000 | September rent |
| 9/4 | Dinner | | - $350 | Dinner with friends |
| 9/5 | Purchased Daily Necessities | | - $850 | Laundry detergent, toothpaste, etc. |

In this case, you have at least two ways to achieve the goal:

### Imperative Programming

You would define a variable `balance`, and then change its state through a series of assignment operations:

```
- `balance = 0` (initial value)
- `balance = balance + 35000` (salary)
- `balance = balance - 65` (breakfast)
- `balance = balance - 15000` (rent)
```

With this method, it's definitely super memory-efficient and calculations are simple. It can also achieve the goal: knowing the correct total balance each day. Incidentally, the aforementioned method of "sequentially changing state" is a characteristic of imperative programming; to some extent, this is a feature because it's very memory-efficient.

### Functional Programming

So, what would it look like to keep accounts using **Functional Programming (FP)**?

FP does not achieve computation by repeatedly modifying existing state, but rather views "calculating the balance" as a **function**. You would treat all transaction records as the **input** for this function, and then the function would give you an **output**, which is the final balance. It minimizes "changing" state, instead repeatedly "calculating" new results.

For example, you can imagine a `sum` function, whose input is a list containing all transactions, and the output is the final balance:

`balance = sum([+35000, -65, -15000, -350, -850])`

Note this calculation method here: On one hand, you need to record every single transaction, none can be discarded, which obviously consumes a lot more memory. On the other hand, the `sum` function's result (output) is only related to its input and completely independent of any external state.

### What Problem Does High-Level Semantics Solve?

Reading this far, you might be thinking: "Both methods can yield the final balance, so what are the actual benefits of FP?"

Suppose an error occurred today, and you found that the final result was calculated incorrectly?

If your accounting uses **Imperative Programming**, the typical debugging method is to check whether the new value is correct every time `balance` changes. In other words, you would continuously inspect the internal state of the system.

But if your accounting uses **Functional Programming**, the typical debugging method is to ensure whether the data transformation of the `sum` function is correct, and whether the input provided to `sum` is correct. Did you notice? Compared to the debugging method of **Imperative Programming**, Functional Programming cleverly splits the debugging complexity into two areas: **data transformation** and **input**.

It is the possibility of this "divide and conquer" approach mentioned above that allows debugging to be greatly simplified.

## Summary

Functional Programming is considered a high-level semantic precisely because it elevates the **complexity of problem-solving from "how to change state step-by-step" to "how to define pure functions to calculate results"**.

The benefits brought by this abstraction are obvious: your code is less prone to errors because it has no side effects, and it's also easier to test and debug.

However, this abstraction is not without its costs. To avoid the complexity of intermediate states, you must store more information and recalculate when needed, which typically consumes more **system resources**.

Therefore, FP is a typical **high-level semantic** solution that solves problems at a higher level of abstraction, ultimately providing you with:

-   Fewer bugs, because the code is more predictable and has no side effects.
-   But at the same time, it might sacrifice some performance, as it typically requires more memory and computation.
