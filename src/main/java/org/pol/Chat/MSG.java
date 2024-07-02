package org.pol.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MSG extends ChatConfig implements CommandExecutor, TranslateColor
{
    Permission msg = new Permission("PoL.msg");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length >= 2 && sender.hasPermission(msg))
        {
            try {
                Player player = Bukkit.getPlayer(args[0]);
                StringBuilder message = new StringBuilder();
                for(int i = 1; i < args.length; i++)
                {
                    message.append(args[i]);
                }
                if (Objects.requireNonNull(player).isOnline()) {
                    sender.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("MSGSenderFormat"))
                            .replace("%PLAYER", args[0])
                            .replace("%MESSAGE", message.toString())
                            .replace("%SENDER", sender.getName())));
                    player.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("MSGMessageFormat"))
                            .replace("%PLAYER", args[0])
                            .replace("%MESSAGE", message.toString())
                            .replace("%SENDER", sender.getName())));
                }
            } catch (NullPointerException e)
            {
                sender.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("PlayerIsOffline"))
                        .replace("%PLAYER", args[0])
                        .replace("%MESSAGE", args[1])
                        .replace("%SENDER", sender.getName())));
            }
            return true;
        }
        else if(!sender.hasPermission(msg))
        {
            sender.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                            .replace("%SENDER", sender.getName()))));
            return true;
        }
        return false;
    }
}
