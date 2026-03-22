package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public abstract class Field<T> {
    protected final String name;

    public Field(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public abstract T deserialize(ConfigurationSection config, T def);

    public abstract void serialize(ConfigurationSection config, T value);
}
