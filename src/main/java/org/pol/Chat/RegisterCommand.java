package org.pol.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class RegisterCommand extends ChatConfig implements CommandExecutor, TranslateColor
{
    Permission permAddChat = new Permission("PoL.Chat.Create");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if(args.length == 4 && sender.hasPermission(permAddChat)) {
                char symbol = args[1].charAt(0);
                StringBuilder format = new StringBuilder();
                for(int i = 3; i < args.length; i++)
                {
                    format.append(args[i]);
                }
                getConfigChat().set("Chats." + args[0] + ".Symbol", String.format("%c", symbol));
                getConfigChat().set("Chats." + args[0] + ".Permission", String.format("%s", args[2]));
                getConfigChat().set("Chats." + args[0] + ".Format", String.format("%s", format));
                getConfigChat().save(getConfigChatFile());
                reloadConfigChat();
                sender.sendMessage(translateColor("&b[PoL] New chat - " + args[0] + " created with permission " +
                        args[2] + " on symbol '" + args[1] + "' with format: " + args[3]));
                return true;
            } else if(!sender.hasPermission(permAddChat)){
                sender.sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                        .replace("%SENDER", sender.getName())));
                return true;
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