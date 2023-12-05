package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import static galena.oreganized.content.block.MeltableBlock.GOOPYNESS;

public class MeltablePillarBlock extends RotatedPillarBlock {

    public MeltablePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(GOOPYNESS, 0));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
        super.createBlockStateDefinition(definition);
        definition.add(GOOPYNESS);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        world.scheduleTick(pos, state.getBlock(), 1);
    }
}
