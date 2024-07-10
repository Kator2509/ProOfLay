package org.pol;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.WorldGuard;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface LoaderAPI
{
    @Nullable
    default LuckPerms loadLP()
    {
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(LuckPerms.class)).getProvider();
    }

    @Nullable
    default WorldGuard loadWG()
    {
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(WorldGuard.class)).getProvider();
    }

    @Nullable
    default WorldEdit loadWE()
    {
        return Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(WorldEdit.class)).getProvider();
    }
}
