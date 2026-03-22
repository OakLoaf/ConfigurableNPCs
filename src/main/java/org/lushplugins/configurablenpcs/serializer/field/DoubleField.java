package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class DoubleField extends Field<Double> {

    public DoubleField(String name) {
        super(name);
    }

    @Override
    public Double deserialize(ConfigurationSection config, Double def) {
        return config.getDouble(name, def);
    }

    @Override
    public void serialize(ConfigurationSection config, Double value) {
        config.set(name, value);
    }
}
