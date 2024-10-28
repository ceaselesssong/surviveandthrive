package galena.oreganized.content.entity.holler;

import com.mojang.serialization.Dynamic;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OSoundEvents;
import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    @SuppressWarnings("unchecked")
    @Override
    public Brain<Holler> getBrain() {
        return (Brain<Holler>) super.getBrain();
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        level().getProfiler().push("hollerBrain");
        getBrain().tick((ServerLevel) level(), this);
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
        var applied = MobEffectUtil.addEffectToPlayersAround(level, source, pos, radius, mobeffectinstance, 200);
        applied.forEach(it -> {
            level.playSound(it, source, OSoundEvents.HOLLER_SHRIEKS.get(), source.getSoundSource(), 1F, 1F);
        });
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
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
        return OSoundEvents.HOLLER_HOLLERS.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return OSoundEvents.HOLLER_HURTS.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OSoundEvents.HOLLER_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    private void disappear(ServerLevel level) {
        double x = getX() + 0.5 + random.nextInt(-100, 100) * 0.01;
        double y = getY() + 1 + random.nextInt(-100, 100) * 0.01;
        double z = getZ() + 0.5 + random.nextInt(-100, 100) * 0.01;
        level.sendParticles(ParticleTypes.SMOKE, x, y, z, 3, 0, 0, 0, 0);

        playSound(OSoundEvents.HOLLER_SHRIEKS.get(), 1F, 1F);

        discard();
    }

    @Override
    protected void onInsideBlock(BlockState unused) {
        if (!(level() instanceof ServerLevel level)) return;
        if (!isPanicking()) return;
        var target = getBrain().getMemory(MemoryModuleType.WALK_TARGET).map(it -> it.getTarget().currentPosition());
        if (target.isEmpty() || target.get().distanceToSqr(position()) > 0.5) return;

        var state = level.getBlockState(blockPosition());

        if (state.is(OTags.Blocks.CAN_TURN_INTO_BURIAL_DIRT)) {
            if (level.random.nextFloat() < 0.25) return;

            level.setBlockAndUpdate(blockPosition(), OBlocks.BURIAL_DIRT.get().defaultBlockState());

            disappear(level);
        }

        if(state.is(Blocks.JUKEBOX)) {
            disappear(level);

            if(state.getValue(JukeboxBlock.HAS_RECORD)) return;

            level.getBlockEntity(blockPosition(), BlockEntityType.JUKEBOX).ifPresent(jukebox -> {
                var stack = new ItemStack(OItems.MUSIC_DISC_AFTERLIFE.get());
                jukebox.setFirstItem(stack);
            });
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
