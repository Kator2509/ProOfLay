package org.pol;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.WorldGuard;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

public interface LoaderAPI
{
    @Nullable
    default LuckPerms loadLP()
    {
        LuckPerms lp = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
        return lp;
    }

    @Nullable
    default WorldGuard loadWG()
    {
        return Bukkit.getServicesManager().getRegistration(WorldGuard.class).getProvider();
    }

    @Nullable
    default WorldEdit loadWE()
    {
        return Bukkit.getServicesManager().getRegistration(WorldEdit.class).getProvider();
    }
}
