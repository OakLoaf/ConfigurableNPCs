package org.lushplugins.configurablenpcs.entity.meta.type;

import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.meta.other.AreaEffectCloudMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;
import org.lushplugins.configurablenpcs.serializer.field.*;

import java.util.List;

public class AreaEffectCloudSerializer implements EntityMetaSerializer<AreaEffectCloudMeta> {
    private static final FloatField RADIUS = new FloatField("radius");
    private static final IntegerField COLOR = new IntegerField("color");
    private static final BooleanField SINGLE_POINT = new BooleanField("single-point");

    @Override
    public List<Class<? extends EntityMeta>> getApplicableMetaTypes() {
        return List.of(AreaEffectCloudMeta.class);
    }

    @Override
    public void deserialize(ConfigurationSection config, AreaEffectCloudMeta meta) {
        meta.setRadius(RADIUS.deserialize(config, 0f));
        meta.setColor(COLOR.deserialize(config, 0));
        meta.setSinglePoint(SINGLE_POINT.deserialize(config, false));
    }

    @Override
    public void serialize(ConfigurationSection config, AreaEffectCloudMeta meta) {
        RADIUS.serialize(config, meta.getRadius());
        COLOR.serialize(config, meta.getColor());
        SINGLE_POINT.serialize(config, meta.isSinglePoint());
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(RADIUS, COLOR, SINGLE_POINT);
    }
}
