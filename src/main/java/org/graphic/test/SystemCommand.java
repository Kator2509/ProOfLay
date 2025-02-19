package org.graphic.test;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class SystemCommand extends ProFlayCommand
{
    protected SystemCommand(@NotNull String name)
    {
        super(name);
    }
    
    protected SystemCommand(@NotNull String name, @NotNull String description, @NotNull String usage, @NotNull ArrayList<String> alies)
    {
        super(name, description, usage, alies);
    }
}
