package org.graphic.test;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandMap
{
    protected Map<String, ProFlayCommand> commandMap = new HashMap<>();
    protected Plugin root;

    public ProFlayCommandMap()
    {

    }

    public ProFlayCommandMap(@NotNull Plugin plugin)
    {
        this.root = plugin;
    }
}

