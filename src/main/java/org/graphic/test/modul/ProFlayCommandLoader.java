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
    static Map<String, ProFlayCommand> list = new HashMap<String, ProFlayCommand>();

    public ProFlayCommandLoader()
    {
        registerDefaultCommands();
    }

    public void registerDefaultCommands()
    {
        register("test", new TestCommand());
    }

    public boolean register(String name, ProFlayCommand command)
    {
        if (ProOfLay.isEnableProFlay())
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] You trying to register command after loading server " + name);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] If you load your plugin after. Change after on before in your plugin.yml.");
            return false;
        }

        if(list.containsKey(name) || list.containsValue(command))
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] This " + command.getName() + " already registered in system.");
            return false;
        }
        else
        {
            list.put(command.getLabel(), command);
            return true;
        }
    }

    public static ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return list.get(name);
    }
}
