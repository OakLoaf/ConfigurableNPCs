package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class FloatField extends Field<Float> {

    public FloatField(String name) {
        super(name);
    }

    @Override
    public Float deserialize(ConfigurationSection config, Float def) {
        return (float) config.getDouble(name, def);
    }

    @Override
    public void serialize(ConfigurationSection config, Float value) {
        config.set(name, value);
    }
}
