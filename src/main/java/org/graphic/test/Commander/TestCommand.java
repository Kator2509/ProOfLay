package org.graphic.test.Commander;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends ProFlayCommand
{
    public TestCommand()
    {
        super("test", "test", new String[]{"test1, test2"});
        this.permission = "test.command";
        this.description = "test command.";
    }

    @Override
    public boolean consoleRun(@NotNull String[] args) {
        Bukkit.getConsoleSender().sendMessage("CONSOLE PASSED");
        return true;
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        Bukkit.getServer().getConsoleSender().sendMessage("TEST PASSED");
        return true;
    }
}
