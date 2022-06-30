package me.gleep.oreganized.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.gleep.oreganized.client.model.ElectrumArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.OreganizedArmorMaterials;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

public class ElectrumArmorItem extends ArmorItem {
    private static final String TEXTURE = Oreganized.MOD_ID + ":textures/entity/electrum_armor.png";

    public ElectrumArmorItem(ArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Properties()
                .tab(CreativeModeTab.TAB_COMBAT)
        );
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if(this.getMaterial() == OreganizedArmorMaterials.ELECTRUM && slot == this.slot) {
            UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
            return ImmutableMultimap.of(
                    Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Electrum speed", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
                    Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", OreganizedArmorMaterials.ELECTRUM.getDefenseForSlot(this.slot), AttributeModifier.Operation.ADDITION),
                    Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", OreganizedArmorMaterials.ELECTRUM.getToughness(), AttributeModifier.Operation.ADDITION),
                    Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", OreganizedArmorMaterials.ELECTRUM.getKnockbackResistance(), AttributeModifier.Operation.ADDITION)
            );
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new ElectrumArmorModel<>(ElectrumArmorModel.createBodyLayer().bakeRoot(), armorSlot);
            }
        });
    }

}
