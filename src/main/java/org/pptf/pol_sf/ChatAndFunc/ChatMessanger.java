package org.pptf.pol_sf.ChatAndFunc;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
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
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if(!(sender instanceof Player) && args.length != 0 || (sender.hasPermission(permOnConsoleMessage) && args.length != 0))
        {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg);
            }
            Bukkit.broadcastMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(Objects.requireNonNull(getConfigChat().getString("ConsoleSenderFormat"))
                            .replace("%SENDER", sender.getName())
                            .replace("%MESSAGE", message.toString())))));
            return true;
        }
        else if(args.length != 0)
        {
            sender.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                            .replace("%SENDER", sender.getName()))));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        LuckPerms ap = null;
        if(provider != null)
        {
            ap = provider.getProvider();
        }
        User user = Objects.requireNonNull(ap).getUserManager().getUser(event.getPlayer().getName());
        String prefix = Objects.requireNonNull(user).getCachedData().getMetaData().getPrefix();
        String suffix = Objects.requireNonNull(user).getCachedData().getMetaData().getSuffix();
        if(prefix == null)
        {
            prefix = "";
        }
        if(suffix == null)
        {
            suffix = "";
        }

        if(!getConfigChat().getBoolean("GlobalChatEnable"))
        {
            event.setFormat(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("MessageFormat"))
                            .replace("%PREFIX", Objects.requireNonNull(prefix))
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", Objects.requireNonNull(suffix))
                            .replace("%MESSAGE", event.getMessage()))));
        }
    }
}
