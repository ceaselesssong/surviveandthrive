package galena.oreganized.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import galena.oreganized.Oreganized;
import galena.oreganized.client.model.ElectrumArmorModel;
import galena.oreganized.registry.OArmorMaterials;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

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
        if(slot == this.slot) {
            UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];
            return ImmutableMultimap.of(
                    Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Electrum speed", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
                    Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", OArmorMaterials.ELECTRUM.getDefenseForSlot(this.slot), AttributeModifier.Operation.ADDITION),
                    Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", OArmorMaterials.ELECTRUM.getToughness(), AttributeModifier.Operation.ADDITION),
                    Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", OArmorMaterials.ELECTRUM.getKnockbackResistance(), AttributeModifier.Operation.ADDITION)
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
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new ElectrumArmorModel<>(ElectrumArmorModel.createBodyLayer().bakeRoot(), armorSlot);
            }
        });
    }
}
