package org.graphic.checker;

import org.jetbrains.annotations.NotNull;

public interface CheckerInterface
{
    static boolean checkAPI(@NotNull String name) {
        return false;
    }

    static boolean checkPlugin(@NotNull String name)
    {
        return false;
    }
}
