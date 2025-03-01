package org.graphic.CConstructor.modul;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.CConstructor.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationLoader
{
    protected static final Map<String, Configuration> configs = new HashMap<String, Configuration>();
    protected static boolean moduleIsEnable = false;

    /*You can create your instance of this object.*/
    public ConfigurationLoader(@NotNull Plugin plugin)
    {
        registerProFlayConfigs(plugin);
        moduleIsEnable = true;
    }

    /*К*/
    public static boolean register(@NotNull String name, @NotNull Plugin plugin, @NotNull String resourcePath, @NotNull String outPath)
    {
        Preconditions.checkArgument(resourcePath != null, ChatColor.AQUA + "[ProFlay] resourcePath is null: " + resourcePath + " You need load from your resources plugin.");
        Preconditions.checkArgument(name != null, ChatColor.AQUA + "[ProFlay] Name configuration is null. That can be cause a problem: " + name);
        if(!isRegistered(new Configuration(plugin, resourcePath, outPath)) && !moduleIsEnable)
        {
            configs.put(name, new Configuration(plugin, resourcePath, outPath));
            return true;
        }
        return false;
    }

    public static boolean isRegistered(@NotNull Configuration configuration)
    {
        if(configs.containsValue(configuration))
        {
            return true;
        }
        return false;
    }

    public static void registerProFlayConfigs(@NotNull Plugin plugin)
    {
        /* You don't need to load this method. ProFlay automatically load this.
        * There a simple method to register your config in system.*/
        register("TEST", plugin, "test.yml", "plugins/test/test.yml");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Configuration module loaded.");
    }

    public static Configuration getConfiguration(@NotNull String name)
    {
        return configs.get(name);
    }
}
