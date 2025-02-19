package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.Configuration;
import org.graphic.CConstructor.modul.ConfigurationLoader;

public final class ProOfLay extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        ConfigurationLoader.registerPoLConfigs(this);
        Configuration conf = ConfigurationLoader.getConfiguration("TEST");
        System.out.println(conf.getString("test4"));
        System.out.println(conf.getString("test.TestString"));
    }

    @Override
    public void onDisable()
    {

    }
}
