package org.graphic.CConstructor.modul;

import org.bukkit.plugin.Plugin;
import org.graphic.CConstructor.Configuration;
import org.graphic.CConstructor.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationLoader {
    private static final Map<String, Configuration> CONFIGS = new ConcurrentHashMap<>();

    public static synchronized void register(@NotNull String name,
                                             @NotNull Plugin plugin,
                                             @NotNull String resourcePath,
                                             @NotNull String outputPath) throws ConfigurationException {
        if (CONFIGS.containsKey(name)) {
            throw new ConfigurationException("Configuration '" + name + "' already registered");
        }

        Path path = Path.of(outputPath);
        Configuration config = new Configuration(plugin, resourcePath, path);
        CONFIGS.put(name, config);
    }

    public static void registerDefaults(@NotNull Plugin plugin) {
        try {
            register("main", plugin, "config.yml", "plugins/ProFlay/config.yml");
            register("test", plugin, "test.yml", "plugins/ProFlay/test.yml");
        } catch (ConfigurationException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

    public static Configuration get(@NotNull String name) {
        return CONFIGS.get(name);
    }
}
