package galena.oreganized.content.entity;

import galena.oreganized.OreganizedConfig;
import galena.oreganized.content.block.SepulcherBlock;
import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OTags;
import galena.oreganized.network.OreganizedNetwork;
import galena.oreganized.network.packet.SepulcherConsumesDeathPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SepulcherBlockEntity extends BlockEntity implements Ticking, Container, GameEventListener.Holder<SepulcherBlockEntity.DeathListener> {

    private final DeathListener listener;
    private LazyOptional<InvWrapper> itemHandler;
    private int progress = 0;

    public SepulcherBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.SEPULCHER.get(), pos, state);
        this.listener = new DeathListener();
        this.itemHandler = createItemHandler();
    }

    private int progressNeeded(int fillLevel) {
        if (fillLevel == SepulcherBlock.MAX_LEVEL) return 20;
        return OreganizedConfig.COMMON.sepulcherDuration.get() / (SepulcherBlock.SEALED_LEVELS - 1);
    }

    @Override
    public void tick(BlockState state, Level level, BlockPos pos) {
        int fillLevel = state.getValue(SepulcherBlock.LEVEL);
        if (fillLevel < SepulcherBlock.MAX_LEVEL) return;
        if (fillLevel == SepulcherBlock.READY) return;

        if (++progress < progressNeeded(fillLevel)) return;

        level.setBlockAndUpdate(pos, state.setValue(SepulcherBlock.LEVEL, fillLevel + 1));
        progress = 0;

        var effectColor = new Color(8889187);
        for (int i = 0; i < 20; i++) {
            var vec = Vec3.atBottomCenterOf(pos).add(level.random.nextDouble() - 0.5, 0.8, level.random.nextDouble() - 0.5);
            level.addParticle(ParticleTypes.ENTITY_EFFECT, vec.x, vec.y, vec.z, effectColor.getRed() / 255D, effectColor.getGreen() / 255D, effectColor.getBlue() / 255D);
        }
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

            if (fillLevel >= SepulcherBlock.MAX_LEVEL) return false;

            SepulcherBlock.insert(null, state, level, getBlockPos(), level.random.nextIntBetweenInclusive(3, 4));

            OreganizedNetwork.CHANNEL.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(vec.x, vec.y, vec.z, 16.0, entity.level().dimension())),
                    new SepulcherConsumesDeathPacket(vec)
            );

            return true;
        }
    }

    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = createItemHandler();
    }

    private LazyOptional<InvWrapper> createItemHandler() {
        return LazyOptional.of(() -> new InvWrapper(this));
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        var fillLevel = getBlockState().getValue(SepulcherBlock.LEVEL);
        return fillLevel < SepulcherBlock.READY;
    }

    @Override
    public ItemStack getItem(int slot) {
        var fillLevel = getBlockState().getValue(SepulcherBlock.LEVEL);
        if (fillLevel == SepulcherBlock.READY) return new ItemStack(OBlocks.BONE_PILE.get());
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        var stack = getItem(slot);
        if (!stack.isEmpty()) SepulcherBlock.clear(null, getBlockState(), getLevel(), getBlockPos());
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return removeItem(slot, 1);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return SepulcherBlock.tryInsert(stack, null, getBlockState(), getLevel(), getBlockPos(), true);
    }

    @Override
    public boolean canTakeItem(Container container, int slot, ItemStack stack) {
        return getBlockState().getValue(SepulcherBlock.LEVEL) == SepulcherBlock.READY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        SepulcherBlock.tryInsert(stack, null, getBlockState(), getLevel(), getBlockPos(), false);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        SepulcherBlock.clear(null, getBlockState(), getLevel(), getBlockPos());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER && facing != null && !this.remove) {
            return itemHandler.cast();
        } else {
            return super.getCapability(capability, facing);
        }
    }
}
