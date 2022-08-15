package galena.oreganized.integration;

import galena.oreganized.integration.farmersdelight.FDCompatRegistry;
import net.minecraftforge.fml.ModList;

public class CompatHandler {

    public static final boolean farmersDelight;

    static {
        ModList mods = ModList.get();

        farmersDelight = mods.isLoaded("farmersdelight");
    }

    public static void init() {

    }

    public static void register() {
        if (farmersDelight) FDCompatRegistry.register();
    }
}
