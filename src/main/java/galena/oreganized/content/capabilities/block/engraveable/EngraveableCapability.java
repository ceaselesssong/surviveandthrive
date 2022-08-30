package galena.oreganized.content.capabilities.block.engraveable;

import galena.oreganized.Oreganized;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class EngraveableCapability {

    protected static final Capability<IEngraveableBlock> ENGRAVEABLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<IEngraveableBlock>() {});
    protected static final ResourceLocation CAP_LOCATION = new ResourceLocation(Oreganized.MOD_ID, "engraveable");

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IEngraveableBlock.class);
    }
}
