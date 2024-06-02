package org.pptf.pol_sf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.pptf.pol_sf.ChatAndFunc.ChatConfig;
import org.pptf.pol_sf.ChatAndFunc.ChatMessanger;
import org.pptf.pol_sf.ChatAndFunc.RandomTeleport;
import org.pptf.pol_sf.JoinAndLeaveEvent.Join;

import java.io.IOException;
import java.util.Objects;

public final class PoL_SF extends JavaPlugin implements Listener, CommandExecutor {
    public final FileConfiguration cfg = this.getConfig();

    @Override
    public void onEnable() {
        cfg.addDefault("ChatModule", true);
        cfg.addDefault("RandomTeleport", true);
        cfg.addDefault("JoinMessage", true);
        cfg.addDefault("ReloadMessage", "&3[PoL] Plugin reload.");
        cfg.addDefault("DontHavePermission", "&c[PoL] You don't have permission.");
        cfg.options().copyDefaults(true);
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
        if(cfg.getBoolean("ChatModule")) {
            ChatConfig chatCFG = new ChatConfig();
            chatCFG.loadChatCFG(this);
            ChatConfig.chatCFG = getConfig();
            this.getServer().getPluginManager().registerEvents(new ChatMessanger(), this);
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Chat load.");
        }
        if(cfg.getBoolean("RandomTeleport"))
        {
            RandomTeleport rtpCFG = new RandomTeleport();
            rtpCFG.loadRtpCFG(this);
            Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleport());
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF rtp load.");
        }
        if(cfg.getBoolean("JoinMessage"))
        {
            Join join = new Join();
            join.loadJoin(this);
            this.getServer().getPluginManager().registerEvents(new Join(), this);
            Join.joinMSG = getConfig();
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Join and Quit load.");
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

    Permission permReload = new Permission("PoL.reload");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.hasPermission(permReload) && args.length != 0 && args[0].equals("reload"))
        {
            this.reloadConfig();
            RandomTeleport.reloadRTPConfig();
            ChatConfig.reloadCFGChat();
            Join.reloadCFGJoin();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(cfg.getString("ReloadMessage"))));
        }
        else if(args.length == 0 || args.length > 2)
        {
            return false;
        }
        else if(!sender.hasPermission(permReload))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(cfg.getString("DontHavePermission"))));
        }
        return true;
    }
}