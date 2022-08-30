package galena.oreganized.content.capabilities.block.engraveable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EngraveableProvider implements ICapabilitySerializable<CompoundTag> {

    private final Capability<IEngraveableBlock> capability = EngraveableCapability.ENGRAVEABLE_CAPABILITY;
    private final EngraveableBlock instance = new EngraveableBlock();
    private final LazyOptional<EngraveableBlock> implementation = LazyOptional.of(() -> instance);

    public void invalidate() {
        implementation.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == capability ? implementation.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }
}
