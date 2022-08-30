package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface ICrystalGlass extends BeaconBeamBlock {

    int NORMAL = 0, ROTATED = 1, INNER = 2, OUTER = 3;
    IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);

    default void updatePattern(BlockPos centerPos, LevelAccessor world) {
        BlockState aboveState = world.getBlockState(centerPos.above());
        BlockState centerState = world.getBlockState(centerPos);
        BlockState belowState = world.getBlockState(centerPos.below());

        if (centerState.getBlock() == aboveState.getBlock() && centerState.getBlock() == belowState.getBlock()) {
            if (aboveState.getValue(TYPE) == ROTATED && centerState.getValue(TYPE) == ROTATED && belowState.getValue(TYPE) == NORMAL) {
                world.setBlock(centerPos, centerState.setValue(TYPE, OUTER), 3);
            } else if (aboveState.getValue(TYPE) == NORMAL && centerState.getValue(TYPE) == NORMAL && belowState.getValue(TYPE) == ROTATED) {
                world.setBlock(centerPos, centerState.setValue(TYPE, INNER), 3);
            }
        }
    }

    //TODO: Add default functions for crystal glass behavior for both block and pane variants to use instead of just copy and pasting them across.
}
