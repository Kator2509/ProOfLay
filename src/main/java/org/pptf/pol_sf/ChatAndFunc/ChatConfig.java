package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class ChatConfig
{
    public static File chatFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "Chat/ChatCFG.yml");
    public static FileConfiguration chatCFG = YamlConfiguration.loadConfiguration(chatFile);

    public FileConfiguration getChatCFG()
    {
        return chatCFG;
    }
    public void loadChatCFG(Plugin plugin)
    {
        if(!chatFile.exists())
        {
            plugin.saveResource("Chat/ChatCFG.yml", false);
        }
    }

    public static void reloadCFGChat()
    {
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
    }
}
