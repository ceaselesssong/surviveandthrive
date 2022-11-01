package galena.oreganized.content.block;

import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpottedGlanceBlock extends Block {

    public SpottedGlanceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState adjState, LevelAccessor world, BlockPos pos, BlockPos adjPos) {
        if (!world.isWaterAt(adjPos)) return super.updateShape(state, direction, adjState, world, pos, adjPos);

        dropLeadNuggets(world, pos);

        return OBlocks.GLANCE.get().defaultBlockState();
    }

    private void dropLeadNuggets(LevelAccessor world, BlockPos pos) {
        ItemStack leadNuggets = new ItemStack(OItems.LEAD_NUGGET.get(), world.getRandom().nextInt(2) + 1);
        Containers.dropItemStack((Level) world, pos.getX(), pos.getY(), pos.getZ(), leadNuggets);
    }
}
