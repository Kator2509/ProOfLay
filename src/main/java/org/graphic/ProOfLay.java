package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.graphic.test.modul.ProFlayCommandLoader;

public final class ProOfLay extends JavaPlugin
{
    protected ProFlayCommandLoader cmd;
    protected ConfigurationLoader cl;
    private static boolean enable = false;

    /**/
    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        cl = new ConfigurationLoader(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Command module.");
        cmd = new ProFlayCommandLoader();
        enable = true;
        if (enable)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Module is enabled.");
        }
        else if(!enable)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] " + ChatColor.RED + "Something is wrong. Need feed back or return back changes.");
        }
    }

    @Override
    public void onDisable()
    {
        enable = false;
    }

    public static boolean isEnableProFlay()
    {
        return enable;
    }
}
