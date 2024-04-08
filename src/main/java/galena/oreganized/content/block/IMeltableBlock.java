package galena.oreganized.content.block;

import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;

/**
 * MeltableBlock check list:
 *  - add goopyness block property
 *  - override `stepOn` and call `hurt`
 *  - override `tick` and call `tickMelting`
 *  - override `neighborChanged` and call `scheduleUpdate`
 *  - override `onPlace` and call `scheduleUpdate`
 */
public interface IMeltableBlock {

    IntegerProperty GOOPYNESS_3 = IntegerProperty.create("goopyness", 0, 2);

    List<BlockPos> OFFSET = List.of(
            new BlockPos(+1, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, +1, 0),
            new BlockPos(0, -1, 0),
            new BlockPos(0, 0, +1),
            new BlockPos(0, 0, -1)
    );

    default IntegerProperty getGoopynessProperty() {
        return GOOPYNESS_3;
    }

    default int getGoopyness(BlockState state) {
        return state.getValue(getGoopynessProperty());
    }

    static int getLightLevel(BlockState state) {
        return switch (state.getValue(GOOPYNESS_3)) {
            case 2 -> 6;
            case 1 -> 3;
            default -> 0;
        };
    }

    default int getInducedGoopyness(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(OTags.Blocks.MELTS_LEAD)) return 2;
        if(state.getBlock() instanceof IMeltableBlock meltable && meltable.getGoopyness(state) == 2) return 1;
        if (state.getLightEmission(world, pos) >= 15) return 1;
        return 0;
    }

    default int getNextGoopyness(BlockState state, Level world, BlockPos pos) {
        var touching = OFFSET.stream()
                .map(pos::offset)
                .map(world::getBlockState)
                .mapToInt(it -> getInducedGoopyness(it, world, pos))
                .max();

        return touching.orElse(0);
    }

    default void tickMelting(BlockState state, Level world, BlockPos pos, RandomSource random) {
        var currentGoopyness = state.getValue(getGoopynessProperty());
        var goopyness = getNextGoopyness(state, world, pos);

        if (currentGoopyness != goopyness) {
            world.setBlockAndUpdate(pos, state.setValue(getGoopynessProperty(), goopyness));
        }

        world.scheduleTick(pos, state.getBlock(), 1);
    }

    default void hurt(BlockState state, Level world, Entity entity) {
        if(getGoopyness(state) < 2) return;
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity le && !EnchantmentHelper.hasFrostWalker(le)) {
            entity.hurt(world.damageSources().hotFloor(), 1.0F);
        }
    }

    default void scheduleUpdate(Level level, BlockPos pos, Block block) {
        level.scheduleTick(pos, block, level.random.nextInt(30, 60));
    }

}
