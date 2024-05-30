package org.pptf.pol_sf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.pptf.pol_sf.ChatAndFunc.ChatConfig;
import org.pptf.pol_sf.ChatAndFunc.ChatMessanger;
import org.pptf.pol_sf.ChatAndFunc.RandomTeleport;

import java.util.Objects;

public final class PoL_SF extends JavaPlugin implements Listener {

    private FileConfiguration cfg = this.getConfig();

    @Override
    public void onEnable() {
        cfg.addDefault("ChatModule", true);
        cfg.addDefault("RandomTeleport", true);
        cfg.options().copyDefaults(true);
        saveConfig();
        getConfig().options().copyDefaults(true);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "*---------------------------");
        if(cfg.getBoolean("ChatModule")) {
            ChatConfig chatCFG = new ChatConfig();
            chatCFG.loadChatCFG(this);
            this.getServer().getPluginManager().registerEvents(new ChatMessanger(), this);
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF Chat load.");
        }
        if(cfg.getBoolean("RandomTeleport"))
        {
            RandomTeleport rtpCFG = new RandomTeleport();
            rtpCFG.loadRtpCFG(this);
            Bukkit.getConsoleSender().sendMessage("§b| PoL-SF rtp load.");
        }
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "| PoL-SF Start.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "*---------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "| PoL-SF disabled.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "o---------------------------");
    }
}
