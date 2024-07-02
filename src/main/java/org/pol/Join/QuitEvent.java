package org.pol.Join;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pol.Chat.TranslateColor;
import org.pol.LoaderAPI;

import java.util.Objects;

public class QuitEvent extends JoinQuitConfig implements Listener, TranslateColor, LoaderAPI
{
    String prefix, suffix;
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        try{
            LuckPerms ap = loadLP();
            ConfigurationSection quitSection = getQuitConfig().getConfigurationSection("Quit");
            User user = Objects.requireNonNull(ap).getUserManager().getUser(event.getPlayer().getUniqueId());
            prefix = Objects.requireNonNull(user).getCachedData().getMetaData().getPrefix();
            suffix = Objects.requireNonNull(user).getCachedData().getMetaData().getSuffix();
            if(prefix == null)
            {
                prefix = "";
            }
            if(suffix == null)
            {
                suffix = "";
            }
            for(String key : quitSection.getKeys(false))
            {
                if(event.getPlayer().hasPermission("Quit." + key))
                {
                    event.setQuitMessage(translateColor(Objects.requireNonNull(Objects.requireNonNull(getQuitConfig().getString("Quit." + key))
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%SUFFIX", suffix)
                            .replace("%PREFIX", prefix))));
                }
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            event.getPlayer().sendMessage(translateColor("&b[PoL] - Config error!"));
        }
    }
}
