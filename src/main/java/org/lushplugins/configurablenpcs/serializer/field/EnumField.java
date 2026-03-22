package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class EnumField<T extends Enum<T>> extends Field<T> {
    private final Class<T> enumType;

    public EnumField(String name, Class<T> enumType) {
        super(name);
        this.enumType = enumType;
    }

    @Override
    public T deserialize(ConfigurationSection config, T def) {
        //noinspection DataFlowIssue
        return config.isString(name) ? Enum.valueOf(enumType, config.getString(name).toUpperCase()) : def;
    }

    @Override
    public void serialize(ConfigurationSection config, T value) {
        config.set(name, value.name().toLowerCase());
    }
}
