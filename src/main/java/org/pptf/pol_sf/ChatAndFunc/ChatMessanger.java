package org.pptf.pol_sf.ChatAndFunc;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
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
            permOnGlobalChat = new Permission("PoL.Chat.Global"), /*Permission on global chat*/
            permOnLocalChat = new Permission("PoL.Chat.Local"), /*Permission on local chat*/
            permOnTradeChat = new Permission("PoL.Chat.Trade"), /*Permission on trade chat*/
            permOnAdminChat = new Permission("PoL.Chat.Admin"), /*Permission on admin chat*/
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

        if(!getConfigChat().getBoolean("GlobalChatEnable") && event.getPlayer().hasPermission(permOnChat))
        {
            event.setFormat(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("MessageFormat"))
                            .replace("%PREFIX", prefix)
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix)
                            .replace("%MESSAGE", event.getMessage()))));
        }
        else if(getConfigChat().getBoolean("GlobalChatEnable") && event.getPlayer().hasPermission(permOnGlobalChat)
                && event.getMessage().charAt(0) == Objects.requireNonNull(getConfigChat().getString("SymbolGlobalChat")).charAt(0))
        {
            event.setFormat(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("GlobalChatFormat"))
                            .replace("%PREFIX", prefix)
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix)
                            .replace("%MESSAGE", event.getMessage().substring(1)))));
        }
        else if(getConfigChat().getBoolean("TradeChatEnable") && event.getPlayer().hasPermission(permOnTradeChat)
                && event.getMessage().charAt(0) == Objects.requireNonNull(getConfigChat().getString("SymbolTradeChat")).charAt(0))
        {
            event.setFormat(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("TradeChatFormat"))
                            .replace("%PREFIX", prefix)
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix)
                            .replace("%MESSAGE", event.getMessage().substring(1)))));
        }
        else if(getConfigChat().getBoolean("GlobalChatEnable") && event.getPlayer().hasPermission(permOnLocalChat))
        {
            Player playerSender = event.getPlayer();
            for (Player player:Bukkit.getOnlinePlayers()) {
                if (player.getLocation().distance(playerSender.getLocation()) <= getConfigChat().getInt("rangeChat")) {
                    player.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                                            Objects.requireNonNull(getConfigChat().getString("LocalChatFormat"))
                                                    .replace("%PREFIX", prefix))
                                    .replace("%PLAYER", event.getPlayer().getName())
                                    .replace("%SUFFIX", suffix))
                            .replace("%MESSAGE", event.getMessage()));
                }
            }
            Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                                    Objects.requireNonNull(getConfigChat().getString("LocalChatFormat"))
                                            .replace("%PREFIX", prefix))
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix))
                    .replace("%MESSAGE", event.getMessage()));
            event.setCancelled(true);
        }
        else
        {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getConfigChat().getString("DontHavePermission"))
                            .replace("%PREFIX", prefix)
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix)
                            .replace("%MESSAGE", event.getMessage())));
            event.setCancelled(true);
        }
    }
}
