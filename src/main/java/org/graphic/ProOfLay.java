package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.graphic.test.modul.ProFlayEventManager;
import org.graphic.test.modul.ProFlayCommandListener;
import org.graphic.test.modul.ProFlayCommandLoader;

public final class ProOfLay extends JavaPlugin {
    private ProFlayCommandLoader cmd;
    private static boolean enable = false;

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        ConfigurationLoader.registerPoLConfigs(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Command module.");
        cmd = new ProFlayCommandLoader();
        this.getServer().getPluginCommand("test").setExecutor(new ProFlayCommandListener());

        // Register the event manager
        getServer().getPluginManager().registerEvents(new ProFlayEventManager(), this);

        enable = true;
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
