package org.graphic.CConstructor;

import org.jetbrains.annotations.NotNull;

import org.bukkit.plugin.Plugin;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Configuration extends MemorySpace implements ConfigInterface {
    private final Plugin plugin;
    private final String resourcePath;
    private boolean initialized = false;

    public Configuration(
            @NotNull Plugin plugin, // Теперь аннотация распознается
            @NotNull String resourcePath,
            @NotNull Path outputPath
    ) throws ConfigurationException {
        super(outputPath);
        this.plugin = plugin;
        this.resourcePath = resourcePath;
        initialize(false);
    }

    @Override
    public void initialize(boolean forceReset) throws ConfigurationException {
        try {
            if (forceReset || !Files.exists(configPath)) {
                Files.createDirectories(configPath.getParent());
                try (InputStream is = plugin.getResource(resourcePath)) {
                    if (is == null) {
                        throw new ConfigurationException("Resource not found: " + resourcePath);
                    }
                    Files.copy(is, configPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            loadData();
            initialized = true;
        } catch (IOException e) {
            throw new ConfigurationException("Initialization failed", e);
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
