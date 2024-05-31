package org.pptf.pol_sf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.pptf.pol_sf.ChatAndFunc.ChatConfig;
import org.pptf.pol_sf.ChatAndFunc.ChatMessanger;
import org.pptf.pol_sf.ChatAndFunc.RandomTeleport;
import org.pptf.pol_sf.JoinAndLeaveEvent.Join;

import java.util.Objects;

public final class PoL_SF extends JavaPlugin implements Listener {

    private FileConfiguration cfg = this.getConfig();

    @Override
    public void onEnable() {
        cfg.addDefault("ChatModule", true);
        cfg.addDefault("RandomTeleport", true);
        cfg.addDefault("JoinMessage", true);
        cfg.options().copyDefaults(true);
        saveConfig();
        getConfig().options().copyDefaults(true);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------o");
        if(cfg.getBoolean("ChatModule")) {
            ChatConfig chatCFG = new ChatConfig();
            chatCFG.loadChatCFG(this);
            chatCFG.chatCFG = getConfig();
            this.getServer().getPluginManager().registerEvents(new ChatMessanger(), this);
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Chat load.");
        }
        if(cfg.getBoolean("RandomTeleport"))
        {
            RandomTeleport rtpCFG = new RandomTeleport();
            rtpCFG.loadRtpCFG(this);
            Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new RandomTeleport());
            rtpCFG.rtpCFG = getConfig();
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF rtp load.");
        }
        if(cfg.getBoolean("JoinMessage"))
        {
            Join join = new Join();
            join.loadJoin(this);
            this.getServer().getPluginManager().registerEvents(new Join(), this);
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
}
