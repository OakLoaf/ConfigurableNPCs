package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class IntegerField extends Field<Integer> {

    public IntegerField(String name) {
        super(name);
    }

    @Override
    public Integer deserialize(ConfigurationSection config, Integer def) {
        return config.getInt(name, def);
    }

    @Override
    public void serialize(ConfigurationSection config, Integer value) {
        config.set(name, value);
    }
}
