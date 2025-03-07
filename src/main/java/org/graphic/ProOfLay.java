package org.graphic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.graphic.test.Commander.modul.ProFlayCommandListener;
import org.graphic.test.Commander.modul.ProFlayCommandLoader;

public final class ProOfLay extends JavaPlugin
{
    private ProFlayCommandListener cmd;
    private static ProOfLay root;
    private static boolean enable = false;

    @Override
    public void onEnable()
    {
        /*Конфигурационный загрузчик, чья задача создавать собственные конфигурации,
        * предоставляя более расширенный функционал. Он вытягивает через прямой поток из самого конфигурации,
        * вместо того, чтобы хранить конфигурации и хранит лишь только путь к конфигурационному файлу.*/
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Configuration module.");
        ConfigurationLoader.registerProFlayConfigs(this);

        /*Командный загрузчик, чья задача через CommandExecutor загружать все команды используя
        * собственный командное представление - ProFlayCommand.*/
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Trying to load a Command module.");
        if(!ProFlayCommandLoader.override(this))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Problem with Command module.");
        }


        enable = true;
    }

    @Override
    public void onDisable()
    {
        enable = false;
    }

    /*Получение root.*/
    public static ProOfLay getInstance()
    {
        if(!enable) {
            return root;
        }
        else
        {
            return null;
        }
    }

    /*Запущен ли плагин.*/
    public static boolean isEnableProFlay()
    {
        return enable;
    }
}