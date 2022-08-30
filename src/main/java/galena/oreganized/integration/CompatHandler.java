package galena.oreganized.integration;

import galena.oreganized.integration.farmersdelight.FDCompatRegistry;
import galena.oreganized.integration.tconstruct.TCCompatRegistry;
import net.minecraftforge.fml.ModList;

public class CompatHandler {

    public static final boolean farmersDelight;
    public static final boolean tinkersConstruct;

    static {
        ModList mods = ModList.get();

        farmersDelight = mods.isLoaded("farmersdelight");
        tinkersConstruct = mods.isLoaded("tconstruct");
    }

    public static void init() {

    }

    public static void register() {
        if (farmersDelight) FDCompatRegistry.register();
        if (tinkersConstruct) TCCompatRegistry.register();
    }
}
