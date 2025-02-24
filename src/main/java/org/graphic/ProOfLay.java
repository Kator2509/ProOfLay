package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;

public final class ProOfLay extends JavaPlugin
{
    public static ProOfLay root;

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        ConfigurationLoader.registerPoLConfigs(this);

    }

    @Override
    public void onDisable()
    {

    }

    public static ProOfLay getRoot() {
        return root;
    }
}
