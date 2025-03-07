package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.graphic.test.Commander.TestCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandListener
{
    protected static Map<String, ProFlayCommand> commandMap = new HashMap<String, ProFlayCommand>();
    private static boolean moduleIsEnable = false;

    public ProFlayCommandListener() {}

    /*Конструктор листенера команд.*/
    public ProFlayCommandListener(@NotNull Plugin plugin)
    {
        this.registerDefaultCommands();
        moduleIsEnable = true;
    }

    /*Метод регистрации дефолтных команд системы.
    * Можно перезаписать их и внести собственную логику, если вызвать register(ProFlayCommand command, String name).*/
    public void registerDefaultCommands()
    {
        if
        (!(
                this.register(new TestCommand(), false)
        ))
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Default command not load.");
        }
    }

    /*Метод регистрации внутри системы и проверка на запущенный модуль.
    * Если модуль запущен moduleIsEnable = true, то остановка регистрации и лог сообщение.
    * Если команда зарегистрирована isRegistered = true, то остановка регистрации и лог сообщение.
    * Если ни одно условие не выполняется, то команда регистрируется в системе для последующей его вызова.*/
    public boolean register(@NotNull ProFlayCommand command, boolean override)
    {
        if (moduleIsEnable)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] You trying to register command after loading server - " + command.getLabel());
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] If you load your plugin after. Change after on before in your plugin.yml.");
            return false;
        }

        if(isRegistered(command))
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] This " + command.getName() + " already registered in system.");
            return false;
        }

        commandMap.put(command.getLabel(), command);
        return true;
    }

    /*Производит проверка на наличие команды.*/
    public boolean isRegistered(@NotNull ProFlayCommand command)
    {
        return commandMap.containsValue(command);
    }

    /*Получить определенную команду.*/
    public ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return commandMap.get(name);
    }
}
