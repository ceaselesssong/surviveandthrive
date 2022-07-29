package galena.oreganized.content.index;

import galena.oreganized.Oreganized;
import galena.oreganized.content.fluid.MoltenLeadFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class OFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Oreganized.MOD_ID);
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Oreganized.MOD_ID);

    public static final RegistryObject<FluidType> MOLTEN_LEAD_TYPE = TYPES.register("molten_lead", () -> new FluidType(FluidType.Properties.create().lightLevel(8).density(1500).temperature(600).viscosity(3000).motionScale(0.007D).canExtinguish(false)) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation(Oreganized.MOD_ID, "fluid/molten_lead");
                }
            });
        }
    });

    public static final RegistryObject<FlowingFluid> MOLTEN_LEAD = FLUIDS.register("molten_lead", () -> new MoltenLeadFluid(OFluids.MOLTEN_LEAD_PROPERTIES));

    public static final MoltenLeadFluid.Properties MOLTEN_LEAD_PROPERTIES = new MoltenLeadFluid.Properties(MOLTEN_LEAD_TYPE, MOLTEN_LEAD, MOLTEN_LEAD).bucket(OItems.MOLTEN_LEAD_BUCKET).block(OBlocks.MOLTEN_LEAD);
}
