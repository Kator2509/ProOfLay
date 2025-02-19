package org.graphic.test;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;

public abstract class ProFlayCommand
{
    private Permission permission;
    private String usage, description, permmessage, name;
    private ArrayList<String> alies;

    protected ProFlayCommand(String name)
    {
        this(name, "", "/" + name, new ArrayList<>());
    }

    protected ProFlayCommand(String name, String description, String usage, ArrayList<String> alies)
    {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.alies = alies;
    }

    public abstract boolean execute(CommandSender sender, String label, String[] args);


}
