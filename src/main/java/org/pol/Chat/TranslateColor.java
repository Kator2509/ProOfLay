package org.pol.Chat;

import org.bukkit.ChatColor;

public interface TranslateColor
{
    default String translateColor(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
