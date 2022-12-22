package galena.oreganized.integration;

import galena.oreganized.integration.quark.QCompatRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CompatHandlerClient {

    public static void setup(final FMLClientSetupEvent event) {
        if (CompatHandler.quark) QCompatRegistry.clientSetup();
    }
}
