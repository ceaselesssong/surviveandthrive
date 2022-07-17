package galena.oreganized.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public interface ICrystalGlass extends BeaconBeamBlock {
    int NORMAL = 0, ROTATED = 1, INNER = 2, OUTER = 3;
    IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);

    //TODO: Add default functions for crystal glass behavior for both block and pane variants to use instead of just copy and pasting them across.
}
