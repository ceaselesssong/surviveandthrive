package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

    private EntityType<? extends Mob> randomMonster(RandomSource random) {
        if(random.nextDouble() < 0.3) return EntityType.SKELETON;
        return EntityType.ZOMBIE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getBlockState(pos.above()).isAir()) level.setBlockAndUpdate(pos, Blocks.DIRT.withPropertiesOf(state));
        if (level.isNight() && random.nextFloat() < 0.1) {
            spawnMonster(level, pos);
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
        }
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        spawnMonster(level, pos);
    }

    public void spawnMonster(Level level, BlockPos pos) {
        var monster = randomMonster(level.random).create(level);
        if (monster != null) {
            monster.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(monster);
            monster.spawnAnim();
        }
    }
}
