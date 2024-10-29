package galena.oreganized.content.entity.holler;

import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OParticleTypes;
import galena.oreganized.index.OSoundEvents;
import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
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
        setPathfindingMalus(BlockPathTypes.WATER, -1);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1);
        moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new HollerPanicGoal(this, 3F));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.5, 2));
        goalSelector.addGoal(4, new HollerFollowGoal(this, 8F, 3F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        goalSelector.addGoal(10, new HollerStrollGoal(this, 1F));

        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (tickCount % 20 == 0) {
            applyFogAround((ServerLevel) level(), position(), this, 20);
        }
    }

    public static void applyFogAround(ServerLevel level, Vec3 pos, @Nullable Entity source, int radius) {
        MobEffectInstance mobeffectinstance = new MobEffectInstance(OEffects.FOG.get(), 260, 0, false, false);
        var applied = MobEffectUtil.addEffectToPlayersAround(level, source, pos, radius, mobeffectinstance, 20 * 60 * 4);
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
        playSound(OSoundEvents.HOLLER_SHRIEKS.get(), 1F, 1F);
        discard();
    }

    public void panicFinish(Vec3 target) {
        if (!(level() instanceof ServerLevel level)) return;
        if (target.distanceToSqr(position()) > 0.5) return;

        var state = level.getBlockState(blockPosition());

        if (state.is(Blocks.JUKEBOX)) {
            disappear(level);

            if (state.getValue(JukeboxBlock.HAS_RECORD)) return;

            for (int i = 0; i < 6; i++) {
                var vec = Vec3.atCenterOf(blockPosition()).add(Math.random() * 1 - 0.5, Math.random() * 1 - 0.5, Math.random() * 1 - 0.5);
                level.sendParticles(OParticleTypes.HOLLERING_SOUL.get(), vec.x, vec.y, vec.z, 1, 0, 0, 0, 0);
            }

            level.getBlockEntity(blockPosition(), BlockEntityType.JUKEBOX).ifPresent(jukebox -> {
                var stack = new ItemStack(OItems.MUSIC_DISC_AFTERLIFE.get());
                jukebox.setFirstItem(stack);
            });
        } else {
            curseGround(level, blockPosition());
            disappear(level);
        }
    }

    private void curseGround(ServerLevel level, BlockPos center) {
        var aabb = new AABB(center).inflate(2, 1, 2);
        BlockPos.betweenClosedStream(aabb).forEach(pos -> {
            if (level.random.nextDouble() > 0.2) return;
            if (!level.getBlockState(pos.above()).canBeReplaced()) return;

            var state = level.getBlockState(pos);
            if (state.is(OTags.Blocks.CAN_TURN_INTO_BURIAL_DIRT)) {
                level.setBlockAndUpdate(pos, OBlocks.BURIAL_DIRT.get().defaultBlockState());

                var vec = Vec3.atCenterOf(blockPosition()).add(Math.random() * 0.5 - 0.25, 1 + Math.random() * 0.2, Math.random() * 0.5 - 0.25);
                level.sendParticles(OParticleTypes.HOLLERING_SOUL.get(), vec.x, vec.y, vec.z, 1, 0, 0, 0, 0);
            }
        });
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
