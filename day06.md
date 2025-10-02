# Fennel Language Crash Course—LuaRocks

In day05, we demonstrated how to use Lua's standard library `io` with `require`. You might wonder, what about third-party modules?

This would require installing third-party modules on your computer first, and then referencing them via `require` as well. Are there programs similar to npm for Node.js, or pip for Python, that can help us manage third-party modules? Yes, there is, and it's LuaRocks.

> One afternoon, I saw a colleague in Emacs complete an http get request directly with a single command, and I thought it was incredibly cool.
>
> So, I started looking for Neovim plugins, only to find that all the http request plugins I found required LuaRocks for installation.
>
> "Then let's install LuaRocks?" I thought to myself.
>
> ```
> brew install luarocks
> ```
> LuaRocks was successfully installed on my Macbook laptop, but that afternoon, no matter how hard I tried, I just couldn't reach the finish line.

What was the problem?

If you've ever done Python development, constantly switching between Python 3.10, 3.11, 3.22 on one computer. Done Node.js development, constantly switching between node 20, node 21, node 22. Done Java development, constantly switching between JVM 8, 17, 21. I encountered this exact problem.

## The Problem of Multiple Interpreters

The Lua in Neovim is LuaJIT, which is compatible with Lua 5.1. This interpreter is automatically included with Neovim when it's installed. It is an embedded execution environment.

On the other hand, if I install Lua using Homebrew, at this point in time, it will be Lua 5.4. After installation, I can directly run Lua in the Macbook's shell environment.

```
brew install lua
```

By the same token, the LuaRocks I install using Homebrew, by reasonable design, will naturally be bound to Lua 5.4, not LuaJIT.

## LuaRocks

There are two options for installing LuaRocks:

1.  Indirect management. Use the Neovim plugin [luarocks.nvim](https://github.com/vhyrro/luarocks.nvim) to install LuaRocks for you, and then let the Neovim plugin manage LuaRocks for you. This path will be simpler.
2.  Manual installation of LuaRocks. The advantage of this path is that you will become more familiar with LuaRocks commands, and if you want to apply Lua to platforms other than Neovim in the future, your knowledge of LuaRocks can be carried over.

### Manual Installation of LuaRocks

First, you need to install LuaJIT. Compared to finding Neovim's LuaJIT, installing a new one directly with Homebrew will be simpler.

```
brew install luajit
```

Next, start by downloading the LuaRocks tarball, create a system-specific `~/.luarocks-luajit` directory, set parameters, compile, and install.

```
wget https://luarocks.org/releases/luarocks-3.12.0.tar.gz
tar zxvf; cd luarocks-3.12.0
mkdir ~/.luarocks-luajit
./configure \
  --with-lua=$(brew --prefix luajit) \
  --with-lua-include=$(brew --prefix luajit)/include/luajit-2.1 \
  --lua-suffix=jit \
  --prefix=$HOME/.luarocks-luajit
make && make install
```

Finally, we need to tell the operating system where to find the executable for the `luarocks` command in the future. And use `source` to reload the shell's configuration file.

```
echo 'export PATH=$HOME/.luarocks-luajit/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

A special note: the LuaRocks version mentioned above is very important, because LuaJIT 2.0 has a 65536 constant limit. If it's not a specific LuaRocks version, you will encounter this error: `"Error: main function has more than 65536 constants"`

### Using LuaRocks

After successful installation, when you issue the command:

```
luarocks install ${package}
```

The new `${package}` will be installed under the `$HOME/.luarocks-luajit/` directory.

### Setting Neovim's Path

So far, even though the new Lua modules have been installed to the correct location, LuaJIT within Neovim still cannot find them, so we must explicitly set the path.

First, find Neovim's configuration file location and create a `lua` folder there.

```
cd ~/.config/nvim/
mkdir lua
```

In that folder, create a Lua script: `luarocks.lua`. (The file name can be arbitrary.)

```
nvim lua/luarocks.lua
```

Paste the following content into `luarocks.lua`, then save it.

```
local function add_luarocks_paths()
  local home_dir = os.getenv("HOME")
  local luarocks_path = string.format("%s/.luarocks-luajit/share/lua/5.1/?.lua;%s/.luarocks-luajit/share/lua/5.1/?/init.lua", home_dir, home_dir)
  local luarocks_cpath = string.format("%s/.luarocks-luajit/lib/lua/5.1/?.so", home_dir)
  package.path = package.path .. ";" .. luarocks_path
  package.cpath = package.cpath .. ";" .. luarocks_cpath
end

return {add_luarocks_paths = add_luarocks_paths}
```

The final step is to add a line to `~/.config/nvim/init.vim`.

```
lua require("luarocks").add_luarocks_paths()
```

Readers interested in more specific details can consider using the Ex command `:luafile %` to load the currently open Lua module in Neovim, and the Ex command `:lua print(os.getenv("HOME"))` to quickly verify the effects of these Lua commands.

## Summary

In this chapter, we discussed the installation of LuaRocks, which is much more complex than a simple Homebrew installation. In summary, what we did was:

1.  Manually install LuaRocks, downloading the tarball, recompiling, and installing, making it dependent on LuaJIT.
2.  Configure Neovim's internal paths so it can read the paths where LuaRocks installs third-party modules.
