package org.lushplugins.configurablenpcs.entity.component;

import me.tofaa.entitylib.wrapper.WrapperEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.serializer.field.Field;

import java.util.List;

public interface EntityComponent {
    Class<? extends WrapperEntity> getApplicableWrapper();

    default boolean isApplicableTo(Class<?> metaType) {
        return metaType.isAssignableFrom(getApplicableWrapper());
    }

    void applyTo(WrapperEntity entity);

    void serialize(ConfigurationSection config);

    List<Field<?>> getFields();

    default List<String> getFieldNames() {
        return getFields().stream()
            .map(Field::name)
            .toList();
    }
}
