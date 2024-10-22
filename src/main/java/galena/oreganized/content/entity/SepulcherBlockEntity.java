package galena.oreganized.content.entity;

import galena.oreganized.OreganizedConfig;
import galena.oreganized.content.block.SepulcherBlock;
import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class SepulcherBlockEntity extends BlockEntity implements Ticking, GameEventListener.Holder<SepulcherBlockEntity.DeathListener> {

    private final DeathListener listener;
    private int progress = 0;

    public SepulcherBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.SEPULCHER.get(), pos, state);
        this.listener = new DeathListener();
    }

    private int progressPerStep() {
        return OreganizedConfig.COMMON.sepulcherDuration.get() / SepulcherBlock.ROT_LEVELS;
    }

    @Override
    public void tick(BlockState state, Level level, BlockPos pos) {
        if (level.isClientSide()) return;

        int fillLevel = state.getValue(SepulcherBlock.LEVEL);
        if (fillLevel < SepulcherBlock.MAX_LEVEL) return;
        if (fillLevel == SepulcherBlock.READY) return;

        if (++progress < progressPerStep()) return;

        level.setBlockAndUpdate(pos, state.setValue(SepulcherBlock.LEVEL, fillLevel + 1));
        progress = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("progress");
    }

    @Override
    public DeathListener getListener() {
        return listener;
    }

    public class DeathListener implements GameEventListener {
        private final PositionSource listenerSource;
        private final int listenerRadius;

        public DeathListener() {
            this.listenerSource = new BlockPositionSource(SepulcherBlockEntity.this.getBlockPos());
            this.listenerRadius = GameEvent.ENTITY_DIE.getNotificationRadius();
        }

        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        public int getListenerRadius() {
            return this.listenerRadius;
        }

        public boolean handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context context, Vec3 vec) {
            if (event != GameEvent.ENTITY_DIE) return false;

            var entity = context.sourceEntity();
            if (!entity.getType().is(OTags.Entities.FILLS_SEPULCHER)) return false;

            var state = getBlockState();
            var fillLevel = state.getValue(SepulcherBlock.LEVEL);

            if(fillLevel >= SepulcherBlock.MAX_LEVEL) return false;

            SepulcherBlock.insert(null, state, level, getBlockPos());

            return true;
        }
    }

}
