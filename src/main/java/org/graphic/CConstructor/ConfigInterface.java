package org.graphic.CConstructor;

import org.jetbrains.annotations.NotNull;

public interface ConfigInterface
{
    void createConfig(@NotNull boolean reset);

    boolean isExistsConfig();
}
