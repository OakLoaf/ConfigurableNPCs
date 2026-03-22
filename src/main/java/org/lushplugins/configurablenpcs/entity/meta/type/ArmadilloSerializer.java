package org.lushplugins.configurablenpcs.entity.meta.type;

import com.github.retrooper.packetevents.protocol.entity.armadillo.ArmadilloState;
import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.meta.mobs.passive.ArmadilloMeta;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.meta.EntityMetaSerializer;
import org.lushplugins.configurablenpcs.serializer.field.EnumField;
import org.lushplugins.configurablenpcs.serializer.field.Field;

import java.util.List;

public class ArmadilloSerializer implements EntityMetaSerializer<ArmadilloMeta> {
    private static final EnumField<ArmadilloState> STATE = new EnumField<>("state", ArmadilloState.class);

    @Override
    public List<Class<? extends EntityMeta>> getApplicableMetaTypes() {
        return List.of(ArmadilloMeta.class);
    }

    @Override
    public void deserialize(ConfigurationSection config, ArmadilloMeta meta) {
        meta.setState(STATE.deserialize(config, ArmadilloState.IDLE));
    }

    @Override
    public void serialize(ConfigurationSection config, ArmadilloMeta meta) {
        STATE.serialize(config, meta.getState());
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(STATE);
    }
}
