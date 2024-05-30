package org.pptf.pol_sf.ChatAndFunc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
    public File rtpFile, rtpMess;
    private Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player)
        {
            if(sender.hasPermission("PoL.rtp"))
            {
                int X = random.nextInt(rtpCFG.getInt("X-min"), rtpCFG.getInt("X-max")),
                        Z = random.nextInt(rtpCFG.getInt("Z-min"), rtpCFG.getInt("Z-max"));
                World worldSender = ((Player) sender).getWorld();
                if(!rtpCFG.getStringList("BlockWorld").contains(worldSender))
                {
                    Block block = worldSender.getHighestBlockAt(X, Z);
                    int Y = block.getY();
                    if(!rtpCFG.getStringList("BlockListIgnored").contains(block))
                    {
                        ((Player) sender).teleport(new Location(worldSender, X, Y, Z));
                        sender.sendMessage((String) Objects.requireNonNull(rtpMessage.get("TeleportedMessage")) + "X - " + X + ", Z - " + Z);
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(rtpMessage.get("TryTeleportedInBlockWorld"))));
                    return true;
                }
            }
        }
        else if(args[0] != null && sender.hasPermission("PoL.rtpPlayer"))
        {
            Player player = Bukkit.getPlayer(args[0]);
            if(player.isOnline())
            {
                int X = random.nextInt(rtpCFG.getInt("X-min"), rtpCFG.getInt("X-max")),
                        Z = random.nextInt(rtpCFG.getInt("Z-min"), rtpCFG.getInt("Z-max"));
                World worldSender = ((Player) sender).getWorld();
                if(!rtpCFG.getStringList("BlockWorld").contains(worldSender))
                {
                    Block block = worldSender.getHighestBlockAt(X, Z);
                    int Y = block.getY();
                    if(!rtpCFG.getStringList("BlockListIgnored").contains(block))
                    {
                        player.teleport(new Location(worldSender, X, Y, Z));
                        sender.sendMessage((String) Objects.requireNonNull(rtpMessage.get("TeleportedMessage")) + "X - " + X + ", Z - " + Z);
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(rtpMessage.get("TryTeleportedInBlockWorld"))));
                    return true;
                }
            }
            else
            {
                sender.sendMessage((String) Objects.requireNonNull(rtpMessage.get("PlayerOffline")));
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
            plugin.saveResource("rtp/rtpMessage.yml", false);
        }
    }
}
