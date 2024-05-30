package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class RandomTeleport implements CommandExecutor {

    public FileConfiguration rtpCFG, rtpMessage;
    public File rtpFile, rtpPath, rtpMess;
    private Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("PoL.rtp"))
            {
                int X = random.nextInt(rtpCFG.getInt("X-min"), rtpCFG.getInt("X-max")),
                        Z = random.nextInt(rtpCFG.getInt("Z-min"), rtpCFG.getInt("Z-max"));
                World worldSender = ((Player) sender).getWorld();
                if(rtpCFG.getStringList("BlockWorld").contains(worldSender))
                {

                }
                else
                {
                    sender.sendMessage("");
                    return true;
                }
                Block blockSender = worldSender.getHighestBlockAt(X, Z);
                return true;
            }
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(rtpMessage.get("IfSenderConsole"))));
            return true;
        }
        return false;
    }

    public void loadRtpCFG(Plugin plugin)
    {
        rtpFile = new File(plugin.getDataFolder(), "rtpCFG.yml");
        rtpMess = new File(plugin.getDataFolder(), "rtpMessage.yml");
        rtpCFG = YamlConfiguration.loadConfiguration(rtpFile);
        rtpMessage = YamlConfiguration.loadConfiguration(rtpMess);
        if(!rtpFile.exists())
        {
            plugin.saveResource("rtp/rtpCFG.yml", false);
            plugin.saveResource("rtp/erpMessage.yml", false);
        }
    }
}
