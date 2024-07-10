package org.pol.Chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class RegisterCommand extends ChatConfig implements CommandExecutor, TranslateColor
{
    private final Permission permAddChat = new Permission("PoL.Chat.Create"), permDelChat = new Permission("Pol.Chat.Delete");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if(args[0].equals("create")) {
                if (args.length >= 5 && sender.hasPermission(permAddChat)) {
                    char symbol = args[3].charAt(0);
                    StringBuilder format = new StringBuilder();
                    for (int i = 4; i < args.length; i++) {
                        format.append(args[i]);
                        if (i < args.length - 1) {
                            format.append(" ");
                        }
                    }
                    getConfigChat().set("Chats." + args[1] + ".Symbol", String.format("%c", symbol));
                    getConfigChat().set("Chats." + args[1] + ".Permission", String.format("%s", args[3]));
                    getConfigChat().set("Chats." + args[1] + ".Format", String.format("%s", format));
                    getConfigChat().save(getConfigChatFile());
                    reloadConfigChat();
                    sender.sendMessage(translateColor("&b[PoL] New chat - " + args[1] + " created with permission " +
                            args[3] + " on symbol '" + args[2] + "' with format: " + format));
                    Bukkit.getConsoleSender().sendMessage(translateColor("&b[PoL] New chat - " + args[1] + " created with permission " +
                            args[3] + " on symbol '" + args[2] + "' with format: " + format));
                    return true;
                } else if (!sender.hasPermission(permAddChat)) {
                    sender.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                            .replace("%SENDER", sender.getName())));
                    if(sender instanceof Player) {
                        Bukkit.getConsoleSender().sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                                .replace("%SENDER", sender.getName())));
                    }
                    return true;
                }
            }
            else if(args[0].equals("delete") && args.length >= 2)
            {
                if(sender.hasPermission(permDelChat)) {
                    getConfigChat().getConfigurationSection("Chats." + args[1]);
                    getConfigChat().set("Chats." + args[1], null);
                    getConfigChat().save(getConfigChatFile());
                    sender.sendMessage(translateColor("&b[PoL] Chat - " + args[1] + "Deleted."));
                    if(sender instanceof Player)
                    {
                        Bukkit.getConsoleSender().sendMessage(translateColor("&b[PoL] Chat - " + args[1] + " Deleted."));
                    }
                    reloadConfigChat();
                    return true;
                }
                else if(!sender.hasPermission(permDelChat))
                {
                    sender.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                            .replace("%SENDER", sender.getName())));
                    if(sender instanceof Player) {
                        Bukkit.getConsoleSender().sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                                .replace("%SENDER", sender.getName())));
                    }
                    return true;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            sender.sendMessage(translateColor("&b[PoL] Config error!"));
            return true;
        }
        return false;
    }
}