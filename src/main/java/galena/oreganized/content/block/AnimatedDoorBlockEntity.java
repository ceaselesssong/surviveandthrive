package galena.oreganized.content.block;

import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OBlocks;
import galena.oreganized.world.IDoorProgressHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static galena.oreganized.content.block.LeadDoorBlock.ANIMATED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN;

public class AnimatedDoorBlockEntity extends BlockEntity {

    private int pressure = 0;

    private final BlockSetType set = OBlocks.LEAD_BLOCK_SET;

    private static final int REQUIRED_PRESSURE_OPEN = 60;

    public AnimatedDoorBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.ANIMATED_DOOR.get(), pos, state);
    }

    public static Optional<AnimatedDoorBlockEntity> getAt(LevelAccessor level, BlockPos pos) {
        var be = level.getBlockEntity(pos);
        if (be instanceof AnimatedDoorBlockEntity door) return Optional.of(door);
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type == OBlockEntities.ANIMATED_DOOR.get()) {
            BlockEntityTicker<AnimatedDoorBlockEntity> ticker = (l, p, s, be) -> be.tick(s, l, p);
            return (BlockEntityTicker<T>) ticker;
        }

        return null;
    }

    public void tick(BlockState state, Level level, BlockPos pos) {
        if (pressure <= 0) return;

        pressure--;

        if (pressure == 0) {
            if (state.getValue(OPEN)) {
                state = state.setValue(OPEN, false);
                level.setBlock(pos, state, 10);
                level.gameEvent(GameEvent.BLOCK_CLOSE, pos, GameEvent.Context.of(state));
                if(state.getBlock() instanceof IHeavyDoor heavy) heavy.sound(null, level, pos, false);
            }

            stopUsing(state, level, pos, null);
        } else if (pressure < (REQUIRED_PRESSURE_OPEN - 5)) {
            setAnimationState(level, pos, state, true);
        }
    }

    private void setAnimationState(Level level, BlockPos pos, BlockState state, boolean animationState) {
        if (state.hasProperty(ANIMATED) && state.getValue(ANIMATED) != animationState) {
            level.setBlockAndUpdate(pos, state.setValue(ANIMATED, animationState));
        }
    }

    private void stopUsing(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        setAnimationState(level, pos, state, false);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player) {
        var progressHolder = (IDoorProgressHolder) player;
        progressHolder.oreganised$incrementOpeningProgress();

        if (pressure == 0) {
            setAnimationState(level, pos, state, true);
        }

        if (!level.isClientSide) System.out.println(pressure);

        if (pressure < REQUIRED_PRESSURE_OPEN) {
            pressure += 10;
        }

        if (pressure > REQUIRED_PRESSURE_OPEN && !state.getValue(OPEN)) {
            state = state.setValue(OPEN, true);
            level.setBlock(pos, state, 10);
            level.gameEvent(GameEvent.BLOCK_OPEN, pos, GameEvent.Context.of(state));
            if(state.getBlock() instanceof IHeavyDoor heavy) heavy.sound(player, level, pos, true);
            stopUsing(state, level, pos, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
