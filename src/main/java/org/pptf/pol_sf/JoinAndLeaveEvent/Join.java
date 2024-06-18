package org.pptf.pol_sf.JoinAndLeaveEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.pptf.pol_sf.JoinAndLeaveEvent.RegisterEvent.RegisterMethod;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Join extends RegisterMethod implements Listener, CommandExecutor
{
    public static File joinFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "JoinMessage.yml");
    public static FileConfiguration joinMSG;
    Permission motdPerm = new Permission("PoL.motd");

    public FileConfiguration getJoinMSG()
    {
        return joinMSG;
    }

    public void loadJoin(Plugin plugin)
    {
        if(!joinFile.exists())
        {
            plugin.saveResource("JoinMessage.yml", false);
        }
        joinMSG = YamlConfiguration.loadConfiguration(joinFile);
    }

    public static void reloadCFGJoin()
    {
        joinMSG = YamlConfiguration.loadConfiguration(joinFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(PoL_SF.getConfiguration().getBoolean("EnableRegisterMethod"))
        {
            registerPlayerInSystem(player);
            Bukkit.getConsoleSender().sendMessage("&bRegister a " + player.getName());
        }
        Bukkit.getServer().dispatchCommand(event.getPlayer().getServer().getConsoleSender(), "motd");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if(sender.hasPermission(motdPerm))
        {
            sender.sendMessage(Objects.requireNonNull(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(PoL_SF.getConfiguration().getString("Motd"))
                            .replace("%SENDER", sender.getName()))));
            return true;
        }
        return false;
    }
}
