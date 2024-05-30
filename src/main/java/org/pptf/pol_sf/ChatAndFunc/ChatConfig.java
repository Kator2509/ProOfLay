package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ChatConfig
{
    private static FileConfiguration chatCFG;
    private static File chatFile, chatPath;
    public void loadChatCFG(Plugin plugin)
    {
        chatFile = new File(plugin.getDataFolder(), "Chat/ChatCFG.yml");
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
        if(!chatFile.exists())
        {
            plugin.saveResource("Chat/ChatCFG.yml", false);
        }
    }
}
