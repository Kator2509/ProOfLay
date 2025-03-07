package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ProFlayCommandLoader extends ProFlayCommandListener
{
    private static boolean load = true;

    /*Регистрация методов системы в CommandExecutor.*/
    public static boolean override(@NotNull Plugin plugin)
    {
        new ProFlayCommandTransfer(plugin);
        for(Map.Entry<String, ProFlayCommand> var2 : commandMap.entrySet()){
            try {
                plugin.getServer().getPluginCommand(var2.getValue().getLabel()).setExecutor(new ProFlayCommandTransfer(plugin));
            } catch (Throwable e)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Don't load the command " + (var2.getKey() != null ? var2.getKey() : "")
                        + " with error message: " + ChatColor.RED + e.getMessage());
                load = false;
            }
        }
        return load;
    }
}
