package org.graphic.CConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MemoryInterface
{
    void setData();
    void setData(@NotNull String pathFile);

    void resetData();

    String getString(@NotNull String path);

    List<String> getStringArray(@NotNull String path);

    Character getCharacter(@NotNull String path);

    List<Character> getCharacterArray(@NotNull String path);

    Boolean getBoolean(@NotNull String path);

    List<Boolean> getBooleanArray(@NotNull String path);

    Integer getInteger(@NotNull String path);

    List<Integer> getIntegerArray(@NotNull String path);

    Double getDouble(@NotNull String path);

    List<Double> getDoubleArray(@NotNull String path);

    Object getObject(@NotNull String path);

    List<Object> getObjectsArray(@NotNull String path);

    String getKeySet();

    List<String> getKeySet(@Nullable String path);

    void setKey(@NotNull String path, @Nullable String argument);
}
