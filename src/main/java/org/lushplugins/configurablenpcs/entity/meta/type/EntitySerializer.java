package org.lushplugins.configurablenpcs.entity.meta.type;

import com.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import me.tofaa.entitylib.meta.EntityMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.serializer.field.*;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;

import java.util.List;

public class EntitySerializer implements EntityMetaSerializer<EntityMeta> {
    private static final BooleanField ON_FIRE = new BooleanField("on-fire");
    private static final BooleanField SNEAKING = new BooleanField("sneaking");
    private static final BooleanField SPRINTING = new BooleanField("sprinting");
    private static final BooleanField INVISIBLE = new BooleanField("invisible");
    private static final BooleanField GLOWING = new BooleanField("glowing");
    private static final BooleanField SWIMMING = new BooleanField("swimming");
    private static final BooleanField FLYING_WITH_ELYTRA = new BooleanField("flying-with-elytra");
    private static final ComponentField CUSTOM_NAME = new ComponentField("custom-name");
    private static final BooleanField CUSTOM_NAME_VISIBLE = new BooleanField("custom-name-visible");
    private static final BooleanField SILENT = new BooleanField("silent");
    private static final EnumField<EntityPose> POSE = new EnumField<>("pose", EntityPose.class);

    @Override
    public List<Class<? extends EntityMeta>> getApplicableMetaTypes() {
        return List.of(EntityMeta.class);
    }

    @Override
    public void deserialize(ConfigurationSection config, EntityMeta meta) {
        meta.setOnFire(ON_FIRE.deserialize(config, false));
        meta.setSneaking(SNEAKING.deserialize(config, false));
        meta.setSprinting(SPRINTING.deserialize(config, false));
        meta.setInvisible(INVISIBLE.deserialize(config, false));
        meta.setGlowing(GLOWING.deserialize(config, false));
        meta.setSwimming(SWIMMING.deserialize(config, false));
        meta.setFlyingWithElytra(FLYING_WITH_ELYTRA.deserialize(config, false));
        meta.setCustomName(CUSTOM_NAME.deserialize(config, null));
        meta.setCustomNameVisible(CUSTOM_NAME_VISIBLE.deserialize(config, false));
        meta.setSilent(SILENT.deserialize(config, false));
        meta.setPose(POSE.deserialize(config, EntityPose.STANDING));
    }

    @Override
    public void serialize(ConfigurationSection config, EntityMeta meta) {
        ON_FIRE.serialize(config, meta.isOnFire());
        SNEAKING.serialize(config, meta.isSneaking());
        SPRINTING.serialize(config, meta.isSprinting());
        INVISIBLE.serialize(config, meta.isInvisible());
        GLOWING.serialize(config, meta.isGlowing());
        SWIMMING.serialize(config, meta.isSwimming());
        FLYING_WITH_ELYTRA.serialize(config, meta.isFlyingWithElytra());
        CUSTOM_NAME.serialize(config, meta.getCustomName());
        CUSTOM_NAME_VISIBLE.serialize(config, meta.isCustomNameVisible());
        SILENT.serialize(config, meta.isSilent());
        POSE.serialize(config, meta.getPose());
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(
            ON_FIRE, SNEAKING, SPRINTING, INVISIBLE, GLOWING, SWIMMING, FLYING_WITH_ELYTRA, CUSTOM_NAME,
            CUSTOM_NAME_VISIBLE, SILENT, POSE
        );
    }
}
