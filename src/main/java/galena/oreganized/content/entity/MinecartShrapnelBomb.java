package galena.oreganized.content.entity;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import galena.oreganized.index.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MinecartShrapnelBomb extends MinecartTNT {
    private static final byte EVENT_PRIME = 10;
    private int fuse = -1;

    public MinecartShrapnelBomb(EntityType<? extends MinecartShrapnelBomb> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public AbstractMinecart.Type getMinecartType() {
        return AbstractMinecart.Type.TNT;
    }

    @Override
    public BlockState getDefaultDisplayBlockState() {
        return OBlocks.SHRAPNEL_BOMB.get().defaultBlockState();
    }

    @Override
    protected Item getDropItem() {
        return OItems.SHRAPNEL_BOMB_MINECART.get();
    }

    @Override
    protected void explode(double p_38689_) {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.BlockInteraction.NONE);
        if (!this.level.isClientSide()) ((ServerLevel)this.level).sendParticles(OParticleTypes.LEAD_SHRAPNEL.get(),
                this.getX(), this.getY(0.0625D) , this.getZ(), 100, 0.0D, 0.0D, 0.0D, 5);
        for (Entity entity : this.level.getEntities(this, new AABB(this.getX() - 30, this.getY() - 4, this.getZ() - 30,
                this.getX() + 30, this.getY() + 4, this.getZ() + 30))) {
            int random = (int) (Math.random() * 100);
            boolean shouldPoison = false;
            if (entity.distanceToSqr(this) <= 16) {
                shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 64) {
                if(random < 60) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 225) {
                if (random < 30) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 900) {
                if (random < 5) shouldPoison = true;
            }
            if (shouldPoison && entity instanceof LivingEntity living) {
                living.hurt(DamageSource.MAGIC, 2);
                living.addEffect(new MobEffectInstance(OEffects.STUNNING.get(), 800));
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 260));
            }
        }
        this.discard();
    }

    @Override
    public void primeFuse() {
        this.fuse = 80;
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)10);
            if (!this.isSilent()) {
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), OSoundEvents.SHRAPNEL_BOMB_PRIMED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("ShrapnelBombFuse", 99)) {
            this.fuse = nbt.getInt("ShrapnelBombFuse");
        }

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("ShrapnelBombFuse", this.fuse);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(OItems.SHRAPNEL_BOMB_MINECART.get());
    }
}
