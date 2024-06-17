package org.pptf.pol_sf.JoinAndLeaveEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Join implements Listener
{
    public static File joinFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "JoinMessage.yml");
    public static FileConfiguration joinMSG;

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
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
    }
}
