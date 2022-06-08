package me.gleep.oreganized.blocks;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

public class LeadBars extends IronBarsBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty SNEAKED = BooleanProperty.create("sneaked");

    public LeadBars(Properties properties) {
        super(properties);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, SNEAKED, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final BlockGetter level = context.getLevel();
        final BlockPos pos = context.getClickedPos();
        final FluidState fluidState = level.getFluidState(pos);
        final BlockPos north = pos.north();
        final BlockPos east = pos.east();
        final BlockPos south = pos.south();
        final BlockPos west = pos.west();
        final BlockState northState = level.getBlockState(north);
        final BlockState eastState = level.getBlockState(east);
        final BlockState southState = level.getBlockState(south);
        final BlockState westState = level.getBlockState(west);
        final Boolean crouching = context.getPlayer().isCrouching();

        return this.defaultBlockState().setValue(NORTH, this.attachsTo(northState, northState.isFaceSturdy(level, north, Direction.SOUTH)))
                .setValue(EAST, this.attachsTo(eastState, eastState.isFaceSturdy(level, east, Direction.WEST)))
                .setValue(SOUTH, this.attachsTo(southState, southState.isFaceSturdy(level, south, Direction.NORTH)))
                .setValue(WEST, this.attachsTo(westState, westState.isFaceSturdy(level, west, Direction.EAST)))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(SNEAKED, crouching);
    }
}
