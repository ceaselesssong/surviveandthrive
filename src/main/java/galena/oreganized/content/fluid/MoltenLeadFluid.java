package galena.oreganized.content.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class MoltenLeadFluid extends ForgeFlowingFluid {

    public MoltenLeadFluid(Properties properties) {
        super(properties);
    }

    @Override
    protected void animateTick(Level world, BlockPos pos, FluidState state, RandomSource rand) {
        if (rand.nextInt(200) == 0) {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    @Override
    public int getTickDelay(LevelReader world) {
        return 5;
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    public int getAmount(FluidState state) {
        return 8;
    }

    public boolean isSource(FluidState state) {
        return true;
    }
}
