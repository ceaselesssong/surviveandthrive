package galena.oreganized.content.block;

import galena.oreganized.OreganizedConfig;
import galena.oreganized.index.OCriteriaTriggers;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OParticleTypes;
import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

public class LeadOreBlock extends DropExperienceBlock {

    public LeadOreBlock(Properties properties) {
        super(properties);
    }

    private static Stream<MobEffectInstance> getEffects(int durationMultiplier) {
        if (OreganizedConfig.COMMON.poisonInsteadOfStunning.get()) {
            return Stream.of(new MobEffectInstance(MobEffects.POISON, 150 * durationMultiplier));
        }

        return Stream.of(
                new MobEffectInstance(OEffects.STUNNING.get(), 600 * durationMultiplier),
                new MobEffectInstance(MobEffects.POISON, 40)
        );
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropXp) {
        super.spawnAfterBreak(state, level, pos, stack, dropXp);

        if (shouldSpawnCloud(state, level, pos, stack)) {
            spawnCloud(level, pos, 2F);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       boolean willHarvest, FluidState fluid) {
        var stack = player.getItemInHand(player.getUsedItemHand());
        if (shouldSpawnCloud(state, level, pos, stack) && player instanceof ServerPlayer serverPlayer) {
            OCriteriaTriggers.IN_LEAD_CLOUD.trigger(serverPlayer);
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    protected boolean shouldSpawnCloud(BlockState state, LevelAccessor level, BlockPos pos, ItemStack stack) {
        if (!OreganizedConfig.COMMON.leadDustCloud.get()) return false;
        if (stack.is(OItems.SCRIBE.get()) || EnchantmentHelper.hasSilkTouch(stack)) return false;

        for (var direction : Direction.values()) {
            var adjacentState = level.getBlockState(pos.relative(direction));
            if (adjacentState.is(OTags.Blocks.PREVENTS_LEAD_CLOUD)) return false;
        }

        return true;
    }

    public static AreaEffectCloud spawnCloud(Level level, BlockPos pos, float size) {
        var vec = Vec3.atCenterOf(pos);
        var cloud = new AreaEffectCloud(level, vec.x, vec.y, vec.z);

        getEffects(Math.max(1, (int) (size))).forEach(cloud::addEffect);

        cloud.setParticle(OParticleTypes.LEAD_CLOUD.get());
        cloud.setRadius(1.5F * size);
        cloud.setRadiusPerTick(-0.02F);
        cloud.setDuration((int) (120 * size));

        level.addFreshEntity(cloud);
        return cloud;
    }

    public static void blowParticles(LevelAccessor level, BlockPos pos, Direction facing, int maxDistance) {
        var speed = 0.5;
        maxDistance = Math.min(maxDistance, 8);

        for (int distance = 1; distance < maxDistance; distance++) {
            var frontPos = pos.relative(facing, distance);
            var frontState = level.getBlockState(frontPos);

            if (frontState.getBlock() instanceof LeadOreBlock) {
                var vec = Vec3.atCenterOf(frontPos);
                level.addParticle(OParticleTypes.LEAD_BLOW.get(),
                        vec.x, vec.y, vec.z,
                        facing.getStepX() * speed, facing.getStepY() * speed, facing.getStepZ() * speed
                );

                var targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(frontPos, pos.relative(facing, maxDistance)).expandTowards(1, 1, 1));

                targets.forEach(target -> {
                    getEffects(1)
                            .filter(it -> !target.hasEffect(it.getEffect()))
                            .forEach(target::addEffect);
                });

                return;
            }
        }
    }

}
