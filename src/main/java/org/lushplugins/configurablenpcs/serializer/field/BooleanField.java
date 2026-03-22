package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class BooleanField extends Field<Boolean> {

    public BooleanField(String name) {
        super(name);
    }

    @Override
    public Boolean deserialize(ConfigurationSection config, Boolean def) {
        return config.getBoolean(name, def);
    }

    @Override
    public void serialize(ConfigurationSection config, Boolean value) {
        config.set(name, value);
    }
}
