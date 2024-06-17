package org.pptf.pol_sf.JoinAndLeaveEvent.RegisterEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class RegisterMethod
{
    public static File registerFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "Register/RegisterMethodConfig.yml");
    public static FileConfiguration registerCFG;
    ZoneId timeZone;
    LocalDate localDate;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();

    public void registerPlayerInSystem(Player player)
    {
        if(!player.hasPlayedBefore())
        {
            timeZone = ZoneId.of(registerCFG.get("TimeZone").toString());
            localDate = LocalDate.now(timeZone);
            objectNode.put("UUID", player.getUniqueId().toString());
            objectNode.put("DateRegister", localDate.toString());
            try {
                objectMapper.writeValue(new File(getPlugin(PoL_SF.class).getDataFolder() + "/DataPlayers/" + player.getName()), objectNode);
            } catch (IOException e)
            {
                Bukkit.getConsoleSender().sendMessage("Can't created the json file of Player - " + player.getName());
                e.printStackTrace();
            }
        }
    }

    public void loadRegister(Plugin plugin)
    {
        if(!registerFile.exists())
        {
            plugin.saveResource("Register/RegisterMethodConfig.yml", false);
        }
        registerCFG = YamlConfiguration.loadConfiguration(registerFile);
    }

    public static void reloadRegister()
    {
        registerCFG = YamlConfiguration.loadConfiguration(registerFile);
    }
}
