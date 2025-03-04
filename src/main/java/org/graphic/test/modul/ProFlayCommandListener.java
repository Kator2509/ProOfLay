package org.graphic.test.modul;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.graphic.test.ProFlayCommandMap;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandListener extends ProFlayCommandLoader implements Listener {
    private final ProFlayCommandMap commandMap;

    public ProFlayCommandListener()
    {
        this.commandMap = null;
    }

    public ProFlayCommandListener(@NotNull Plugin plugin)
    {
        super(plugin);
        this.commandMap = new ProFlayCommandMap(plugin);
    }

    
}
