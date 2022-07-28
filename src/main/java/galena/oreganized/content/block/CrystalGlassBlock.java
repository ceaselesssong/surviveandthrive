package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class CrystalGlassBlock extends AbstractGlassBlock implements ICrystalGlass {

    private final DyeColor color;
    public static final int NORMAL = 0, ROTATED = 1, INNER = 2, OUTER = 3;
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);

    public CrystalGlassBlock(DyeColor color, BlockBehaviour.Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(TYPE, context.getPlayer().isCrouching() ? ROTATED : NORMAL);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState adjState, LevelAccessor world, BlockPos pos, BlockPos adjPos) {
        Block centerBlock = state.getBlock();
        Block aboveBlock = world.getBlockState(pos.above()).getBlock();
        Block twoAboveBlock = world.getBlockState(pos.above(2)).getBlock();
        Block belowBlock = world.getBlockState(pos.below()).getBlock();
        Block twoBelowBlock = world.getBlockState(pos.below(2)).getBlock();

        if (direction == Direction.DOWN || direction == Direction.UP) {
            if (centerBlock == aboveBlock && centerBlock == belowBlock) {
                updatePattern(pos, world);
            } else if (centerBlock == twoAboveBlock && centerBlock == aboveBlock && centerBlock != belowBlock) {
                updatePattern(pos.above(), world);
            } else if (centerBlock == twoBelowBlock && centerBlock == belowBlock && centerBlock != aboveBlock) {
                updatePattern(pos.below(), world);
            }
            if (state.getValue(TYPE) == OUTER || state.getValue(TYPE) == INNER) {
                if (centerBlock != adjState.getBlock()) {
                    return state.setValue(TYPE, ROTATED);
                }
            }
        }
        return super.updateShape(state, direction, adjState, world, pos, adjPos);
    }

    private void updatePattern(BlockPos centerPos, LevelAccessor world) {
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
}
