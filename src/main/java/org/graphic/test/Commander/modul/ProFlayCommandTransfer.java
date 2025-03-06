package org.graphic.test.Commander.modul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandTransfer extends ProFlayCommandListener implements CommandExecutor {

    public ProFlayCommandTransfer(){}

    public ProFlayCommandTransfer(@NotNull Plugin plugin)
    {
        super(plugin);
        System.out.println("TEST");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(commandMap.containsKey(label) && sender instanceof Player)
        {
            return commandMap.get(label).run(sender, args);
        }
        else
        {
            return commandMap.get(label).consoleRun(args);
        }
    }
}
