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
    private static final Map<String, Configuration> configs = new HashMap<String, Configuration>();

    /*Регистрация в системе конфигурационного файла.*/
    public static boolean register(@NotNull String name, @NotNull Plugin plugin, @NotNull String resourcePath, @NotNull String outPath)
    {
        Preconditions.checkArgument(resourcePath != null, ChatColor.AQUA + "[ProFlay] resourcePath is null: " + resourcePath + " You need load from your resources plugin.");
        Preconditions.checkArgument(name != null, ChatColor.AQUA + "[ProFlay] Name configuration is null. That can be cause a problem: " + name);
        if(!configs.containsValue(new Configuration(plugin, resourcePath, outPath)))
        {
            configs.put(name, new Configuration(plugin, resourcePath, outPath));
            return true;
        }
        return false;
    }

    public static void registerProFlayConfigs(@NotNull Plugin plugin)
    {
        /*
        * Не нужно загружать данный метод, он лишь загружает дефолтное значения для определенных конфигураций.
        * Чтобы внести собственные изменения, вызовете MemorySpace - setData(@NotNull String pathFile) для установки собственного
        * конфигурационного пути. Но прежде создайте конфиг Configuration - createConfig(@NotNull boolean reset).*/
        if(register("main", plugin, "config.yml", "plugins/ProFlay/Config.yml") ||
        register("TEST", plugin, "test.yml", "plugins/test/test.yml"))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Configuration module loaded.");
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Another config not loaded.");
        }
    }

    /*Получение конфигурации из Map.*/
    public static Configuration getConfiguration(@NotNull String name)
    {
        return configs.get(name);
    }
}
