package org.graphic.test.modul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandListener extends ProFlayCommandLoader implements CommandExecutor {

    public ProFlayCommandListener()
    {
        super();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        if(!this.list.containsKey(label))
        {
            return false;
        }
        return false;
    }
}
