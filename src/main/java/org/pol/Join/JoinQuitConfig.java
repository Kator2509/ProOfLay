package org.pol.Join;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.pol.PoL;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class JoinQuitConfig
{
    private final static File configJoin = new File(getPlugin(PoL.class).getDataFolder(), "JoinQuit/Join.yml"),
    configQuit = new File(getPlugin(PoL.class).getDataFolder(), "JoinQuit/Quit.yml");

    public static FileConfiguration joinConfig = YamlConfiguration.loadConfiguration(configJoin),
    quitConfig = YamlConfiguration.loadConfiguration(configQuit);

    public void loadJoinQuit(Plugin plugin)
    {
        if(!configJoin.exists())
        {
            plugin.saveResource("JoinQuit/Join.yml", false);
        }
        if(!configQuit.exists())
        {
            plugin.saveResource("JoinQuit/Quit.yml", false);
        }
        joinConfig = YamlConfiguration.loadConfiguration(configJoin);
        quitConfig = YamlConfiguration.loadConfiguration(configQuit);
    }

    public static void reloadJoinQuit()
    {
        joinConfig = YamlConfiguration.loadConfiguration(configJoin);
        quitConfig = YamlConfiguration.loadConfiguration(configQuit);
    }

    public static FileConfiguration getJoinConfig()
    {
        return joinConfig;
    }

    public static FileConfiguration getQuitConfig()
    {
        return quitConfig;
    }
}
