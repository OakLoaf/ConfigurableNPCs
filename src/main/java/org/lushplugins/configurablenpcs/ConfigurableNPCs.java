package org.lushplugins.configurablenpcs;


import com.github.retrooper.packetevents.PacketEvents;
import me.tofaa.entitylib.APIConfig;
import me.tofaa.entitylib.EntityLib;
import me.tofaa.entitylib.spigot.SpigotEntityLibPlatform;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.lushplugins.configurablenpcs.entity.EntityConfiguration;
import org.lushplugins.configurablenpcs.entity.component.EntityComponentRegistry;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializers;

import java.util.UUID;

public final class ConfigurableNPCs {
    private static final EntityMetaSerializers metaSerializers = new EntityMetaSerializers();
    private static final EntityComponentRegistry componentSerializers = new EntityComponentRegistry();

    public static void init(JavaPlugin plugin) {
        EntityLib.init(new SpigotEntityLibPlatform(plugin), new APIConfig(PacketEvents.getAPI()));
    }

    public static EntityMetaSerializers metaSerializers() {
        return metaSerializers;
    }

    public static EntityComponentRegistry componentSerializers() {
        return componentSerializers;
    }

    public static WrapperEntity createEntity(ConfigurationSection config, UUID uuid, int entityId) {
        return EntityConfiguration.deserialize(config).createEntity(uuid, entityId);
    }

    public static WrapperEntity createEntity(ConfigurationSection config) {
        return EntityConfiguration.deserialize(config).createEntity();
    }
}
