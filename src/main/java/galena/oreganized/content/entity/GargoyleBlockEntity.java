package galena.oreganized.content.entity;

import galena.oreganized.index.OBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GargoyleBlockEntity extends BlockEntity {

    public GargoyleBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.GARGOYLE.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level world, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity.getLevel() == null) return;
        if (blockEntity.getLevel().isClientSide()) return;
        state.getBlock().tick(blockEntity.getBlockState(), (ServerLevel) blockEntity.getLevel(), blockEntity.getBlockPos(), blockEntity.getLevel().getRandom());
    }
}
