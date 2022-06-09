package me.gleep.oreganized.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class SilverOrnamentBars extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_3;

    protected static final VoxelShape Z_SHAPE = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);
    protected static final VoxelShape X_SHAPE = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    public static final float RANGE = 24.0f;
    boolean isUndeadNearby = false;

    public SilverOrnamentBars(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEVEL, 3).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.Z ? X_SHAPE : Z_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        Direction direction = context.getHorizontalDirection();
        Direction.Axis direction$axis = direction.getAxis();
        return this.defaultBlockState().setValue(FACING, direction)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(LEVEL, 3);
    }



    @Override
    public void animateTick(BlockState state, final Level level, final BlockPos pos, final @NotNull Random random) {
        int dist = 4;

        List<Entity> list = level.getEntities((Entity) null,
                new AABB(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                        pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE), (Entity entity) -> entity instanceof LivingEntity
        );

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = Math.sqrt(living.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()));
                if (((int) Math.ceil(distance / (RANGE / 4))) < 4) {
                    dist = (int) Math.ceil(distance / (RANGE / 4));

                    /*if (dist > 7) {
                        dist = 7;
                    }*/
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }
        connectToBars(state, level, pos);
        level.setBlockAndUpdate(pos, state.setValue(LEVEL, dist - 1));
        level.scheduleTick( pos, state.getBlock(), 1);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LEVEL);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType computationType) {
        return false;
    }

    protected void connectToBars(BlockState state, Level level, BlockPos pos) {
        Direction direction = state.getValue(FACING).getAxis() == Direction.Axis.X ? Direction.SOUTH : Direction.EAST;
        BlockState leftState = level.getBlockState(pos.relative(direction));
        BlockState rightState = level.getBlockState(pos.relative(direction.getOpposite()));
        BooleanProperty left = state.getValue(FACING).getAxis() == Direction.Axis.X ? BlockStateProperties.NORTH : BlockStateProperties.WEST;
        BooleanProperty right = state.getValue(FACING).getAxis() == Direction.Axis.X ? BlockStateProperties.SOUTH : BlockStateProperties.EAST;

        if (leftState.getBlock() instanceof IronBarsBlock || leftState.is(BlockTags.WALLS)) {
            level.setBlockAndUpdate(pos.relative(direction), leftState.setValue(left, true));
        }
        if (rightState.getBlock() instanceof IronBarsBlock || rightState.is(BlockTags.WALLS)) {
            level.setBlockAndUpdate(pos.relative(direction.getOpposite()), rightState.setValue(right, true));
        }
    }
}
