package org.graphic.test;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ProFlayCommand
{
    protected String permission, usage, label, name, description;
    protected List<String> alias;

    public ProFlayCommand()
    {
        this.name = null;
        this.label = null;
        this.usage = null;
        this.alias = null;
    }

    public ProFlayCommand(@NotNull String name, @NotNull String label, @NotNull String[] alias)
    {
        this.name = name;
        this.label = label;
        this.usage = "/" + label + " " + (description != null ? description : null);
        this.alias = new ArrayList<>(Arrays.asList(alias));
    }

    public abstract boolean run(@NotNull CommandSender sender, @NotNull String[] args);

    public String getPermissionName()
    {
        return this.permission;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLabel()
    {
        return this.label;
    }
}
