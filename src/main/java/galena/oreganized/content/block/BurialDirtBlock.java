package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BurialDirtBlock extends Block {
    public BurialDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getBlockState(pos.above()).isAir()) level.setBlockAndUpdate(pos, Blocks.DIRT.withPropertiesOf(state));
        if (level.isNight() && random.nextFloat() < 0.1) {
            spawnZombie(level, pos);
            level.setBlockAndUpdate(pos, Blocks.DIRT.withPropertiesOf(state));
        }
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        spawnZombie(level, pos);
    }

    public void spawnZombie(Level level, BlockPos pos) {
        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie != null) {
            zombie.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(zombie);
            zombie.spawnAnim();
        }
    }
}
