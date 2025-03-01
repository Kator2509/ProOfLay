package org.graphic.test.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.graphic.ProOfLay;
import org.graphic.test.ProFlayCommand;
import org.graphic.test.TestCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandLoader
{
    protected static Map<String, ProFlayCommand> commandMap = new HashMap<String, ProFlayCommand>();
    private static boolean moduleIsEnable = false;

    public ProFlayCommandLoader()
    {
        registerDefaultCommands();
        moduleIsEnable = true;
    }

    public void registerDefaultCommands()
    {
        register("test", new TestCommand());
    }

    public static boolean register(@NotNull String name, @NotNull ProFlayCommand command)
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

    public static boolean isRegistered(@NotNull ProFlayCommand command)
    {
        if(commandMap.containsValue(command))
        {
            return true;
        }
        return false;
    }

    public static ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return commandMap.get(name);
    }
}
