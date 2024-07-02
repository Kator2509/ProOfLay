package org.pol.Join;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.pol.Chat.TranslateColor;
import org.pol.LoaderAPI;

import java.util.Objects;

public class JoinEvent extends JoinQuitConfig implements Listener, TranslateColor, LoaderAPI
{
    String prefix, suffix;
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        try {
            LuckPerms ap = loadLP();
            ConfigurationSection joinSection = getJoinConfig().getConfigurationSection("Join");
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
            for (String key : joinSection.getKeys(false)) {
                if (event.getPlayer().hasPermission("Join." + key)) {
                    event.setJoinMessage(translateColor(Objects.requireNonNull(getJoinConfig().getString("Join." + key))
                            .replace("%PLAYER", event.getPlayer().getName())
                            .replace("%PREFIX", prefix)
                            .replace("%SUFFIX", suffix)));
                }
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            event.getPlayer().sendMessage(translateColor("&b[PoL] - Config error!"));
        }
    }
}
