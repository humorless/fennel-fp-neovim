# Neovim Plugin Development—Standard Plugin

On day 17, we demonstrated the "Hello World" of plugins. Obviously, a slightly more complex plugin won't consist of just a single file. Additionally, we want plugins to be installable. Therefore, this article will demonstrate how to create a standard plugin.

## Plugin Development

### Prerequisites
- First, ensure that the [nfnl plugin](https://github.com/Olical/nfnl#installation) is installed in Neovim.

- Create a project folder and navigate into it.

```
$ mkdir my-plugin
$ cd my-plugin
```

- Prepare the nfnl configuration file in the project directory.

`echo "{}" > .nfnl.fnl`

With this configuration file, nfnl's automatic compilation feature can be enabled. For detailed instructions, refer to [nfnl's README](https://github.com/Olical/nfnl?tab=readme-ov-file#usage).

- Create subdirectories

```
mkdir -p lua/my-plugin fnl/my-plugin
```

### Add Code

- Create the file `fnl/my-plugin/init.fnl` with the following content:
 
```
(fn hi []
  (print "Hi World Fennel!"))

(fn setup []
  (vim.api.nvim_create_user_command 
    :HiFennel 
    hi 
    {}))

;; Export the setup function so that init.vim can call it
{: setup}
```
 
- After saving the file, `lua/my-plugin/init.lua` will be automatically generated. 

- If you run the `tree` command, you should see:

```
~$ tree -a
.
├── .nfnl.fnl
├── fnl
│   └── my-plugin
│       └── init.fnl
└── lua
    └── my-plugin
        └── init.lua
```
 
### Publish Plugin

- Include the following files in your version control system:

```
.nfnl.fnl
fnl/my-plugin/init.fnl
lua/my-plugin/init.lua
```

- Publish the plugin to your GitHub.

### Installation

- In your init.vim file, add your plugin to the Plug block:

```
call plug#begin(stdpath('data') . '/plugged')
Plug '$username/my-plugin'
call plug#end()
```

Replace $username with your GitHub username.

- In Neovim's Normal Mode, execute the following commands:

```
:source %
:PlugInstall
```

- Below the Plug block in your init.vim file, add another line:

```
lua require("my-plugin").setup()
```
This line is used to initialize the plugin.

- After restarting Neovim, you can use the defined `:HiFennel` command in Neovim.

## Module Imports

Often, plugins are divided into several modules. How should module imports be handled in such cases?

- Create the file `fnl/my-plugin/util.fnl` with the following content:

```
(fn inc [x]
  (+ 1 x))

{: inc}
```

Here, a `util` module is created, and it has a public function named `inc`.

- Modify the file `fnl/my-plugin/init.fnl` with the following content:

```
(local util (require :my-plugin.util))
(util.inc 15)

(fn hi []
  (print "Hi World Fennel!"))

(fn setup []
  (vim.api.nvim_create_user_command :HiFennel hi {}))

;; Export the setup function so that init.vim can call it
{: setup}
```

The top two lines import the `util` module and call `util.inc`.

- Test the import

If we use `<localleader>ee` to evaluate the first line `(local util (require :my-plugin.util))`, we will get an error similar to the following:

```
; eval (current-form): (local util (require :my-plugi...
; [Runtime] [string "local util = require("my-plugin.util")..."]:1: module 'my-plugin.util' not found:
; 	no field package.preload['my-plugin.util']
; 	no file './my-plugin/util.lua'
; 	no file '/opt/homebrew/share/luajit-2.1/my-plugin/util.lua'
; 	no file '/usr/local/share/lua/5.1/my-plugin/util.lua'
...
```

* To fix the path issue, execute the following Ex command:

```
package.path = vim.fn.getcwd() .. "/lua/?.lua;" .. package.path
```

After fixing the path, re-evaluating the first line with `<localleader>ee` will work correctly.

### Cause of the Error

This error occurs because Neovim's **Lua search path** `package.path` does not include the `lua` folder within your current project directory. Therefore, when `require` attempts to load the `my-plugin.util` module, it cannot find the corresponding `lua/my-plugin/util.lua` file in the default paths, thus throwing the 'module 'my-plugin.util' not found' error.

After executing the command `package.path = vim.fn.getcwd() .. "/lua/?.lua;" .. package.path`, this line adds the `lua` folder under the current working directory to the Lua search path. This way, `require` can correctly find and load the `my-plugin.util` module.

We can also incorporate this modification to the Lua search path directly into the project using [Directory local Neovim configuration in Fennel](https://github.com/Olical/nfnl?tab=readme-ov-file#directory-local-neovim-configuration-in-fennel), so there's no need to manually adjust the path with commands in the future. Furthermore, once this Neovim plugin is installed, this path issue will no longer exist because the plugin's directory is automatically included in Neovim's runtimepath.

## Summary

This article introduced standard Neovim plugin development, how to install plugins, module imports, and common path issues encountered during development.
