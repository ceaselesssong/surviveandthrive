package galena.oreganized.content.entity;

import galena.oreganized.index.OItems;
import galena.oreganized.index.OSoundEvents;
import net.minecraft.core.Position;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class LeadBoltEntity extends AbstractArrow {

    private static boolean canShootOff(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.getEnchantmentLevel(Enchantments.BINDING_CURSE) > 0) return false;
        return true;
    }

    @Nullable
    private static EquipmentSlot randomSlot(LivingEntity entity) {
        if (entity.isUsingItem()) {
            var hand = entity.getUsedItemHand();
            var using = entity.getItemInHand(hand);
            if (!canShootOff(using)) return null;
            if (using.getItem() instanceof ShieldItem) {
                return switch (hand) {
                    case OFF_HAND -> EquipmentSlot.OFFHAND;
                    case MAIN_HAND -> EquipmentSlot.MAINHAND;
                };
            }
        }

        var slots = Stream.of(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS)
                .filter(it -> canShootOff(entity.getItemBySlot(it)))
                .toList();

        if (slots.isEmpty()) return null;
        return slots.get(entity.getRandom().nextInt(0, slots.size()));

    }

    public LeadBoltEntity(EntityType<? extends LeadBoltEntity> type, Level level) {
        super(type, level);
    }

    public LeadBoltEntity(EntityType<? extends LeadBoltEntity> type, Level level, Position pos) {
        super(type, pos.x(), pos.y(), pos.z(), level);
    }

    public LeadBoltEntity(EntityType<? extends LeadBoltEntity> type, Level level, LivingEntity user) {
        super(type, user, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(OItems.LEAD_BOLT.get());
    }

    private void damageEntity(EntityHitResult result) {
        if (result.getEntity() instanceof LivingEntity living && living.getRandom().nextDouble() < 0.1) {
            if (knockOff(living)) {
                playSound(OSoundEvents.BOLT_HIT_ARMOR.get(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
                discard();
                return;
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        setSoundEvent(OSoundEvents.BOLT_HIT.get());

        var arrowCount = result.getEntity() instanceof LivingEntity living ? living.getArrowCount() : 0;

        var baseDamage = 3.0;
        setBaseDamage(baseDamage);

        if (result.getEntity().getType() == EntityType.IRON_GOLEM) {
            setBaseDamage(baseDamage * 3);
        }

        damageEntity(result);

        setBaseDamage(baseDamage);

        if (result.getEntity() instanceof LivingEntity living) {
            living.setArrowCount(arrowCount);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        setSoundEvent(OSoundEvents.BOLT_HIT.get());
        super.onHitBlock(result);
    }

    private boolean knockOff(LivingEntity entity) {
        var slot = randomSlot(entity);
        if (slot == null) return false;

        var stack = entity.getItemBySlot(slot);

        var item = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
        item.setPickUpDelay(40);
        level().addFreshEntity(item);

        entity.setItemSlot(slot, ItemStack.EMPTY);

        return true;
    }

    @Override
    public void shootFromRotation(Entity user, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        super.shootFromRotation(user, p_37253_, p_37254_, p_37255_, p_37256_ * 0.5F, p_37257_);
    }
}
