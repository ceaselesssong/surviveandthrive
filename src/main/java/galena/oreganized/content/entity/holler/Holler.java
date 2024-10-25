package galena.oreganized.content.entity.holler;

import com.mojang.serialization.Dynamic;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class Holler extends PathfinderMob {

    private static final List<SensorType<? extends Sensor<? super Holler>>> SENSOR_TYPES = List.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.HURT_BY
    );


    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
            MemoryModuleType.PATH,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.IS_PANICKING
    );

    public Holler(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        moveControl = new FlyingMoveControl(this, 20, true);
    }

    protected Brain.Provider<Holler> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_218344_) {
        return HollerAi.makeBrain(brainProvider().makeBrain(p_218344_));
    }

    @Override
    public Brain<Holler> getBrain() {
        return (Brain<Holler>) super.getBrain();
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        level().getProfiler().push("hollerBrain");
        getBrain().tick((ServerLevel)level(), this);
        level().getProfiler().pop();
        level().getProfiler().push("hollerActivityUpdate");
        HollerAi.updateActivity(this);
        level().getProfiler().pop();
        super.customServerAiStep();
        if ((tickCount + getId()) % 120 == 0) {
            applyFogAround((ServerLevel) level(), position(), this, 20);
        }
    }

    public static void applyFogAround(ServerLevel level, Vec3 pos, @Nullable Entity source, int radius) {
        MobEffectInstance mobeffectinstance = new MobEffectInstance(OEffects.FOG.get(), 260, 0, false, false);
        MobEffectUtil.addEffectToPlayersAround(level, source, pos, radius, mobeffectinstance, 200);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide && isAlive() && tickCount % 10 == 0) {
            heal(1.0F);
        }
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    public void tick() {
        noPhysics = true;
        super.tick();
        noPhysics = false;
        setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FLYING_SPEED, 0.10000000149011612)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void travel(Vec3 vec3) {
        if (isControlledByLocalInstance()) {
            if (isInWater()) {
                moveRelative(0.02F, vec3);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.800000011920929));
            } else if (isInLava()) {
                moveRelative(0.02F, vec3);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.5));
            } else {
                moveRelative(getSpeed(), vec3);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.9100000262260437));
            }
        }

        calculateEntityAnimation(false);
        tryCheckInsideBlocks();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void checkFallDamage(double p_218316_, boolean p_218317_, BlockState state, BlockPos pos) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ALLAY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        if ((state.getBlock() instanceof SnowyDirtBlock || state.is(Blocks.DIRT)) && isPanicking()) {
            BlockPos.withinManhattan(blockPosition(), 3, 1, 3).forEach(pos -> {
                if (level().getBlockState(pos).getBlock() instanceof SnowyDirtBlock && level().random.nextFloat() < 0.25)
                    level().setBlockAndUpdate(pos, OBlocks.BURIAL_DIRT.get().withPropertiesOf(state));
                double x = getX()+0.5+random.nextInt(-100, 100)*0.01;
                double y = getY()+1+random.nextInt(-100, 100)*0.01;
                double z = getZ()+0.5+random.nextInt(-100, 100)*0.01;
                if (!level().isClientSide) ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 3, 0, 0, 0, 0);
            });

            if (!level().isClientSide) {
                ServerLevel level = (ServerLevel) level();
                double x = getX()+0.5+random.nextInt(-100, 100)*0.01;
                double y = getY()+random.nextInt(-100, 100)*0.01;
                double z = getZ()+0.5+random.nextInt(-100, 100)*0.01;
                level.sendParticles(ParticleTypes.SMOKE, x, y, z, 3, 0, 0, 0, 0);
            }
            discard();
        }
    }

    public static boolean checkHollerSpawnRules(EntityType<Holler> entityType, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return levelAccessor.getBrightness(LightLayer.SKY, pos) < random.nextInt(8) && random.nextInt(10) > (isHalloween() ? 2 : 5) && checkMobSpawnRules(entityType, levelAccessor, spawnType, pos, random);
    }

    public boolean isPanicking() {
        return brain.getMemory(MemoryModuleType.IS_PANICKING).isPresent();
    }

    private static boolean isHalloween() {
        LocalDate $$0 = LocalDate.now();
        int $$1 = $$0.get(ChronoField.DAY_OF_MONTH);
        int $$2 = $$0.get(ChronoField.MONTH_OF_YEAR);
        return $$2 == 10 && $$1 >= 20 || $$2 == 11 && $$1 <= 3;
    }
}
