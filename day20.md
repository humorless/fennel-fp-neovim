# Project Discussion—auto-conjure

[auto-conjure](https://github.com/humorless/auto-conjure) is the first Neovim plugin I developed, and as such, it is very simple.

When setting up your first Neovim plugin project, you might want to ask yourself, is my topic much more difficult than auto-conjure? If so, should I start with something simpler first?

## Problem Overview

When using Conjure to develop ClojureScript, I have to issue a command `:ConjureShadowSelect [build-id]` to start ClojureScript's interactive development mode, but I often forget this command. Also, every time I need to issue this command, I have to specifically open a shadow-cljs.edn file to find `[build-id]`, which is also quite annoying.

One day, I found that others seemed to have the same problem, so he proposed a solution based on Neovim auto commands:

```vimscript
" define a function `AutoConjureSelect` to auto select
function! AutoConjureSelect()
  let shadow_build=system("ps aux | grep 'shadow-cljs watch' | head -1 | sed -E 's/.*?shadow-cljs watch //' | tr -d '\n'")
  let cmd='ConjureShadowSelect ' . shadow_build
  execute cmd
endfunction
command! AutoConjureSelect call AutoConjureSelect()

" trigger the function `AutoConjureSelect` whenever you open a cljs file.
autocmd BufReadPost *.cljs :AutoConjureSelect
```

This solution obtains the `[build-id]` by monitoring the output of the shadow-cljs process, and then generates the correct command.

Unfortunately, this solution didn't work very well on my computer, probably due to operating system differences. So I modified a [Babashka script](https://babashka.org/) myself to replace `system(...)` in the above solution.

```
#!/usr/bin/env bb

(require '[clojure.edn :as edn])
(require '[clojure.java.io :as io])

(def shadow-config (edn/read-string (slurp (io/file "shadow-cljs.edn"))))
(def build-ids (map name (keys (:builds shadow-config)))) ;; convert to string

(print (first build-ids))
```

If the Babashka script could be changed to Fennel, my editor setup could completely rely on the Neovim runtime, without having to additionally depend on a Babashka runtime.

## Solution Architecture

Initially, I thought I would translate the Babashka script into Fennel. After all, Babashka is also a Clojure-inspired Lisp, and its syntax is about 70% similar to Fennel's.

Then, I encountered a problem: **Fennel does not have a library for parsing EDN format**.

Fortunately, not long after, I found a [library](https://github.com/raystubbs/edn.lua) for parsing EDN format, written in Lua by Ray Stubbs.

## Summary

An ideal starting project should be small enough to be implemented once in another language first, and then learn Fennel and Neovim plugin development through the translation process.

Once the first step is taken, the rest becomes much simpler.
