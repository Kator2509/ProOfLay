package org.pol.Chat;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ChatEvent extends ChatConfig implements Listener, CommandExecutor, TranslateColor
{
    Permission permOnConsoleMessage = new Permission("PoL.say");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if(!(sender instanceof Player) && args.length != 0 || (sender.hasPermission(permOnConsoleMessage) && args.length != 0))
        {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg);
            }
            Bukkit.broadcastMessage(Objects.requireNonNull(translateColor(
                    Objects.requireNonNull(Objects.requireNonNull(getConfigChat().getString("ConsoleSenderFormat"))
                            .replace("%SENDER", sender.getName())
                            .replace("%MESSAGE", message.toString())))));
            return true;
        }
        else if(args.length != 0)
        {
            sender.sendMessage(Objects.requireNonNull(translateColor(
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
        String message = event.getMessage(), playerName = event.getPlayer().getName();
        if(prefix == null)
        {
            prefix = "";
        }
        if(suffix == null)
        {
            suffix = "";
        }

        if(message.length() != 1) {
            ConfigurationSection sec = getConfigChat().getConfigurationSection("Chats");
            try {
                for (String key : sec.getKeys(false)) {
                    if (Objects.requireNonNull(getConfigChat().getString("Chats." + key + ".Symbol")).charAt(0) == message.charAt(0)) {
                        if (event.getPlayer().hasPermission(Objects.requireNonNull(getConfigChat().getString("Chats." + key + ".Permission")))) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.hasPermission(Objects.requireNonNull(getConfigChat().getString("Chats." + key + ".Permission")))) {
                                    player.sendMessage(Objects.requireNonNull(translateColor(
                                            Objects.requireNonNull(Objects.requireNonNull(getConfigChat().getString("Chats." + key + ".Format"))
                                                    .replace("%PREFIX", prefix)
                                                    .replace("%PLAYER", playerName)
                                                    .replace("%SUFFIX", suffix)
                                                    .replace("%MESSAGE", message.substring(1))))));
                                }
                            }
                            Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(translateColor(
                                    Objects.requireNonNull(Objects.requireNonNull(getConfigChat().getString("Chats." + key + ".Format"))
                                            .replace("%PREFIX", prefix)
                                            .replace("%PLAYER", playerName)
                                            .replace("%SUFFIX", suffix)
                                            .replace("%MESSAGE", message.substring(1))))));
                        } else {
                            event.getPlayer().sendMessage(Objects.requireNonNull(translateColor(getConfigChat().getString("DontHavePermission"))));
                        }
                        event.setCancelled(true);
                    }
                }
                if (getConfigChat().getBoolean("localChatEnable") && !event.isCancelled()) {
                    Player playerSender = event.getPlayer();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getLocation().distance(playerSender.getLocation()) <= getConfigChat().getInt("rangeChat")) {
                            player.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                                                    Objects.requireNonNull(getConfigChat().getString("LocalChatFormat"))
                                                            .replace("%PREFIX", prefix))
                                                            .replace("%PLAYER", event.getPlayer().getName())
                                                            .replace("%SUFFIX", suffix))
                                                            .replace("%MESSAGE", message));
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(translateColor(
                                            Objects.requireNonNull(getConfigChat().getString("LocalChatFormat"))
                                                    .replace("%PREFIX", prefix))
                                                    .replace("%PLAYER", playerName)
                                                    .replace("%SUFFIX", suffix))
                                                    .replace("%MESSAGE", message));
                    event.setCancelled(true);
                } else if (!event.isCancelled()) {
                    event.setFormat(translateColor(Objects.requireNonNull(getConfigChat().getString("MessageFormat"))
                            .replace("%PREFIX", prefix)
                            .replace("%PLAYER", playerName)
                            .replace("%SUFFIX", suffix)
                            .replace("%MESSAGE", message)));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                event.getPlayer().sendMessage(translateColor("&c[PoL] - Config error!"));
                event.setCancelled(true);
            }
        }
        else
        {
            if(!Objects.requireNonNull(getConfigChat().getString("MessageIsEmpty")).isEmpty()) {
                event.getPlayer().sendMessage(translateColor(Objects.requireNonNull(getConfigChat().getString("MessageIsEmpty"))
                        .replace("%PREFIX", prefix)
                        .replace("%PLAYER", playerName)
                        .replace("%SUFFIX", suffix)
                        .replace("%MESSAGE", message)));
            }
            event.setCancelled(true);
        }
    }
}
