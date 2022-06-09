package me.gleep.oreganized.entities.projectiles;

import me.gleep.oreganized.effect.StunnedEffect;
import me.gleep.oreganized.potion.ModPotions;
import me.gleep.oreganized.registry.OreganizedEntityTypes;
import me.gleep.oreganized.registry.OreganizedItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class LeadBolt  extends ThrowableItemProjectile {

    protected boolean ricochet;
    protected int ricochetTimes = 0;

    private boolean dropItem;

    public LeadBolt(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
        this.setDropItem(false);
    }

    public LeadBolt(EntityType<? extends ThrowableItemProjectile> type, double x, double y, double z, Level level) {
        super(OreganizedEntityTypes.LEAD_BOLT.get(), x, y, z, level);
    }

    public LeadBolt(EntityType<? extends ThrowableItemProjectile> type, LivingEntity shooter, Level level) {
        super(OreganizedEntityTypes.LEAD_BOLT.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return OreganizedItems.LEAD_BOLT.get();
    }

    private ParticleOptions makeParticle() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(getDefaultItem()));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions iparticledata = this.makeParticle();

            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(new IndirectEntityDamageSource("arrow", this, this.getOwner()), 0.0F);
            livingEntity.addEffect(new MobEffectInstance(ModPotions.STUNNED, 100, 0, false, true));
        }
        this.playSound(SoundEvents.ARROW_HIT_PLAYER, 1, 1);

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockState blockState = this.level.getBlockState(result.getBlockPos());
        LivingEntity shooter = (LivingEntity) this.getOwner();
        if (!blockState.getCollisionShape(this.level, result.getBlockPos()).isEmpty()) {
            this.playStepSound(result.getBlockPos(), blockState);
            if (!this.level.isClientSide) {
                Vec3 delta = this.getDeltaMovement();
                Direction direction = result.getDirection();
                float velocity = (float) delta.length() / 2.0F;
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    this.shoot(delta.x, delta.reverse().y, delta.z, velocity, 1.0F);
                } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    this.shoot(delta.x, delta.y, delta.reverse().z, velocity, 1.0F);
                } else if (direction == Direction.EAST || direction == Direction.WEST) {
                    this.shoot(delta.reverse().x, delta.y, delta.z, velocity, 1.0F);
                } else {
                    this.shoot(delta.x, delta.reverse().y, delta.reverse().z, velocity, 1.0F);
                }
                this.ricochetTimes--;
                if (this.ricochetTimes == 0) {
                    this.discard();
                    if (!(shooter instanceof Player) || ((Player) shooter).getAbilities().instabuild) {
                        // don't drop anything
                    } else if (this.dropItem) {
                        this.spawnAtLocation(new ItemStack(getDefaultItem()));
                    }
                }
            } else {
                this.discard();
                if (!(shooter instanceof Player) || ((Player) shooter).getAbilities().instabuild) {
                    // don't drop anything
                } else if (this.dropItem) {
                    this.spawnAtLocation(new ItemStack(getDefaultItem()));
                }
            }
        }
    }

    public void setRicochetTimes(int times) {
        this.ricochet = true;
        this.ricochetTimes = times;
    }

    protected void setDropItem(boolean dropItem) {
        this.dropItem = dropItem;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
