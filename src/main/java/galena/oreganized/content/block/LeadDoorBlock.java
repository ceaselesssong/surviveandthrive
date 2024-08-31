package galena.oreganized.content.block;

import galena.oreganized.index.OBlocks;
import galena.oreganized.world.IDoorProgressHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;

public class LeadDoorBlock extends DoorBlock implements IMeltableBlock {

    public LeadDoorBlock(Properties properties) {
        super(properties, OBlocks.LEAD_BLOCK_SET);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var progressHolder = (IDoorProgressHolder) player;

        if (progressHolder.oreganised$incrementOpeningProgress() > 9) {
            progressHolder.oreganised$resetOpeningProgress();
            return super.use(state, level, pos, player, hand, hit);
        } else {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getGoopynessProperty());
    }

    /*
    @Override
    public boolean onGoopynessChange(Level world, BlockState state, BlockPos pos, RandomSource random, int from, int to) {
        var half = state.getValue(HALF);
        var otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        var otherState = world.getBlockState(otherPos);

        if (otherState.is(this) && getGoopyness(otherState) != to) {
            world.setBlockAndUpdate(otherPos, otherState.setValue(getGoopynessProperty(), to));
        }

        return IMeltableBlock.super.onGoopynessChange(world, state, pos, random, from, to);
    }
     */

    @Override
    public int getNextGoopyness(BlockGetter world, BlockState selfState, BlockPos selfPos) {
        var self = goopynessAt(world, selfState, selfPos);

        var half = selfState.getValue(HALF);
        var otherPos = half == DoubleBlockHalf.LOWER ? selfPos.above() : selfPos.below();
        var otherState = world.getBlockState(otherPos);
        var other = goopynessAt(world, otherState, otherPos);

        return Math.max(self, other);
    }

    @Override
    public int getInducedGoopyness(BlockGetter world, BlockState state, BlockPos pos, BlockState selfState, BlockPos selfPos) {
        if (state.is(this)) {
            var selfHalf = selfState.getValue(HALF);
            if (selfHalf == DoubleBlockHalf.UPPER && pos.getY() < selfPos.getY()
                    || selfHalf == DoubleBlockHalf.LOWER && pos.getY() > selfPos.getY()
            ) {
                return 0;
                //return getGoopyness(state);
            }
        }
        return IMeltableBlock.super.getInducedGoopyness(world, state, pos, selfState, selfPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        tickMelting(state, world, pos, random);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        hurt(state, world, entity);
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        scheduleUpdate(level, pos, block);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        scheduleUpdate(level, pos, state.getBlock());
    }
}
