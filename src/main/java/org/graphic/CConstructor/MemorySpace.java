package org.graphic.CConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MemorySpace implements MemoryInterface {
    protected final Yaml yaml = new Yaml();
    protected Path configPath;
    protected Map<String, Object> data;

    protected MemorySpace(@NotNull Path configPath) {
        this.configPath = configPath;
    }

    @Override
    public void loadData() throws ConfigurationException {
        try (InputStream is = Files.newInputStream(configPath)) {
            data = yaml.load(is);
        } catch (IOException | YAMLException e) {
            throw new ConfigurationException("Failed to load config: " + configPath, e);
        }
    }

    @Override
    public void loadData(@NotNull String path) throws ConfigurationException {
        this.configPath = Path.of(path);
        loadData();
    }

    @Override
    public void reloadData() throws ConfigurationException {
        loadData();
    }

    private Object navigatePath(String path) throws ConfigurationException {
        String[] parts = path.split("\\.");
        Map<String, Object> current = data;

        for (int i = 0; i < parts.length - 1; i++) {
            Object node = current.get(parts[i]);
            if (!(node instanceof Map)) {
                throw new ConfigurationException("Invalid path segment: " + parts[i]);
            }
            current = (Map<String, Object>) node;
        }
        return current.get(parts[parts.length - 1]);
    }

    @Override
    public <T> T get(@NotNull String path, Class<T> type) throws ConfigurationException {
        Object value = navigatePath(path);
        if (value == null) {
            throw new ConfigurationException("Path not found: " + path);
        }
        if (!type.isInstance(value)) {
            throw new ConfigurationException("Type mismatch for path: " + path);
        }
        return type.cast(value);
    }

    @Override
    public <T> List<T> getList(@NotNull String path, Class<T> type) throws ConfigurationException {
        Object value = navigatePath(path);
        if (!(value instanceof List<?> rawList)) {
            throw new ConfigurationException("Path is not a list: " + path);
        }

        List<T> result = new ArrayList<>();
        for (Object item : rawList) {
            if (!type.isInstance(item)) {
                throw new ConfigurationException("List type mismatch at path: " + path);
            }
            result.add(type.cast(item));
        }
        return result;
    }

    @Override
    public List<String> getKeys(@Nullable String path) throws ConfigurationException {
        if (path == null || path.isEmpty()) {
            return new ArrayList<>(data.keySet());
        }

        Object node = navigatePath(path);
        if (!(node instanceof Map)) {
            throw new ConfigurationException("Path is not a section: " + path);
        }
        return new ArrayList<>(((Map<?, ?>) node).keySet().stream()
                .map(Object::toString)
                .toList());
    }

    @Override
    public void set(@NotNull String path, @Nullable Object value) throws ConfigurationException {
        // Реализация записи данных
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
