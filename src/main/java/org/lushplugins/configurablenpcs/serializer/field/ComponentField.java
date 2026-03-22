package org.lushplugins.configurablenpcs.serializer.field;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.util.Components;

public class ComponentField extends Field<Component> {

    public ComponentField(String name) {
        super(name);
    }

    @Override
    public Component deserialize(ConfigurationSection config, Component def) {
        return config.isString(name) ? Components.fromString(config.getString(name)) : def;
    }

    @Override
    public void serialize(ConfigurationSection config, Component value) {
        config.set(name, Components.toString(value));
    }
}
