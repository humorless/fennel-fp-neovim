# Neovim Plugin Development—Hello World

In the last part of day16, we discussed that we can create plugins that work with Neovim in the `~/.config/nvim/fnl` folder. The most crucial Neovim Runtime knowledge has been acquired, so let's start a new Hello World!

## Hello World Plugin

### Specification Overview

We will create a Hello World plugin that will be initialized when Neovim starts, and it will register an Ex command for Neovim. After that, users can use the `:HelloFennel` Ex command.

### Implementation

- Prerequisites, prepare the nfnl configuration file.

`echo "{}" > ~/.config/nvim/.nfnl.fnl`

With this configuration file, nfnl's automatic compilation feature can be enabled. For detailed instructions, refer to [nfnl's README](https://github.com/Olical/nfnl?tab=readme-ov-file#usage).

- Create the plugin folder

`mkdir ~/.config/nvim/fnl`

- Create a new file ` ~/.config/nvim/fnl/hello.fnl`, with the following content:

```
(fn hello []
  (print "Hello World Fennel!"))

(fn setup []
  (vim.api.nvim_create_user_command 
    :HelloFennel 
    hello 
    {}))

;; Export the setup function so init.vim can call it
{: setup}
```

- Modify the `~/.config/nvim/init.vim` file, adding the following line:

```
lua require("hello").setup()
```

- Test:

When you save the `hello.fnl` file, **`nfnl` will automatically compile it into `~/.config/nvim/lua/hello.lua`**.

After restarting Neovim, enter the Ex command `:HelloFennel`, and you will see the message `Hello World Fennel!` printed at the bottom.
 
### Explanation

First, let's look at the content of `hello.fnl`. In this file, we defined two functions: `hello` and `setup`. `hello` is used to print messages, while `setup` is used to register an Ex command.

Inside `setup`, `vim.api.nvim_create_user_command` is [Neovim's API](https://neovim.io/doc/user/api.html#nvim_create_user_command()). The first parameter of this API is the name of the Ex command; the second parameter is the function to be executed by the Ex command; the third parameter is the arguments to be passed to the function (Lua Table data type).

At the end of `hello.fnl`, as a convention, a Lua Table is written containing the external API of this hello module. In this example, it is clearly `setup`.

When Neovim starts, it first reads `init.vim`. When it reads the command `lua require("hello").setup()`, it will look for and load the `hello.lua` file in the `~/.config/nvim/lua/` folder. Then, the `setup` function in that file will execute and register the `:HelloFennel` command.

## Summary

In this article, we successfully completed our first Fennel plugin's "Hello World". We not only personally built the project structure and wrote simple Fennel code, but also, through implementation, verified previously discussed concepts:

- How the nfnl plugin automatically compiles Fennel into Lua that Neovim can understand.
- Neovim uses the `runtimepath` variable to find and load compiled Lua modules.

Thus, we have mastered the most streamlined process for creating a simple Neovim plugin from scratch.
