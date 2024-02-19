package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.ToIntFunction;

public class MeltableBlock extends Block implements IMeltableBlock {

    public static final ToIntFunction<BlockState> LIGHT_BY_GOOPYNESS = state -> {
        var level = state.getValue(GOOPYNESS);
        if(level == 2) return 13;
    }

    public MeltableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(GOOPYNESS, 0));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
        super.createBlockStateDefinition(definition);
        definition.add(GOOPYNESS);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        tickMelting(state, world, pos, random);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if(getGoopyness(state) < 2) return;
        hurt(world, entity);
        super.stepOn(world, pos, state, entity);
    }
}
