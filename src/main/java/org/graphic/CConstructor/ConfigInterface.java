package org.graphic.CConstructor;

public interface ConfigInterface {
    void initialize(boolean forceReset) throws ConfigurationException;

    boolean isInitialized();
}
