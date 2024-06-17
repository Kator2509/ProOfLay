package org.pptf.pol_sf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.pptf.pol_sf.ChatAndFunc.ChatConfig;
import org.pptf.pol_sf.ChatAndFunc.ChatMessanger;
import org.pptf.pol_sf.ChatAndFunc.RandomTeleport;
import org.pptf.pol_sf.JoinAndLeaveEvent.Join;
import org.pptf.pol_sf.JoinAndLeaveEvent.RegisterEvent.RegisterMethod;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class PoL_SF extends JavaPlugin implements Listener, CommandExecutor {
    @Override
    public void onEnable() {
        saveResource("Config.yml", false);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
        if(getConfig().getBoolean("ChatModule")) {
            ChatConfig chatCFG = new ChatConfig();
            chatCFG.loadChatCFG(this);
            ChatConfig.chatCFG = getConfig();
            this.getServer().getPluginManager().registerEvents(new ChatMessanger(), this);
            ChatConfig.reloadCFGChat();
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Chat load.");
        }
        if(getConfig().getBoolean("RandomTeleport"))
        {
            RandomTeleport rtpCFG = new RandomTeleport();
            rtpCFG.loadRtpCFG(this);
            Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleport());
            RandomTeleport.rtpCFG = getConfig();
            RandomTeleport.rtpMessage = getConfig();
            RandomTeleport.reloadRTPConfig();
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF rtp load.");
        }
        if(getConfig().getBoolean("JoinMessage"))
        {
            Join join = new Join();
            join.loadJoin(this);
            this.getServer().getPluginManager().registerEvents(new Join(), this);
            Join.joinMSG = getConfig();
            Join.reloadCFGJoin();
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Join and Quit load.");
        }
        if(getConfig().getBoolean("EnableRegisterMethod"))
        {
            RegisterMethod.reloadRegister();
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
            RegisterMethod.reloadRegister();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("ReloadMessage"))));
        }
        else if(args.length == 0 || args.length > 2)
        {
            return false;
        }
        else if(!sender.hasPermission(permReload))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("DontHavePermission"))));
        }
        return true;
    }
}