package galena.oreganized.content.entity;

import galena.oreganized.content.block.SepulcherBlock;
import galena.oreganized.index.OBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SepulcherBlockEntity extends BlockEntity implements Ticking {

    private int progress = 0;

    public SepulcherBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.SEPULCHER.get(), pos, state);
    }

    @Override
    public void tick(BlockState state, Level level, BlockPos pos) {
        if (state.getValue(SepulcherBlock.LEVEL) < SepulcherBlock.MAX_LEVEL) return;

        if (--progress > 0) return;

        level.setBlockAndUpdate(pos, state.setValue(SepulcherBlock.LEVEL, SepulcherBlock.BONES_LEVEL));
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

}
