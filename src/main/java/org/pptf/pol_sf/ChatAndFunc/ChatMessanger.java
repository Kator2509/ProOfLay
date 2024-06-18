package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

import java.util.Objects;

public class ChatMessanger extends ChatConfig implements Listener, CommandExecutor
{
    /*
    If you want to get a permission to player need get him Chat permission
    and if enabled local or global, need get him permission on their chat.
     */
    Permission permOnChat = new Permission("PoL.Chat"), /*Permission on chat*/
            permOnGlobal = new Permission("PoL.Chat.Global"), /*Permission on global chat if that enable*/
            permOnLocalChat = new Permission("PoL.Chat.Local"), /*Permission on local chat if that enable*/
            permOnConsoleMessage = new Permission("PoL.say");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player) && args.length != 0 || (sender.hasPermission(permOnConsoleMessage) && args.length != 0))
        {
            String message = "";
            for(int i = 0; i < args.length; i++)
            {
                message += args[i];
            }
            Bukkit.broadcastMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("ConsoleSenderFormat")
                            .replace("%SENDER", sender.getName())
                            .replace("%MESSAGE", message)))));
            System.out.println(args.length);
            return true;
        }
        else if(args.length != 0)
        {
            sender.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("OutOfPermission"))
                            .replace("%SENDER", sender.getName()))));
        }
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {

    }
}
