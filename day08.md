# Lisp In-depth—Interactive Development

Previously in day02, we introduced Lisp, interactive development, and S-expression editing. Next, we will delve deeper into Lisp, with today's focus on interactive development.

## Other Commands for Interactive Development

Previously, we introduced Conjure's Normal Mode command `,ee`. Let's clarify here; strictly speaking, the command bound by Conjure is: `<localleader>ee`.

Open the `~/.config/nvim/init.vim` file, and you will see a line that defines `<localleader>` as `,`.

```
let maplocalleader=","
```
This is why we use `,ee`. Readers can set `<localleader>` to other keys according to their preferences.

In addition to what was previously mentioned:

- `<localleader>ee` ：Evaluates the expression under the current cursor. (evaluate expression).
- `:ConjureEval [code]` ：Evaluates the current `[code]`.

Other commonly used Conjure commands include:

- `<localleader>lv` ：Opens the log buffer window. (log in vertical window)
- `<localleader>lq` ：Closes the log buffer window. (log quit)
- `<localleader>lr`  ：Clears the content within the log buffer. (log reset)
- `<localleader>eb` ：Evaluates the content of the current buffer. (evaluate buffer)
- `<localleader>ew` ：Evaluates the string under the current cursor. (evaluate word)
- `<localleader>gd` ：Jumps to the definition of the string under the current cursor. (go to definition)
- `K` ：Queries documentation for the string under the current cursor. (doc)

### Memorizing Commands

Memorizing these commands actually has English clues. For example, the window opened by `<localleader>lv` is vertical, so you can associate it with "log in vertical window". A slightly less obvious association is that the English word for documentation is 'documentation,' but it's commonly shortened to 'doc,' which corresponds to K.

Does Conjure have more commands? Yes, there are more commands in the [official documentation](https://github.com/Olical/conjure/blob/main/doc/conjure.txt). Is there a need to research more? We've discussed a total of 9 Conjure commands here, which should be enough for a professional Lisp programmer to use for five years.

### Command Categories

The aforementioned 9 commands can be divided into four major categories, explained one by one below.

- log buffer 

`<localleader>lv`, `<localleader>lq`, `<localleader>lr` are clearly for log buffer control. The log buffer is one of Conjure's standout designs among many Vim interactive development plugins. A major advantage of the log buffer is: even if you evaluate something and the returned data is excessively large, Neovim usually won't freeze. The data will simply be in the log buffer.

- Expression Evaluation

These four commands, `<localleader>ee`, `:ConjureEval [code]`, `<localleader>eb`, `<localleader>ew`, at Conjure's core, communicate with the interpreter using the same structure. For instance, the implementation of `<localleader>ee` in Conjure first reads an expression, then sends its content to the interpreter; as for `<localleader>eb`, it first reads the entire buffer's content, then sends that content to the interpreter. The latter half of both commands mentioned above involves the same behavior.

- Go to Definition

It sends a special command to the interpreter to query definitions. Notably, the jump-to-definition feature for the Fennel language is not yet implemented in the latest version of Conjure. On the other hand, we can still achieve the go-to-definition functionality by pairing LSP (language server protocol) with the installation of [fennel-ls](https://git.sr.ht/~xerool/fennel-ls).

- Query Documentation

It sends a special command to the interpreter to query documentation. To add, `K` is a built-in Neovim command used to query documentation for the keyword under the cursor. Conjure cleverly integrates this command, allowing `K` to automatically send a query request to the Lisp interpreter, so we can look up function documentation.

## Leveraging Interactive Development

In day03, we discussed

> Because of interactive development, Lisp programmers almost always have a Debugger open.

Can interactive development be used as a Debugger? Almost.

Debugger assistance for debugging typically falls into three main categories:

1. Return values of function execution.
2. Step-by-step execution.
3. Runtime values of local variables within a function.

1 and 2 can clearly be replaced by interactive development. For 3, you'll need a slight detour. Here, we will introduce a technique: **Inline Inspection**.

### Inline Inspection

Consider the following code: When we want to understand the value of the local variable `z`, it seems `print` is the only option. The annoying part about `print` is that when too much is printed, we have to manually search for the desired output.

```
(fn wrong-add [x y]
  (let [z (+ x 2)]
    (+ z y)))
```

In Fennel, we can use `tset` (table set) to "capture" runtime local variable values and write them into a global Table, allowing us to inspect them at any time. The core idea behind this technique is: **observing internal local variables by modifying a globally accessible state**.

The key point of the rewrite below is: adding `(local ddd {})` at the top, and using `(tset ddd :z z)` to capture the runtime value of `z`.

```
(local ddd {})

(fn wrong-add [x y]
  (let [z (+ x 2)]
    (tset ddd :z z)
    (+ z y)))

(wrong-add 3 4)
```

After evaluating the entire file with `<localleader>eb`, move the cursor above `ddd`, execute the command `<localleader>eb`, and you will see the result.

```
; evaluate (word): ddd
{:z 5}
```

This technique has different implementations in various Lisp dialects; for example, in Clojure, one would typically [use `def` to achieve a similar effect](https://blog.michielborkent.nl/inline-def-debugging.html).

### Debugging Libraries

Imagine that while developing software, you use a function `pp` from library A. You strongly suspect `pp` has an error, but how do you test this to verify your hypothesis? Modify the library? Wouldn't that be a bit laborious, especially when A is an already installed jar file?

When developing in Clojure, I've done this:

1. Perform a go-to-definition on `pp` to jump into library A's source code.
2. Modify `pp`, and evaluate the buffer directly without saving.

In this way, the definition of `pp` is modified within the interpreter, and you can easily verify your hypothesis.

It's important to emphasize one point here: In Neovim, a **buffer is not the same as a file saved on the hard drive**. A buffer is the state of the file you are currently viewing.

Similar techniques require slight adjustments in the case of Fennel.

For the first step, Fennel's go-to-definition usually cannot handle situations where the library only has Lua source code and no Fennel source code. In such cases, you need to manually `grep` to find the correct location.

For the second step, if the found function definition location is only a Lua file, you can modify the Lua file, and use Neovim's Ex command: `:lua [content]` to evaluate the currently modified content.

## Summary

This article introduced nine commonly used Conjure commands for interactive development and two practical techniques.

There is a proverb related to luxury goods:
> A luxury, once enjoyed, becomes a necessity.

Interactive development is a Luxury; it allows your software development flexibility to reach another level. Do you think it's a necessity?
