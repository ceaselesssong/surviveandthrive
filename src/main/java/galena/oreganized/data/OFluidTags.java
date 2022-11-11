package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static galena.oreganized.index.OTags.Fluids.MOLTEN_LEAD;

public class OFluidTags extends FluidTagsProvider {

    public OFluidTags(DataGenerator generator, @Nullable ExistingFileHelper helper) {
        super(generator, Oreganized.MOD_ID, helper);
    }

    @Override
    public @NotNull String getName() {
        return "Oreganized Fluid Tags";
    }

    @Override
    protected void addTags() {
        tag(MOLTEN_LEAD).add(OFluids.MOLTEN_LEAD.get());
    }
}
