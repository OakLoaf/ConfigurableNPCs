package org.lushplugins.configurablenpcs.entity.meta;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.tofaa.entitylib.meta.EntityMeta;
import org.apache.commons.lang3.ClassUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.meta.type.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityMetaSerializers {
    private final Multimap<Class<?>, EntityMetaSerializer<?>> serializers = HashMultimap.create();

    public EntityMetaSerializers() {
        register(new AgeableSerializer());
        register(new AreaEffectCloudSerializer());
        register(new ArmadilloSerializer());
//        register(new ArmorStandSerializer());
        register(new AvatarSerializer());
//        register(new AxolotlSerializer());
//        register(new BatSerializer());
//        register(new BeeSerializer());
//        register(new BlazeSerializer());
//        register(new BlockDisplaySerializer());
//        register(new BoatSerializer());
//        register(new CamelSerializer());
//        register(new CatSerializer());
//        register(new ChickenSerializer());
//        register(new CowSerializer());
//        register(new CreeperSerializer());
//        register(new DolphinSerializer());
//        register(new DragonFireballSerializer());
//        register(new EndCrystalSerializer());
//        register(new EnderDragonSerializer());
//        register(new EndermanSerializer());
        register(new EntitySerializer());
//        register(new FallingBlockSerializer());
//        register(new LargeFireballSerializer());
//        register(new FireworkRocketSerializer());
//        register(new FishingHookSerializer());
//        register(new FoxSerializer());
//        register(new FrogSerializer());
//        register(new FurnaceMinecartSerializer());
//        register(new GhastSerializer());
//        register(new GlowSquidSerializer());
//        register(new GoatSerializer());
//        register(new GuardianSerializer());
//        register(new GhastSerializer());
//        register(new HoglinSerializer());
//        register(new HorseSerializer());
//        register(new InteractionSerializer());
//        register(new ItemDisplaySerializer());
//        register(new ItemFrameSerializer());
//        register(new LivingEntitySerializer());
//        register(new LlamaSerializer());
//        register(new MarkerSerializer());
//        register(new MobSerializer());
//        register(new MooshroomSerializer());
//        register(new OcelotSerializer());
//        register(new PaintingSerializer());
//        register(new PandaSerializer());
//        register(new ParrotSerializer());
//        register(new PhantomSerializer());
//        register(new PigSerializer());
//        register(new PiglinSerializer());
//        register(new PlayerSerializer());
//        register(new PolarBearSerializer());
//        register(new PrimedTntSerializer());
//        register(new PufferFishSerializer());
//        register(new RabbitSerializer());
//        register(new SheepSerializer());
//        register(new ShulkerSerializer());
//        register(new SlimeSerializer());
//        register(new SmallFireballSerializer());
//        register(new SnifferSerializer());
//        register(new SnowGolemSerializer());
//        register(new SpiderSerializer());
//        register(new StriderSerializer());
//        register(new TextDisplaySerializer());
//        register(new TntSerializer());
//        register(new ThrownTridentSerializer());
//        register(new TropicalFishSerializer());
//        register(new TurtleSerializer());
//        register(new VexSerializer());
//        register(new VillagerSerializer());
//        register(new WardenSerializer());
//        register(new WitchSerializer());
//        register(new WitherSerializer());
//        register(new WitherSkullSerializer());
//        register(new WolfSerializer());
//        register(new ZoglinSerializer());
//        register(new ZombieSerializer());
//        register(new ZombieVillagerSerializer());
    }

    public Collection<EntityMetaSerializer<?>> getSerializersApplicableTo(Class<? extends EntityMeta> metaType) {
        List<EntityMetaSerializer<?>> serializers = new ArrayList<>();

        if (this.serializers.containsKey(metaType)) {
            serializers.addAll(this.serializers.get(metaType));
        }

        for (Class<?> metaClass : ClassUtils.getAllSuperclasses(metaType)) {
            Collection<EntityMetaSerializer<?>> serializer = this.serializers.get(metaClass);
            if (!serializer.isEmpty()) {
                serializers.addAll(serializer);
            }
        }

        return serializers;
    }

    public Collection<EntityMetaSerializer<?>> getSerializersApplicableTo(EntityMeta meta) {
        return getSerializersApplicableTo(meta.getClass());
    }

    public Collection<EntityMetaSerializer<?>> getSerializersApplicableTo(EntityType entityType) {
        return getSerializersApplicableTo(EntityMeta.getMetaClass(entityType));
    }

    public void deserialize(EntityMeta meta, ConfigurationSection config) {
        getSerializersApplicableTo(meta).forEach(serializer -> {
            serializer.castAndDeserialize(config, meta);
        });
    }

    public void serialize(EntityMeta meta, ConfigurationSection config) {
        getSerializersApplicableTo(meta).forEach(serializer -> {
            serializer.castAndSerialize(config, meta);
        });
    }

    private void register(EntityMetaSerializer<?> serializer) {
        for (Class<? extends EntityMeta> type : serializer.getApplicableMetaTypes()) {
            serializers.put(type, serializer);
        }
    }
}
