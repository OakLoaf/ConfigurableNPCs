package org.lushplugins.configurablenpcs.entity.meta.type;

import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.meta.mobs.passive.ArmadilloMeta;
import me.tofaa.entitylib.meta.types.AvatarMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;
import org.lushplugins.configurablenpcs.serializer.field.BooleanField;
import org.lushplugins.configurablenpcs.serializer.field.Field;
import org.lushplugins.configurablenpcs.serializer.field.StringField;

import java.util.List;

public class AvatarSerializer implements EntityMetaSerializer<AvatarMeta> {
    private static final StringField MAIN_HAND = new StringField("main-hand");
    private static final BooleanField SHOW_CAPE = new BooleanField("show-cape");
    private static final BooleanField SHOW_SKIN_LAYER = new BooleanField("show-skin-layer");

    @Override
    public List<Class<? extends EntityMeta>> getApplicableMetaTypes() {
        return List.of(ArmadilloMeta.class);
    }

    @Override
    public void deserialize(ConfigurationSection config, AvatarMeta meta) {
        meta.setRightHandMain(!MAIN_HAND.deserialize(config, "right").equalsIgnoreCase("left"));
        meta.setCapeEnabled(SHOW_CAPE.deserialize(config, false));

        boolean showSkinLayer = SHOW_SKIN_LAYER.deserialize(config, true);
        meta.setJacketEnabled(showSkinLayer);
        meta.setLeftSleeveEnabled(showSkinLayer);
        meta.setRightSleeveEnabled(showSkinLayer);
        meta.setLeftLegEnabled(showSkinLayer);
        meta.setRightLegEnabled(showSkinLayer);
        meta.setHatEnabled(showSkinLayer);
    }

    @Override
    public void serialize(ConfigurationSection config, AvatarMeta meta) {
        MAIN_HAND.serialize(config, meta.isRightHandMain() ? "right" : "left");
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(MAIN_HAND);
    }
}
