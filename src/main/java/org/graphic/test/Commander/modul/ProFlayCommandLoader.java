package org.graphic.test.Commander.modul;

import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ProFlayCommandLoader extends ProFlayCommandListener
{
    public static boolean override(@NotNull Plugin plugin)
    {
        new ProFlayCommandTransfer(plugin);
        System.out.println("TEST EXECUTOR");
        for(Map.Entry<String, ProFlayCommand> var2 : commandMap.entrySet()){
            System.out.println("TEST OVERRIDE");
            System.out.println(var2.getValue().getLabel());
            plugin.getServer().getPluginCommand(var2.getValue().getLabel()).setExecutor(new ProFlayCommandTransfer(plugin));
        }
        return false;
    }
}
