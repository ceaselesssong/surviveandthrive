package galena.oreganized.content.block;

import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LeadTrapdoorBlock extends TrapDoorBlock implements IMeltableBlock, TickingEntityBlock<HeavyDoorBlockEntity>, IHeavyDoor {

    public LeadTrapdoorBlock(Properties properties) {
        super(properties, OBlocks.LEAD_BLOCK_SET);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HeavyDoorBlockEntity(pos, state);
    }

    @Override
    public BlockEntityType<HeavyDoorBlockEntity> getType() {
        return OBlockEntities.HEAVY_DOOR.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return HeavyDoorBlockEntity.getAt(level, pos).map(it -> it.use(state, level, pos, player)).orElse(InteractionResult.PASS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getGoopynessProperty());
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
        scheduleUpdate(level, pos, block);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(POWERED, false);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        scheduleUpdate(level, pos, state.getBlock());
    }

    @Override
    public void sound(@Nullable Player player, Level level, BlockPos pos, boolean open) {
        playSound(player, level, pos, open);
    }
}
