package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.graphic.test.modul.ProFlayCommandLoader;

public final class ProOfLay extends JavaPlugin
{
    private ProFlayCommandLoader cmd;
    private static ProOfLay root;
    private static boolean enable = false;

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        ConfigurationLoader.registerPoLConfigs(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Command module.");


        enable = true;
    }

    @Override
    public void onDisable()
    {
        enable = false;
    }

    public static ProOfLay getInstance()
    {
        return root;
    }

    public static boolean isEnableProFlay()
    {
        return enable;
    }
}