package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.Permission;

import java.util.Objects;

public class ChatMessanger extends ChatConfig implements Listener
{
    Permission permOnChat = new Permission("PoL.Chat");
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent commandCheck)
    {
        if(commandCheck.getMessage().startsWith("/say"))
        {
            commandCheck.setCancelled(true);
            String[] split = commandCheck.getMessage().split(" ");
            String message = "";
            for(int i = 1; i < split.length; i++)
            {
                message += split[i];
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(Objects.requireNonNull(getConfigChat().getString("ConsoleSenderFormat"))
                            .replace("%SENDER", commandCheck.getPlayer().getName())
                            .replace("%MESSAGE", message))));
            System.out.println(message);
        }
    }
}
