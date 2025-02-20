package org.graphic.test.modul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.graphic.ProOfLay;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandListener implements CommandExecutor {
    public ProFlayCommandListener()
    {
        ProOfLay.getRoot().getCommand("ProFlay").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }
}
