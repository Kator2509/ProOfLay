package org.graphic.CConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public interface MemoryInterface {
    void loadData() throws ConfigurationException;
    void loadData(@NotNull String path) throws ConfigurationException;
    void reloadData() throws ConfigurationException;

    <T> T get(@NotNull String path, Class<T> type) throws ConfigurationException;
    <T> List<T> getList(@NotNull String path, Class<T> type) throws ConfigurationException;

    default String getString(@NotNull String path) throws ConfigurationException {
        return get(path, String.class);
    }

    default Boolean getBoolean(@NotNull String path) throws ConfigurationException {
        return get(path, Boolean.class);
    }

    default Integer getInteger(@NotNull String path) throws ConfigurationException {
        return get(path, Integer.class);
    }

    default Double getDouble(@NotNull String path) throws ConfigurationException {
        return get(path, Double.class);
    }

    default Character getCharacter(@NotNull String path) throws ConfigurationException {
        String value = getString(path);
        return (value != null && !value.isEmpty()) ? value.charAt(0) : null;
    }

    List<String> getKeys(@Nullable String path) throws ConfigurationException;
    void set(@NotNull String path, @Nullable Object value) throws ConfigurationException;
}
