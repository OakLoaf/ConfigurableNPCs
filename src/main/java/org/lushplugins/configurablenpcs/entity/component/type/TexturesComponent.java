package org.lushplugins.configurablenpcs.entity.component.type;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import me.tofaa.entitylib.wrapper.WrapperPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.component.EntityComponent;
import org.lushplugins.configurablenpcs.serializer.field.Field;
import org.lushplugins.configurablenpcs.util.SkinUtils;
import org.mineskin.data.ValueAndSignature;

import java.util.*;

public class TexturesComponent implements EntityComponent {
    private static final Field<TextureProperty> TEXTURES = new Field<>("textures") {

        @Override
        public TextureProperty deserialize(ConfigurationSection config, TextureProperty def) {
            String value = config.getString("textures.value");
            String signature = config.getString("textures.signature");
            return new TextureProperty("textures", value, signature);
        }

        @Override
        public void serialize(ConfigurationSection config, TextureProperty value) {
            config.set("value", value.getValue());

            String signature = value.getSignature();
            if (signature != null) {
                config.set("signature", signature);
            }
        }
    };

    private TextureProperty textures;

    public TexturesComponent(ConfigurationSection config) {
        this.textures = TEXTURES.deserialize(config, null);
    }

    @Override
    public Class<? extends WrapperEntity> getApplicableWrapper() {
        return WrapperPlayer.class;
    }

    @Override
    public void applyTo(WrapperEntity entity) {
        if (textures == null) {
            return;
        }

        if (entity instanceof WrapperPlayer player) {
            // TODO: Add 'mirror' and uuid/name placeholder support?
            player.setTextureProperties(Collections.singletonList(textures));

            if (textures.getSignature() == null) {
                SkinUtils.generateSkin(textures.getValue()).thenAccept(skin -> {
                    ValueAndSignature textureData = skin.texture().data();
                    textures = new TextureProperty("textures", textureData.value(), textureData.signature());
                    player.setTextureProperties(Collections.singletonList(textures));
                });
            }
        }
    }

    @Override
    public void serialize(ConfigurationSection config) {
        TEXTURES.serialize(config, this.textures);
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(TEXTURES);
    }
}
