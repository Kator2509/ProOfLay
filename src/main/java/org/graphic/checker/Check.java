package org.graphic.checker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Check implements CheckerInterface
{
    public static boolean checkAPI(@NotNull String name)
    {
        try {
            return Bukkit.getServicesManager().getRegistration(Class.forName(name + ".class")) != null;
        } catch (Throwable e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Out of API - " + name);
            return false;
        }
    }

    public static boolean checkPlugin(@NotNull String name)
    {
        return Bukkit.getPluginManager().getPlugin(name) != null;
    }
}
