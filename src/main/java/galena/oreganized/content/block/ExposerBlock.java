package galena.oreganized.content.block;

import galena.oreganized.content.ISilver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ExposerBlock extends DirectionalBlock implements ISilver {

    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_7;
    public static final int TexturedFrames = 4;


    public ExposerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LEVEL);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int dist = getUndeadDistance(world, pos, null, TexturedFrames);
        world.setBlockAndUpdate(pos, state.setValue(LEVEL, dist - 1));
        world.scheduleTick(pos, state.getBlock(), 1);
        this.updateNeighborsInFront(world, pos, state);
    }

    protected void updateNeighborsInFront(Level worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite().getNormal());
        worldIn.neighborChanged(blockpos, this, pos);
        worldIn.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
    }

    public boolean isSignalSource(BlockState p_55138_) {
        return true;
    }

}
