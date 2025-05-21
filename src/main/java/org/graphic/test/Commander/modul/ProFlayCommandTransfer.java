package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.graphic.CConstructor.Configuration;
import org.graphic.CConstructor.ConfigurationException;
import org.graphic.CConstructor.modul.ConfigurationLoader;
import org.jetbrains.annotations.NotNull;

public class ProFlayCommandTransfer extends ProFlayCommandListener implements CommandExecutor {
    protected Configuration config;

    public ProFlayCommandTransfer(@NotNull Plugin plugin) {
        super(plugin);
        try {
            this.config = ConfigurationLoader.get("main");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Failed to load config: " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        try {
            if (commandMap.containsKey(label)) {
                if (sender instanceof Player) {
                    return commandMap.get(label).run(sender, args);
                } else if (sender instanceof ConsoleCommandSender) {
                    return commandMap.get(label).consoleRun(args);
                }
            }

            if (!commandMap.containsKey(label)) {
                String message = config.getString("OutCommandMessage");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return true;
            }

            return false;
        } catch (ConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ProFlay] Configuration error: " + e.getMessage());
            return false;
        }
    }
}
