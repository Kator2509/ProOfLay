package org.graphic.test;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends ProFlayCommand
{
    public TestCommand()
    {
        super("test", "test", new String[]{"test1, test2"});
        this.permission = "test.command";
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String label, @NotNull String usage, @NotNull String[] args) {
        Bukkit.getServer().getConsoleSender().sendMessage("TEST PASSED");
        return false;
    }
}
