package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ProFlayCommandLoader extends ProFlayCommandListener {
    private static boolean load = true;

    public static boolean override(@NotNull Plugin plugin) {
        ProFlayCommandTransfer transfer = new ProFlayCommandTransfer(plugin);
        for (Map.Entry<String, ProFlayCommand> entry : commandMap.entrySet()) {
            try {
                plugin.getServer().getPluginCommand(entry.getKey()).setExecutor(transfer);
            } catch (Throwable e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Error loading command " +
                        entry.getKey() + ": " + ChatColor.RED + e.getMessage());
                load = false;
            }
        }
        return load;
    }
}
