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
import org.pptf.pol_sf.PoL_SF;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class RandomTeleport implements CommandExecutor {
    public File rtpFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "rtp/rtpCFG.yml"), rtpMess = new File(getPlugin(PoL_SF.class).getDataFolder(), "rtp/rtpMessage.yml");
    public FileConfiguration rtpCFG = YamlConfiguration.loadConfiguration(rtpFile), rtpMessage = YamlConfiguration.loadConfiguration(rtpMess);

    private Random random = new Random();

    public void loadRtpCFG(Plugin plugin)
    {
        if(!rtpFile.exists())
        {
            plugin.saveResource("rtp/rtpCFG.yml", false);
            plugin.saveResource("rtp/rtpMessage.yml", false);
        }
    }

    public FileConfiguration getRtpCFG()
    {
        return rtpCFG;
    }

    public FileConfiguration getRtpMessage()
    {
        return rtpMessage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player && args.length == 0) {
                if (sender.hasPermission("PoL.rtp")) {
                    int X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")),
                            Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max"));
                    World worldSender = ((Player) sender).getWorld();
                    if (!getRtpCFG().getStringList("BlockWorld").contains(worldSender.getName())) {
                        Block block = worldSender.getHighestBlockAt(X, Z);
                        int Y = block.getY() + 1;
                        if (!getRtpCFG().getStringList("BlockListIgnored").contains(block)) {
                            ((Player) sender).teleport(new Location(worldSender, X, Y, Z));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getRtpMessage().get("TeleportedMessage")) + "X - " + X + ", Z - " + Z));
                            System.out.println(worldSender.getName());
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(getRtpMessage().get("TryTeleportedInBlockWorld"))));
                        return true;
                    }
                }
            } else if (args.length == 1 && sender.hasPermission("PoL.rtp.Player")) {
                Player player = Bukkit.getPlayer(args[0]);
                try {
                    if (Objects.requireNonNull(player).isOnline()) {
                        int X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")),
                                Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max"));
                        World worldSender = player.getWorld();
                        if (!getRtpCFG().getStringList("BlockWorld").contains(worldSender.getName())) {
                            Block block = worldSender.getHighestBlockAt(X, Z);
                            int Y = block.getY() + 1;
                            if (!getRtpCFG().getStringList("BlockListIgnored").contains(block)) {
                                player.teleport(new Location(worldSender, X, Y, Z));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getRtpMessage().get("TeleportedMessage")) + "X - " + X + ", Z - " + Z));
                                return true;
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(getRtpMessage().get("TryTeleportedInBlockWorld"))));
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(getRtpMessage().get("PlayerOffline"))));
                    return true;
                }
            }
            else if(!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(getRtpMessage().get("IfSenderConsole"))));
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) Objects.requireNonNull(getRtpMessage().get("DontHavePermission"))));
                return true;
            }
        return false;
    }
}