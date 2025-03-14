package org.graphic.test.Commander.modul;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.graphic.test.Commander.ProFlayCommand;
import org.graphic.test.Commander.TestCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProFlayCommandListener {
    protected static final Map<String, ProFlayCommand> commandMap = new ConcurrentHashMap<>();
    private static boolean moduleIsEnable = false;

    // Конструктор с плагином
    public ProFlayCommandListener(@NotNull Plugin plugin) {
        this.registerDefaultCommands();
        moduleIsEnable = true;
    }

    // Дефолтный конструктор
    public ProFlayCommandListener() {
    }

    public void registerDefaultCommands() {
        if (!this.register(new TestCommand(), false)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Default command not loaded.");
        }
    }

    public boolean register(@NotNull ProFlayCommand command, boolean override) {
        if (moduleIsEnable) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Late command registration: " + command.getLabel());
            return false;
        }

        if (commandMap.containsKey(command.getLabel())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[ProFlay] Command already registered: " + command.getName());
            return false;
        }

        commandMap.put(command.getLabel(), command);
        return true;
    }

    public boolean isRegistered(@NotNull ProFlayCommand command) {
        return commandMap.containsValue(command);
    }

    public ProFlayCommand getProFlayCommand(@NotNull String name) {
        return commandMap.get(name);
    }
}