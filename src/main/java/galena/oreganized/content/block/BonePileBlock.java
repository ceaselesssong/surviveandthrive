package galena.oreganized.content.block;

import galena.oreganized.index.OParticleTypes;
import galena.oreganized.index.OSoundEvents;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

import java.util.function.Consumer;

public class BonePileBlock extends FallingBlock {

    public BonePileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState other, FallingBlockEntity entity) {
        super.onLand(level, pos, state, other, entity);
        if (!entity.isSilent()) level.playSound(null, pos, OSoundEvents.BONE_PILE_FALL.get(), SoundSource.BLOCKS, 1F, 1F);
        particles(level, Vec3.atCenterOf(pos), 10);
    }

    @Override
    public boolean addLandingEffects(BlockState state, ServerLevel level, BlockPos pos, BlockState other, LivingEntity entity, int numberOfParticles) {
        particles(level, entity.position(), numberOfParticles / 2);
        return true;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        var vec = entity.position();
        level.addParticle(OParticleTypes.BONE_FRAGMENT.get(), vec.x, vec.y, vec.z, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5);
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ClientProperties());
    }

    private void particles(Level level, Vec3 vec, int numberOfParticles) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(OParticleTypes.BONE_FRAGMENT.get(), vec.x, vec.y, vec.z, numberOfParticles, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5, 0.0);
        } else for (int i = 0; i < numberOfParticles; i++) {
            level.addParticle(OParticleTypes.BONE_FRAGMENT.get(),
                    vec.x + level.random.nextDouble() - 0.5, vec.y + level.random.nextDouble() - 0.5, vec.z + level.random.nextDouble() - 0.5,
                    level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5, level.random.nextDouble() - 0.5
            );
        }
    }

    public class ClientProperties implements IClientBlockExtensions {

        @Override
        public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
            particles(level, Vec3.atCenterOf(pos), 10);
            return IClientBlockExtensions.super.addDestroyEffects(state, level, pos, manager);
        }

    }

}
