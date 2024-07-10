package org.pol.Chat;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.pol.LoaderAPI;

import java.util.Objects;

public interface TranslateColor extends LoaderAPI
{
    default String translateColor(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    default String replaceMessage(Player player, String message)
    {
        LuckPerms ap = loadLP();
        String prefix = null, suffix = null;
        if(ap != null) {
            User user = Objects.requireNonNull(ap).getUserManager().getUser(player.getUniqueId());
            prefix = Objects.requireNonNull(user).getCachedData().getMetaData().getPrefix();
            suffix = Objects.requireNonNull(user).getCachedData().getMetaData().getSuffix();
        }
        if(prefix == null)
        {
            prefix = "";
        }
        if(suffix == null)
        {
            suffix = "";
        }
        return translateColor(message
                .replace("%PLAYER", player.getName())
                .replace("%PREFIX", prefix)
                .replace("%SUFFIX", suffix));
    }
}
