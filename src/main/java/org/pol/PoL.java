package org.pol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.pol.Chat.ChatConfig;
import org.pol.Chat.ChatEvent;
import org.pol.RTP.RTPEvent;

import java.io.File;
import java.util.Objects;

public final class PoL extends JavaPlugin implements Listener, CommandExecutor {

    /*    _______       ____     .
    /    |       \     /    \    |
    /    |        |   /      \   |
    /    |_______/   |        |  |
    /    |           |        |  |
    /    |            \      /   |
    /    |             \____/    |________
    */

    public File cfgFile = new File(this.getDataFolder(), "Config.yml");
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        if(!cfgFile.exists()) {
            saveResource("Config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(cfgFile);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
        if(config.getBoolean("ChatModule")) {
            ChatConfig chatCFG = new ChatConfig();
            chatCFG.loadChatCFG(this);
            Objects.requireNonNull(this.getCommand("say")).setExecutor(new ChatEvent());
            Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Chat load.");
        }
        if(config.getBoolean("RandomTeleport"))
        {
            RTPEvent rtpConfig = new RTPEvent();
            rtpConfig.loadRtpCFG(this);
            Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RTPEvent());
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF rtp load.");
        }
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "| PoL-SF Start.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "| PoL-SF disabled.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
    }

    public static FileConfiguration getConfiguration()
    {
        return config;
    }

    Permission permReload = new Permission("PoL.reload");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if(sender.hasPermission(permReload) && args.length != 0 && args[0].equals("reload"))
        {
            config = YamlConfiguration.loadConfiguration(cfgFile);
            RTPEvent.reloadRTPConfig();
            ChatConfig.reloadConfigChat();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("ReloadMessage"))));
            return true;
        }
        else if(args.length == 0 || args.length > 2)
        {
            return false;
        }
        else if(!sender.hasPermission(permReload))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("DontHavePermission"))
                    .replace("%SENDER", sender.getName())));
            return true;
        }
        return false;
    }
}
