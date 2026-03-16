package org.lushplugins.configurablenpcs;

import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigurableNpCs extends JavaPlugin {
    private static ConfigurableNpCs plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static ConfigurableNpCs getInstance() {
        return plugin;
    }
}
