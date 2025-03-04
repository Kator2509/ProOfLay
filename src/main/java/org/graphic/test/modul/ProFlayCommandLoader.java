package org.graphic.test.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.ProFlayCommand;
import org.graphic.test.TestCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandLoader
{
    protected static Map<String, ProFlayCommand> commandMap = new HashMap<String, ProFlayCommand>();
    private boolean moduleIsEnable = false;

    public ProFlayCommandLoader() {}

    public ProFlayCommandLoader(@NotNull Plugin plugin)
    {
        this.registerDefaultCommands();
        this.moduleIsEnable = true;
    }

    public void registerDefaultCommands()
    {
        this.register("test", new TestCommand());
    }

    public boolean register(@NotNull String name, @NotNull ProFlayCommand command)
    {
        if (!moduleIsEnable)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] You trying to register command after loading server " + name);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] If you load your plugin after. Change after on before in your plugin.yml.");
            return false;
        }

        if(isRegistered(command))
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] This " + command.getName() + " already registered in system.");
            return false;
        }
        else
        {
            commandMap.put(command.getLabel(), command);
            return true;
        }
    }

    public boolean isRegistered(@NotNull ProFlayCommand command)
    {
        if(commandMap.containsValue(command))
        {
            return true;
        }
        return false;
    }

    public ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return commandMap.get(name);
    }
}
