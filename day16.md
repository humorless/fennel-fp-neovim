# Neovim Plugin Development—Getting Started

Previous articles explored concepts like Fennel, Lisp, and functional programming. From now on, we will begin applying these concepts to Neovim plugin development. First, let's discuss a failed learning experience.

> In the past, I originally thought that there were only two options for developing Vim plugins: VimScript and Lua. After discovering Fennel, I excitedly began to research it.

> At first, I found that Conjure could be used for Fennel development, but the crucial jump-to-definition didn't work. Then, I tried to set a seemingly simpler goal and attempted to move forward. Soon I realized that when encountering problems, I often couldn't figure out how to handle them. So, I put the matter aside.

## Why the Learning Failed

If we analyze what knowledge is required for Neovim plugin development, in addition to the basics of Neovim editor operations, there are at least the following four types of knowledge:

1.  Conjure-related development environment setup
2.  Fennel and Lua
3.  Neovim Runtime
4.  Neovim API

My previous mistake was: as a Clojure programmer, I already possessed knowledge of Conjure-related development environments. I thought that once I mastered this one item, I had mastered 50% of the knowledge, so I directly started challenging myself with a project. However, in reality, I had less than 25% of the necessary knowledge.

Naturally, it's very difficult to move forward with such a lack of knowledge.

## A Relatively Reasonable Learning Strategy

A more reasonable learning strategy should be:

1.  Properly set up the development environment, including Conjure, syntax highlighting, and so on.
2.  Carefully read through most of the introduction to the Fennel language. Even if you can't remember it all, read it first.
3.  Conduct some preliminary research on Neovim Runtime.
4.  After the preceding three types of knowledge are ready, start setting small problems and learn by doing.

Readers might have a question here: What's the difference between Neovim Runtime and API? The Runtime knowledge I'm discussing here mainly covers the following aspects:

-   Lua compilation
-   Paths
-   Basic debugging

These are essential knowledge. Neovim API, on the other hand, is all-encompassing. For instance, there's knowledge about the Tree sitter API; if you don't intend to use Tree sitter at all, you can actually skip it for now.

## Neovim Runtime

When we develop general web applications (Web Application), we typically first develop the software, then perform **unit testing**, followed by **integration testing** with databases and Http servers.

The previous interactive development can be regarded as equivalent to unit testing, as we can quickly verify the operation of a single Fennel function. What about the **integration testing** part? How do our written Fennel programs work with Neovim?

### Two Challenges: Cannot Understand, Cannot Find

To make Fennel programs work with Neovim, we mainly need to overcome two challenges:

1.  Neovim actually doesn't understand Fennel, because it only has a built-in Lua Runtime. Therefore, we must compile Fennel into Lua.
2.  Inside Neovim, there's a variable called `runtimepath` which it uses to find Lua files to load and execute based on the paths within that variable. More precisely, we must place our Lua code in the `/lua` subdirectory under the folder pointed to by the `runtimepath` variable for Neovim to find our code.

### Compilation Plugin nfnl

For compilation convenience, we need to install another plugin: [nfnl](https://github.com/Olical/nfnl)

Installation method:

-   Open the file `~/.config/nvim/init.vim`
-   Locate the section enclosed by `plug#begin` and `plug#end` in the file, and add the nfnl installation command.

```
call plug#begin(stdpath('data') . '/plugged')
...
" === Fennel (Config) Support ===
Plug 'Olical/nfnl'
...
call plug#end()
```

-   Next, in Neovim's Normal Mode, execute the following commands:

```
:source %
:PlugInstall
```

-   Additionally, because the automatic compilation plugin's command conflicts somewhat with `fnlfmt`, we must modify the `FennelOnSave` settings in `~/.config/nvim/init.vim`. Change it as follows:

```
augroup FennelOnSave
  " Format and compile after save
  autocmd!
  autocmd BufWritePost *.fnl call Fnlfmt() | NfnlCompileFile
augroup END
```

After installing and setting up this nfnl plugin, if we later edit `$path/fnl/some_file.fnl` and save the file, the plugin will automatically generate a corresponding `$path/lua/some_file.lua` file.

### Making Lua Files Discoverable

First, we can use the following Neovim Ex command to view `runtimepath`.

```
set runtimepath?
```

Which yields:

```
runtimepath=~/.config/nvim, ...
```

Thus, we can know that Lua files placed in the `~/.config/nvim/lua` folder can be read by Neovim.

To recall, previously on day06, when we installed LuaRocks, we also placed our handwritten `luarocks.lua` in the `~/.config/nvim/lua` folder.

Let's consider another crucial question: "If the compiled Lua files should be placed in the `~/.config/nvim/lua` folder, what about the corresponding Fennel files?" Naturally, they should be placed in the `~/.config/nvim/fnl` folder.

## Summary

This article discusses Neovim plugin learning strategies and the necessary Neovim Runtime knowledge for developing plugins.
