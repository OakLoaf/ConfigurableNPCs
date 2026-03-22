package org.lushplugins.configurablenpcs.entity.component;

import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.component.type.AttributesComponent;
import org.lushplugins.configurablenpcs.entity.component.type.EquipmentComponent;
import org.lushplugins.configurablenpcs.entity.component.type.TexturesComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EntityComponentRegistry {
    private final Map<String, Function<ConfigurationSection, EntityComponent>> components = new HashMap<>();

    public EntityComponentRegistry() {
        register("attributes", AttributesComponent::new);
        register("equipment", EquipmentComponent::new);
        register("textures", TexturesComponent::new);
    }

    public List<EntityComponent> constructFor(ConfigurationSection config) {
        return config.getKeys(false).stream()
            .map(key -> components.get(key).apply(config))
            .toList();
    }

    private void register(String key, Function<ConfigurationSection, EntityComponent> constructor) {
        components.put(key, constructor);
    }
}
