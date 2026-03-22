package org.lushplugins.configurablenpcs.entity;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import me.tofaa.entitylib.EntityLib;
import me.tofaa.entitylib.Platform;
import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.meta.types.LivingEntityMeta;
import me.tofaa.entitylib.meta.types.PlayerMeta;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import me.tofaa.entitylib.wrapper.WrapperLivingEntity;
import me.tofaa.entitylib.wrapper.WrapperPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.ConfigurableNPCs;
import org.lushplugins.configurablenpcs.entity.component.EntityComponent;

import java.util.*;

public record EntityConfiguration(EntityType type, EntityMeta meta, List<EntityComponent> components) {

    public WrapperEntity createEntity(UUID uuid, int entityId) {
        EntityMeta meta = EntityMeta.createMeta(entityId, type);

        WrapperEntity entity;
        if (meta instanceof PlayerMeta) {
            // TODO: Make profile configurable
            entity = new WrapperPlayer(new UserProfile(uuid, ""), entityId);
            meta = entity.getEntityMeta();
            ((WrapperPlayer) entity).setInTablist(false);
        } else if (meta instanceof LivingEntityMeta) {
            entity = new WrapperLivingEntity(entityId, uuid, type, meta);
        } else {
            entity = new WrapperEntity(entityId, uuid, type, meta);
        }

        this.meta.getMetadata().copyTo(meta.getMetadata());
        components.forEach(component -> component.applyTo(entity));

        return entity;
    }

    public WrapperEntity createEntity() {
        Platform<?> platform = EntityLib.getPlatform();
        UUID uuid = platform.getEntityUuidProvider().provide(type);
        int entityId = platform.getEntityIdProvider().provide(uuid, type);

        return createEntity(uuid, entityId);
    }

    public void serialize(ConfigurationSection config) {
        config.set("type", type.getName().toString());

        ConfigurationSection metaSection = config.createSection("meta");
        ConfigurableNPCs.metaSerializers().serialize(meta, metaSection);

        ConfigurationSection componentsSection = config.createSection("components");
        components.forEach(component -> component.serialize(componentsSection));
    }

    public static EntityConfiguration deserialize(ConfigurationSection config) {
        EntityType entityType = EntityTypes.getByName(config.getString("type"));

        ConfigurationSection metaSection = config.getConfigurationSection("meta");
        EntityMeta meta = EntityMeta.createMeta(-1, entityType);
        ConfigurableNPCs.metaSerializers().deserialize(meta, metaSection);

        ConfigurationSection componentsSection = config.getConfigurationSection("components");
        List<EntityComponent> components;
        if (componentsSection != null) {
            components = new ArrayList<>(ConfigurableNPCs.componentSerializers().constructFor(componentsSection));
        } else {
            components = new ArrayList<>();
        }

        return new EntityConfiguration(entityType, meta, components);
    }
}
