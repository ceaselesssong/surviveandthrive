package galena.oreganized;

import galena.oreganized.index.OEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
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
        public final ConfigValue<Integer> leadClusterSize;
        public final ConfigValue<Integer> leadFrequency;
        public final ConfigValue<Integer> leadMinHeight;
        public final ConfigValue<Integer> leadMaxHeight;
        public final ConfigValue<Boolean> stunningOrPoison;

        public final ConfigValue<Integer> silverClusterSize;
        public final ConfigValue<Integer> silverFrequency;
        public final ConfigValue<Integer> silverMinHeight;
        public final ConfigValue<Integer> silverMaxHeight;

        public final ConfigValue<Boolean> ravagerSilver;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common");
            builder.push("common");
            builder.comment("Lead");
            builder.push("lead");
            leadClusterSize = builder.comment("The average cluster size lead ore generates in").define("leadClusterSize", 6);
            leadFrequency = builder.comment("How frequent lead ore generates").define("leadFrequency", 6);
            leadMinHeight = builder.comment("Minimum y level that lead ore can generate").define("leadMinHeight", -40);
            leadMaxHeight = builder.comment("Maximum y level that lead ore can generate").define("leadMaxHeight", -20);
            stunningOrPoison = builder.comment("Should lead poisoning events give the Stunning effect or just Poison").define("leadPoisoningStunning", true);
            builder.comment("Silver");
            builder.push("silver");
            silverClusterSize = builder.comment("The average cluster size silver ore generates in").define("silverClusterSize", 6);
            silverFrequency = builder.comment("How frequent silver ore generates").define("silverFrequency", 6);
            silverMinHeight = builder.comment("Minimum y level that silver ore can generate").define("silverMinHeight", -15);
            silverMaxHeight = builder.comment("Maximum y level that silver ore can generate").define("silverMaxHeight", 5);
            ravagerSilver = builder.comment("Ravagers have a chance of dropping Silver Nuggets upon death").define("ravagerSilver", false);
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

    public static MobEffect StunningOrPoison() {
        return COMMON.stunningOrPoison.get() ? OEffects.STUNNING.get() : MobEffects.POISON;
    }

    public static boolean stunningFromConfig() {
        return COMMON.stunningOrPoison.get();
    }
}
