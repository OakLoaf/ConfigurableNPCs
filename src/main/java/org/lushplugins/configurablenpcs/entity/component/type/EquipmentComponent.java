package org.lushplugins.configurablenpcs.entity.component.type;

import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import me.tofaa.entitylib.wrapper.WrapperEntityEquipment;
import me.tofaa.entitylib.wrapper.WrapperLivingEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.configurablenpcs.entity.component.EntityComponent;
import org.lushplugins.configurablenpcs.serializer.field.Field;
import org.lushplugins.lushlib.config.YamlConverter;
import org.lushplugins.lushlib.config.YamlUtils;
import org.lushplugins.lushlib.item.DisplayItemStack;

import java.util.Arrays;
import java.util.List;

public class EquipmentComponent implements EntityComponent {
    private static final Field<ItemStack[]> EQUIPMENT = new Field<>("equipment") {

        @Override
        public ItemStack[] deserialize(ConfigurationSection config, ItemStack[] def) {
            ItemStack[] equipment = new ItemStack[6];
            Arrays.fill(equipment, ItemStack.EMPTY);

            YamlUtils.getMap(config, "equipment", ConfigurationSection.class)
                .forEach((key, section) -> {
                    EquipmentSlot slot = EquipmentSlot.valueOf(key.toUpperCase());
                    DisplayItemStack item = YamlConverter.getDisplayItem(section);
                    if (item.hasType()) {
                        equipment[slot.ordinal()] = SpigotConversionUtil.fromBukkitItemStack(item.asItemStack());
                    }
                });

            return equipment;
        }

        @Override
        public void serialize(ConfigurationSection config, ItemStack[] value) {
            ConfigurationSection equipmentSection = config.createSection("equipment");
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack item = value[slot.ordinal()];
                if (item == null || item == ItemStack.EMPTY) {
                    continue;
                }

                ConfigurationSection section = equipmentSection.createSection(slot.name().toLowerCase());
                YamlConverter.setDisplayItem(section, DisplayItemStack.builder(SpigotConversionUtil.toBukkitItemStack(item)).build());
            }
        }
    };

    private final ItemStack[] equipment;

    public EquipmentComponent(ConfigurationSection config) {
        this.equipment = Arrays.stream(EQUIPMENT.deserialize(config, new ItemStack[6]))
            .map(item -> item != null ? item : ItemStack.EMPTY)
            .toArray(ItemStack[]::new);
    }

    @Override
    public Class<? extends WrapperEntity> getApplicableWrapper() {
        return WrapperLivingEntity.class;
    }

    @Override
    public void applyTo(WrapperEntity entity) {
        if (entity instanceof WrapperLivingEntity livingEntity) {
            WrapperEntityEquipment equipment = livingEntity.getEquipment();

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                equipment.setItem(slot, this.equipment[slot.ordinal()]);
            }
        }
    }

    @Override
    public void serialize(ConfigurationSection config) {
        EQUIPMENT.serialize(config, this.equipment);
    }

    @Override
    public List<Field<?>> getFields() {
        return List.of(EQUIPMENT);
    }
}
