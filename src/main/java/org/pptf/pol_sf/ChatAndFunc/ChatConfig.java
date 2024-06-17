package org.pptf.pol_sf.ChatAndFunc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class ChatConfig
{
    public static File chatFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "Chat/ChatCFG.yml");
    public static FileConfiguration chatCFG;

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
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
    }

    public static void reloadCFGChat()
    {
        chatCFG = YamlConfiguration.loadConfiguration(chatFile);
    }
}
