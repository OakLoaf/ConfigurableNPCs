package org.lushplugins.configurablenpcs.entity.component.type;

import com.github.retrooper.packetevents.protocol.attribute.Attribute;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import me.tofaa.entitylib.wrapper.WrapperEntityAttributes;
import me.tofaa.entitylib.wrapper.WrapperLivingEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.component.EntityComponent;
import org.lushplugins.configurablenpcs.serializer.field.Field;
import org.lushplugins.lushlib.config.YamlUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributesComponent implements EntityComponent {
    private static final Field<List<WrapperPlayServerUpdateAttributes.Property>> ATTRIBUTES = new Field<>("attributes") {

        @Override
        public List<WrapperPlayServerUpdateAttributes.Property> deserialize(ConfigurationSection config, List<WrapperPlayServerUpdateAttributes.Property> def) {
            return YamlUtils.getConfigurationSections(config, "attributes").stream()
                .map(section -> {
                    Attribute attribute = Attributes.getByName(section.getString("attribute"));
                    double value = section.getDouble("value");
                    return new WrapperPlayServerUpdateAttributes.Property(attribute, value, Collections.emptyList());
                })
                .toList();
        }

        @Override
        public void serialize(ConfigurationSection config, List<WrapperPlayServerUpdateAttributes.Property> values) {
            List<Map<String, Object>> sections = values.stream()
                .map(property -> {
                    Map<String, Object> section = new HashMap<>();
                    section.put("attribute", property.getAttribute().getName().toString());
                    section.put("value", property.getValue());
                    return section;
                })
                .toList();

            config.set("attributes", sections);
        }
    };

    private final List<WrapperPlayServerUpdateAttributes.Property> attributes;

    public AttributesComponent(ConfigurationSection config) {
        this.attributes = ATTRIBUTES.deserialize(config, Collections.emptyList());
    }

    @Override
    public Class<? extends WrapperEntity> getApplicableWrapper() {
        return WrapperLivingEntity.class;
    }

    @Override
    public void applyTo(WrapperEntity entity) {
        if (entity instanceof WrapperLivingEntity livingEntity) {
            WrapperEntityAttributes attributes = livingEntity.getAttributes();

            for (WrapperPlayServerUpdateAttributes.Property attribute : this.attributes) {
                attributes.setAttribute(attribute.getAttribute(), attribute.getValue(), attribute.getModifiers());
            }
        }
    }

    @Override
    public void serialize(ConfigurationSection config) {
        ATTRIBUTES.serialize(config, this.attributes);
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(ATTRIBUTES);
    }
}
