package galena.oreganized.content.entity;

import galena.oreganized.index.OItems;
import galena.oreganized.index.OSoundEvents;
import net.minecraft.core.Position;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.stream.Stream;

public class LeadBoltEntity extends AbstractArrow {

    private static Stream<EquipmentSlot> armorSlots() {
        return Stream.of(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS);
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

    @Override
    protected void onHitEntity(EntityHitResult result) {
        setSoundEvent(OSoundEvents.BOLT_HIT.get());

        var arrowCount = result.getEntity() instanceof LivingEntity living ? living.getArrowCount() : 0;

        var baseDamage = getBaseDamage() * 2.0;
        setBaseDamage(baseDamage);

        if(result.getEntity().getType() == EntityType.IRON_GOLEM) {
            setBaseDamage(baseDamage * 10);
        }

        super.onHitEntity(result);

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

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        if (entity.getRandom().nextDouble() < 0.1) {
            var slots = armorSlots().filter(entity::hasItemInSlot).toList();
            if (slots.isEmpty()) return;

            setSoundEvent(OSoundEvents.BOLT_HIT_ARMOR.get());

            var slot = slots.get(entity.getRandom().nextInt(0, slots.size()));
            var stack = entity.getItemBySlot(slot);

            ItemEntity item;
            if (entity instanceof Player player) {
                item = player.drop(stack, true, true);
            } else {
                item = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                item.setPickUpDelay(40);
                level().addFreshEntity(item);
            }

            if (item != null) {
                entity.setItemSlot(slot, ItemStack.EMPTY);
            }
        }
    }
}
