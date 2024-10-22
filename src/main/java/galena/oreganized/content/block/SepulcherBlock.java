package galena.oreganized.content.block;

import galena.oreganized.content.entity.SepulcherBlockEntity;
import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SepulcherBlock extends Block implements TickingEntityBlock<SepulcherBlockEntity> {

    public static final int MAX_LEVEL = 6;
    public static final int ROT_LEVELS = 4;
    public static final int READY = MAX_LEVEL + ROT_LEVELS;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, READY);

    public SepulcherBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LEVEL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int fillLevel = state.getValue(LEVEL);

        if (fillLevel == READY) {
            extract(player, state, level, pos);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        var held = player.getItemInHand(hand);
        var food = held.getFoodProperties(null);

        if (fillLevel < MAX_LEVEL && food != null && food.isMeat()) {
            insert(player, state, level, pos);
            player.awardStat(Stats.ITEM_USED.get(held.getItem()));
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    public static void insert(@Nullable Entity user, BlockState state, LevelAccessor level, BlockPos pos) {
        var newState = state.setValue(LEVEL, state.getValue(LEVEL) + 1);
        level.setBlock(pos, newState, 3);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(user, newState));
    }

    public static void extract(Entity user, BlockState state, Level level, BlockPos pos) {
        if (!level.isClientSide()) {
            Vec3 vec = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
            ItemEntity item = new ItemEntity(level, vec.x(), vec.y(), vec.z(), new ItemStack(OBlocks.BONE_PILE.get()));
            item.setDefaultPickUpDelay();
            level.addFreshEntity(item);
        }

        var empty = state.setValue(LEVEL, 0);
        level.setBlockAndUpdate(pos, empty);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(user, empty));

        level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SepulcherBlockEntity(pos, state);
    }

    @Override
    public BlockEntityType<SepulcherBlockEntity> getType() {
        return OBlockEntities.SEPULCHER.get();
    }
}
