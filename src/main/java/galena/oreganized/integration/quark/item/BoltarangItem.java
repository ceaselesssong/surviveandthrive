package galena.oreganized.integration.quark.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import galena.oreganized.content.item.OItem;
import galena.oreganized.index.OItemTiers;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OTags;
import galena.oreganized.integration.quark.entity.Boltarang;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import vazkii.quark.base.handler.QuarkSounds;
import vazkii.quark.content.tools.entity.rang.AbstractPickarang;
import vazkii.quark.content.tools.module.PickarangModule;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;

public class BoltarangItem extends OItem {

    public BoltarangItem(ItemLike follow) {
        super(new Properties().stacksTo(1).durability(800), follow);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, (player) -> {
            player.broadcastBreakEvent(InteractionHand.MAIN_HAND);
        });
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(@Nonnull BlockState blockIn) {
        return OItems.ELECTRUM_PICKAXE.get().isCorrectToolForDrops(blockIn) || OItems.ELECTRUM_AXE.get().isCorrectToolForDrops(blockIn) || OItems.ELECTRUM_SHOVEL.get().isCorrectToolForDrops(blockIn);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Math.max(OItemTiers.ELECTRUM.getUses(), 0);
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level worldIn, BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(1, entityLiving, (player) -> {
                player.broadcastBreakEvent(InteractionHand.MAIN_HAND);
            });
        }

        return true;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.setItemInHand(handIn, ItemStack.EMPTY);
        int eff = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemstack);
        Vec3 pos = playerIn.position();
        worldIn.playSound((Player)null, pos.x, pos.y, pos.z, QuarkSounds.ENTITY_PICKARANG_THROW, SoundSource.NEUTRAL, 0.5F + (float)eff * 0.14F, 0.4F / (worldIn.random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isClientSide) {
            Inventory inventory = playerIn.getInventory();
            int slot = handIn == InteractionHand.OFF_HAND ? inventory.getContainerSize() - 1 : inventory.selected;
            AbstractPickarang<?> entity = new Boltarang(worldIn, playerIn);
            entity.setThrowData(slot, itemstack);
            entity.shoot(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F + (float)eff * 0.325F, 0.0F);
            entity.setOwner(playerIn);
            worldIn.addFreshEntity(entity);
        }

        if (!playerIn.getAbilities().instabuild && PickarangModule.pickarangType.cooldown > 0) {
            int cooldown = PickarangModule.pickarangType.cooldown - eff;
            if (cooldown > 0) {
                playerIn.getCooldowns().addCooldown(this, cooldown);
            }
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    @Nonnull
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = Multimaps.newSetMultimap(new HashMap(), HashSet::new);
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)PickarangModule.pickarangType.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.8, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
        return 0.0F;
    }

    @Override
    public boolean isRepairable(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, ItemStack repair) {
        return repair.is(OTags.Items.INGOTS_ELECTRUM);
    }

    @Override
    public int getEnchantmentValue() {
        return OItems.ELECTRUM_PICKAXE.get().getEnchantmentValue();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || ImmutableSet.of(Enchantments.BLOCK_FORTUNE, Enchantments.SILK_TOUCH, Enchantments.BLOCK_EFFICIENCY).contains(enchantment);
    }
}
