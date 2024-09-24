package galena.oreganized.content.fluid;

import galena.oreganized.index.OParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static galena.oreganized.content.block.MoltenLeadBlock.MOVING;

@ParametersAreNonnullByDefault
public class MoltenLeadFluid extends ForgeFlowingFluid {


    public MoltenLeadFluid(Properties properties) {
        super(properties);
        registerDefaultState(defaultFluidState().setValue(LEVEL, 8).setValue(MOVING, false));
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    public int getAmount(FluidState state) {
        return 8;
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL);
        builder.add(MOVING);
    }

    @Override
    protected void spread(Level level, BlockPos pos, FluidState fluidState) {
        if (fluidState.isEmpty()) return;
        BlockState blockstate = level.getBlockState(pos);
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        FluidState fluidstate = getNewLiquid(level, belowPos, belowState);
        if (canSpreadTo(level, pos, blockstate, Direction.DOWN, belowPos, belowState, level.getFluidState(belowPos), fluidstate.getType())) {
            spreadTo(level, belowPos, belowState, Direction.DOWN, fluidstate);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    public void animateTick(Level world, BlockPos blockPos, FluidState fluidState, RandomSource random) {
        BlockPos abovePos = blockPos.above();
        if (world.getBlockState(abovePos).isAir() && !world.getBlockState(abovePos).isSolidRender(world, abovePos)) {
            if (random.nextInt(300) == 0) {
                world.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    @Override
    @Nullable
    public ParticleOptions getDripParticle() {
        return OParticleTypes.DRIPPING_LEAD.get();
    }
}
