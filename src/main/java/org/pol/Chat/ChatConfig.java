package org.pol.Chat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.pol.PoL;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class ChatConfig
{
    private final static File chatFile = new File(getPlugin(PoL.class).getDataFolder(), "Chat/ChatConfig.yml");
    public static FileConfiguration chatCFG = YamlConfiguration.loadConfiguration(chatFile);

    public void loadChatCFG(Plugin plugin)
    {
        if(!chatFile.exists())
        {
            plugin.saveResource("Chat/ChatConfig.yml", false);
        }
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
    }

    public static FileConfiguration getConfigChat()
    {
        return chatCFG;
    }

    public static File getConfigChatFile()
    {
        return chatFile;
    }

    public static void reloadConfigChat()
    {
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
    }
}
