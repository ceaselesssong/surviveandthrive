package galena.oreganized.content.block;

import galena.oreganized.content.ISilver;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MeltableBlock extends Block {
    public static final IntegerProperty GOOPYNESS = IntegerProperty.create("goopyness", 0, 2);

    public MeltableBlock(Properties properties) {
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
