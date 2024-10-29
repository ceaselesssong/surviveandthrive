package galena.doom_and_gloom;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = DoomAndGloom.MOD_ID)
public class DGConfig {
    public static final Common COMMON;
    private static final ForgeConfigSpec COMMON_SPEC;

    public static class Common {
        public final ConfigValue<Integer> sepulcherDuration;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common");
            builder.push("common");

            sepulcherDuration = builder.comment("Time in ticks the sepulcher takes to turn meat into bones").defineInRange("sepulcherDuration", 20 * 30, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);

        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }

    public static void register() {
        var context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }

}
