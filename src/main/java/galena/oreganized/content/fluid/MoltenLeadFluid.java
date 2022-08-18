package galena.oreganized.content.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class MoltenLeadFluid  extends ForgeFlowingFluid {

    public MoltenLeadFluid(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(LEVEL, 8));
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL);
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
    protected void spreadTo(LevelAccessor p_76220_, BlockPos p_76221_, BlockState p_76222_, Direction p_76223_, FluidState p_76224_) {
        // We don't want to spread >:(
    }

    @Override
    public boolean canBeReplacedWith(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
        return false;
    }
}
