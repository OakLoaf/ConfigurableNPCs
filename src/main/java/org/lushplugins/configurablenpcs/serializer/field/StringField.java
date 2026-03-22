package org.lushplugins.configurablenpcs.serializer.field;

import org.bukkit.configuration.ConfigurationSection;

public class StringField extends Field<String> {

    public StringField(String name) {
        super(name);
    }

    @Override
    public String deserialize(ConfigurationSection config, String def) {
        return config.getString(name, def);
    }

    @Override
    public void serialize(ConfigurationSection config, String value) {
        config.set(name, value);
    }
}
