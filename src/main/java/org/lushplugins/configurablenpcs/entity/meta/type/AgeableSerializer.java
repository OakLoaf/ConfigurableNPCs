package org.lushplugins.configurablenpcs.entity.meta.type;

import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.meta.mobs.monster.zombie.ZombieMeta;
import me.tofaa.entitylib.meta.other.ArmorStandMeta;
import me.tofaa.entitylib.meta.types.AgeableMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;
import org.lushplugins.configurablenpcs.serializer.field.BooleanField;
import org.lushplugins.configurablenpcs.serializer.field.Field;

import java.util.List;

public class AgeableSerializer implements EntityMetaSerializer<EntityMeta> {
    private static final BooleanField BABY = new BooleanField("baby");

    @Override
    public List<Class<? extends EntityMeta>> getApplicableMetaTypes() {
        return List.of(
            ZombieMeta.class,
            ArmorStandMeta.class,
            AgeableMeta.class
        );
    }

    @Override
    public void deserialize(ConfigurationSection config, EntityMeta meta) {
        boolean baby = BABY.deserialize(config, false);
        if (meta instanceof ZombieMeta zombieMeta) {
            zombieMeta.setBaby(baby);
        } else if (meta instanceof ArmorStandMeta armorStandMeta) {
            armorStandMeta.setSmall(baby);
        } else if (meta instanceof AgeableMeta ageableMeta) {
            ageableMeta.setBaby(baby);
        }
    }

    @Override
    public void serialize(ConfigurationSection config, EntityMeta meta) {
        if (meta instanceof ZombieMeta zombieMeta) {
            BABY.serialize(config, zombieMeta.isBaby());
        } else if (meta instanceof ArmorStandMeta armorStandMeta) {
            BABY.serialize(config, armorStandMeta.isSmall());
        } else if (meta instanceof AgeableMeta ageableMeta) {
            BABY.serialize(config, ageableMeta.isBaby());
        }
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(BABY);
    }
}
