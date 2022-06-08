package me.gleep.oreganized.blocks;

import com.google.common.collect.Maps;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class SilverBars extends IronBarsBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_3;

    public SilverBars(Properties properties) {
        super(properties);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, LEVEL, WATERLOGGED);
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

        return this.defaultBlockState().setValue(NORTH, this.attachsTo(northState, northState.isFaceSturdy(level, north, Direction.SOUTH)))
                .setValue(EAST, this.attachsTo(eastState, eastState.isFaceSturdy(level, east, Direction.WEST)))
                .setValue(SOUTH, this.attachsTo(southState, southState.isFaceSturdy(level, south, Direction.NORTH)))
                .setValue(WEST, this.attachsTo(westState, westState.isFaceSturdy(level, west, Direction.EAST)))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(LEVEL, 3);
    }

    @Override
    public void animateTick(BlockState state, final Level level, final BlockPos pos, final @NotNull Random random) {

        // If you can see a better way of doing this, feel free to rewrite it.
        // I may have overcomplicated the math side of things.
        int[] ages = new int[6];
        int finalAge;

        for(int direction = 0; direction <= 5; direction++) {
            if (level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getBlock() == RegistryHandler.SILVER_BLOCK.get()) {
                int silverBlockLevel = level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getValue(SilverBlock.LEVEL);
                ages[direction] = silverBlockLevel <= 1 ? 0 : silverBlockLevel <= 3 ? 1 : silverBlockLevel <= 5 ? 2 : 3;
            } else if(level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getBlock() == RegistryHandler.SILVER_ORNAMENT_BARS.get()) {
                ages[direction] = level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getValue(SilverOrnamentBars.LEVEL);
            } else if (level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getBlock() == RegistryHandler.SILVER_BARS.get()) {
                ages[direction] = level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getValue(SilverBars.LEVEL) <= 2
                        ? level.getBlockState(pos.relative(Direction.from3DDataValue(direction))).getValue(SilverBars.LEVEL) + 1 : 3;
            } else {
                ages[direction] = 3;
            }
        }
        finalAge = Arrays.stream(ages).min().getAsInt();


        level.setBlockAndUpdate(pos, state.setValue(LEVEL, finalAge));
        level.scheduleTick( pos, state.getBlock(), 1);
    }
}
