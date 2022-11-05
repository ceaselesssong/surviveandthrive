package galena.oreganized;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Oreganized.MOD_ID)
public class OreganizedConfig {
    public static final Common COMMON;
    public static final Client CLIENT;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static class Common {
        public final ConfigValue<Boolean> engraving;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common");
            builder.push("common");
            this.engraving = builder.comment("Disables the engraving feature, can be useful on servers who struggle with griefers.").define("Engraving", true);
            builder.pop();
        }
    }

    public static class Client {

        public Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client");
            builder.push("client");
            builder.pop();
        }
    }

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        final Pair<Client, ForgeConfigSpec> cleintSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);

        COMMON = commonSpecPair.getLeft();
        CLIENT = cleintSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        CLIENT_SPEC = cleintSpecPair.getRight();
    }
}
