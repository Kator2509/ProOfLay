package org.graphic.test.modul;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ProFlayCommandListener
{
    Map<String, ProFlayCommand> list = new HashMap<String, ProFlayCommand>();

    public ProFlayCommandListener()
    {
        register();
    }

    public boolean register()
    {
        return false;
    }

    public ProFlayCommand getProFlayCommand(@NotNull String name)
    {
        return list.get(name);
    }
}
