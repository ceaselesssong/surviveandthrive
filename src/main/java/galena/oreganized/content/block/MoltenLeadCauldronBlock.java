package galena.oreganized.content.block;

import galena.oreganized.content.index.OCauldronInteractions;
import galena.oreganized.content.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;

public class MoltenLeadCauldronBlock extends AbstractCauldronBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public MoltenLeadCauldronBlock(BlockBehaviour.Properties properties) {
        super(properties, OCauldronInteractions.LEAD);
        registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    protected double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    public static ToIntFunction<BlockState> moltenStageEmission() {
        return (state) -> {
            return state.getValue(AGE) * 2;
        };
    }

    public boolean isFull(BlockState state) {
        return true;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        if (!state.getValue(AGE).equals(3)) return Shapes.block();
        return super.getShape(state, world, pos, ctx);
    }

    public void entityInside(BlockState state, Level level, BlockPos blockPos, Entity entity) {
        if (this.isEntityInsideContent(state, blockPos, entity)) {
            entity.setSecondsOnFire(10);
        }
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        return 3;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 3 && world.isAreaLoaded(pos, 4)) {
            BlockState below = world.getBlockState(pos.below());
            if (below.is(OTags.Blocks.FIRE_SOURCE) || below.getFluidState().is(FluidTags.LAVA)) {
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
            } else {
                world.setBlock(pos, state.setValue(AGE, 1), 2);
            }
        }
    }
}
