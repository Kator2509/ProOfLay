package org.graphic.test.Commander.modul;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.graphic.CConstructor.Configuration;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandTransfer extends ProFlayCommandListener implements CommandExecutor {
    protected Configuration config = ConfigurationLoader.getConfiguration("main");

    /*Класс представляет собой трансфер, который предоставляет ProFlayCommand
    * выполняться в системе с собственными методами.*/

    public ProFlayCommandTransfer(){}

    public ProFlayCommandTransfer(@NotNull Plugin plugin)
    {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(commandMap.containsKey(label) && sender instanceof Player)
        {
            return commandMap.get(label).run(sender, args);
        }
        else if(sender instanceof ConsoleCommandSender)
        {
            return commandMap.get(label).consoleRun(args);
        }


        if(!commandMap.containsKey(label))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("OutCommandMessage")));
            return true;
        }


        return false;
    }
}
