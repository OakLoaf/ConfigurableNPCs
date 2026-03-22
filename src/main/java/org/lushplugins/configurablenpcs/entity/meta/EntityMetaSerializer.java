package org.lushplugins.configurablenpcs.entity.meta;

import me.tofaa.entitylib.meta.EntityMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.serializer.field.Field;

import java.util.List;

public interface EntityMetaSerializer<T extends EntityMeta> {

    List<Class<? extends EntityMeta>> getApplicableMetaTypes();

    default boolean isApplicableTo(Class<?> metaType) {
        return getApplicableMetaTypes().stream()
            .anyMatch(metaType::isAssignableFrom);
    }

    void deserialize(ConfigurationSection config, T meta);

    default void castAndDeserialize(ConfigurationSection config, EntityMeta meta) {
        //noinspection unchecked
        deserialize(config, (T) meta);
    }

    void serialize(ConfigurationSection config, T meta);

    default void castAndSerialize(ConfigurationSection config, EntityMeta meta) {
        //noinspection unchecked
        deserialize(config, (T) meta);
    }

    List<Field<?>> getFields();

    default List<String> getFieldNames() {
        return getFields().stream()
            .map(Field::name)
            .toList();
    }
}
