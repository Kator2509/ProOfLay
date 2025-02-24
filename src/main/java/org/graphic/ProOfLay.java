package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;

public final class ProOfLay extends JavaPlugin
{
    private static ProOfLay root;
    private boolean enable = false;

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

    public static ProOfLay getRoot() {
        return root;
    }

    public boolean isEnableProFlay()
    {
        return this.enable;
    }
}
