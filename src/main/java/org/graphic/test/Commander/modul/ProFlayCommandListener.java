package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.graphic.test.Commander.TestCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandListener
{
    protected static Map<String, ProFlayCommand> commandMap = new HashMap<String, ProFlayCommand>();
    private boolean moduleIsEnable = false;

    public ProFlayCommandListener() {}

    public ProFlayCommandListener(@NotNull Plugin plugin)
    {
        this.registerDefaultCommands();
        this.moduleIsEnable = true;
    }

    public void registerDefaultCommands()
    {
        this.register(new TestCommand());
    }

    public boolean register(@NotNull ProFlayCommand command)
    {
        if (moduleIsEnable)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] You trying to register command after loading server - " + command.getLabel());
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
            System.out.println(command);
            commandMap.put(command.getLabel(), command);
            return true;
        }
    }

    public boolean isRegistered(@NotNull ProFlayCommand command)
    {
        return commandMap.containsValue(command);
    }

    public ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return commandMap.get(name);
    }
}
