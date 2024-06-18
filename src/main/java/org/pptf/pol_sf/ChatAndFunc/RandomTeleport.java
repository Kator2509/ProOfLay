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
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.pptf.pol_sf.PoL_SF;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class RandomTeleport implements CommandExecutor {
    public static File rtpFile = new File(getPlugin(PoL_SF.class).getDataFolder(), "rtp/rtpCFG.yml"),
    rtpMess = new File(getPlugin(PoL_SF.class).getDataFolder(), "rtp/rtpMessage.yml");
    public static FileConfiguration rtpCFG, rtpMessage;
    private final Permission rtp = new Permission("PoL.rtp"), rtpPlayer = new Permission("PoL.rtp.Player"), rtpNoCooldown = new Permission("PoL.rtp.NoCooldown");
    private final Random random = new Random();
    private final HashMap<String, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission(rtpNoCooldown) && args.length == 0 && rtpCFG.isBoolean("EnableTimeCooldown")) {
            World worldSender = ((Player) sender).getWorld();
            double X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")) + 0.5,
                    Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max")) + 0.5;
            Block block = worldSender.getHighestBlockAt((int) X, (int) Z);
            int Y = block.getY() + 1;
            while (getRtpCFG().getStringList("BlockListIgnored").contains(block.getType().name())) {
                X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")) + 0.5;
                Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max")) + 0.5;
                block = worldSender.getHighestBlockAt((int) X, (int) Z);
                Y = block.getY() + 1;
            }
            ((Player) sender).teleport(new Location(worldSender, X, Y, Z));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getRtpMessage().get("TeleportedMessage").toString()
                            .replace("%X", String.valueOf(X))
                            .replace("%Z", String.valueOf(Z))
                            .replace("%Y", String.valueOf(Y))
                            .replace("%SENDER", sender.getName()))));
            return true;
        }
        else if (sender instanceof Player && args.length == 0 && sender.hasPermission(rtp) && !isCooldown(sender.getName(), rtpCFG.getInt("TimeCooldown"))) {
            World worldSender = ((Player) sender).getWorld();
            if (!getRtpCFG().getStringList("BlockWorld").contains(worldSender.getName())) {
                double X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")) + 0.5,
                        Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max")) + 0.5;
                Block block = worldSender.getHighestBlockAt((int) X, (int) Z);
                int Y = block.getY() + 1;
                while (getRtpCFG().getStringList("BlockListIgnored").contains(block.getType().name())) {
                    X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")) + 0.5;
                    Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max")) + 0.5;
                    block = worldSender.getHighestBlockAt((int) X, (int) Z);
                    Y = block.getY() + 1;
                }
                ((Player) sender).teleport(new Location(worldSender, X, Y, Z));
                if(rtpCFG.isBoolean("EnableTimeCooldown")) {
                    setCooldown(sender.getName());
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(getRtpMessage().get("TeleportedMessage").toString()
                                .replace("%X", String.valueOf(X))
                                .replace("%Z", String.valueOf(Z))
                                .replace("%Y", String.valueOf(Y))
                                .replace("%SENDER", sender.getName()))));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(getRtpMessage().get("TryTeleportedInBlockWorld").toString()
                                .replace("%SENDER", sender.getName()))));
            }
            return true;
        } else if (args.length == 1 && sender.hasPermission(rtpPlayer)) {
            Player player = Bukkit.getPlayer(args[0]);
            try {
                if (Objects.requireNonNull(player).isOnline()) {
                    World worldSender = player.getWorld();
                    if (!getRtpCFG().getStringList("BlockWorld").contains(worldSender.getName())) {
                        int X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max")),
                                Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max"));
                        Block block = worldSender.getHighestBlockAt(X, Z);
                        int Y = block.getY() + 1;
                        while (getRtpCFG().getStringList("BlockListIgnored").contains(block.getType().name())) {
                            X = random.nextInt(getRtpCFG().getInt("X-min"), getRtpCFG().getInt("X-max"));
                            Z = random.nextInt(getRtpCFG().getInt("Z-min"), getRtpCFG().getInt("Z-max"));
                            block = worldSender.getHighestBlockAt((int) X, (int) Z);
                            Y = block.getY() + 1;
                        }
                        player.teleport(new Location(worldSender, X + 0.5, Y, Z + 0.5));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(getRtpMessage().get("TeleportedMessageForPlayer").toString()
                                        .replace("%X", String.valueOf(X))
                                        .replace("%Z", String.valueOf(Z))
                                        .replace("%Y", String.valueOf(Y))
                                        .replace("%PLAYER", player.getName())
                                        .replace("%SENDER", sender.getName()))));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(getRtpMessage().get("TeleportedMessageByPlayer").toString()
                                        .replace("%X", String.valueOf(X))
                                        .replace("%Z", String.valueOf(Z))
                                        .replace("%Y", String.valueOf(Y))
                                        .replace("%PLAYER", player.getName())
                                        .replace("%SENDER", sender.getName()))));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(getRtpMessage().get("TryTeleportedInBlockWorld").toString()
                                        .replace("%PLAYER", player.getName())
                                        .replace("%SENDER", sender.getName()))));
                    }
                    return true;
                }
            } catch (NullPointerException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(getRtpMessage().get("PlayerOffline").toString()
                                .replace("%PLAYER", args[0])
                                .replace("%SENDER", sender.getName()))));
                return true;
            }
        }
        else if(isCooldown(sender.getName(), rtpCFG.getInt("TimeCooldown")) && sender instanceof Player)
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getRtpMessage().get("TimeCooldownMessag").toString()
                            .replace("%TIME", getCooldownValue(sender.getName(), 20))
                            .replace("%SENDER", sender.getName()))));
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(Objects.requireNonNull(getRtpMessage().get("IfSenderConsole")).toString())));
            return true;
        }
        else if(args.length > 1 && sender.hasPermission(rtpPlayer))
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getRtpMessage().get("ToManyArguments").toString()
                            .replace("%SENDER", sender.getName()))));
            return true;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(getRtpMessage().get("DontHavePermission").toString()
                            .replace("%SENDER", sender.getName()))));
            return true;
        }
        return false;
    }

    public void loadRtpCFG(Plugin plugin) {
        if (!rtpFile.exists()) {
            plugin.saveResource("rtp/rtpCFG.yml", false);
            plugin.saveResource("rtp/rtpMessage.yml", false);
        }
        rtpCFG = YamlConfiguration.loadConfiguration(rtpFile);
        rtpMessage = YamlConfiguration.loadConfiguration(rtpMess);
    }

    public FileConfiguration getRtpCFG() {
        return rtpCFG;
    }

    public FileConfiguration getRtpMessage() {
        return rtpMessage;
    }

    public void setCooldown(String player) {
        cooldown.put(player, System.currentTimeMillis());
    }

    public Long getCooldown(String player)
    {
        return (System.currentTimeMillis() - cooldown.get(player)) / 1000;
    }

    public String getCooldownValue(String player, Integer time)
    {
        long timeRemain = time - getCooldown(player);
        String messageTime = String.valueOf(timeRemain);
        return messageTime;
    }

    public boolean isCooldown(String player, Integer time) {
        if (cooldown.containsKey(player))
        {
            return getCooldown(player) <= time;
        }
        return false;
    }

    public static void reloadRTPConfig()
    {
        rtpCFG = YamlConfiguration.loadConfiguration(rtpFile);
        rtpMessage = YamlConfiguration.loadConfiguration(rtpMess);
    }
}