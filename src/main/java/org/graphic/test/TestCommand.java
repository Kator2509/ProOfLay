package org.graphic.test;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class TestCommand extends SystemCommand
{
    TestCommand()
    {
        super("test", "test", "test", new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        Bukkit.getServer().getConsoleSender().sendMessage("CONSOLE TEST PASSED");
        sender.sendMessage("TEST PASSED");
        return false;
    }
}
