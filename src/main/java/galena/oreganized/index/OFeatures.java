package galena.oreganized.index;

import com.google.common.collect.ImmutableList;
import galena.oreganized.Oreganized;
import galena.oreganized.OreganizedConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class OFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Oreganized.MOD_ID);

    public static final RegistryObject<Feature<OreConfiguration>> SILVER_ORE_LOW = FEATURES.register("silver_ore", () -> new OreFeature(OreConfiguration.CODEC));
    public static final RegistryObject<Feature<OreConfiguration>> SILVER_ORE_HIGH = FEATURES.register("silver_ore_high", () -> new OreFeature(OreConfiguration.CODEC));
    public static final RegistryObject<Feature<OreConfiguration>> LEAD_ORE = FEATURES.register("lead_ore", () -> new OreFeature(OreConfiguration.CODEC));
    public static final RegistryObject<Feature<OreConfiguration>> LEAD_ORE_EXTRA = FEATURES.register("lead_ore_extra", () -> new OreFeature(OreConfiguration.CODEC));

    public static final class Configured {

        public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Oreganized.MOD_ID);

        public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> SILVER_ORE_LOW = registerOre("silver_ore", OBlocks.SILVER_ORE, OBlocks.DEEPSLATE_SILVER_ORE, 3, 0.8F - Mth.clamp(OreganizedConfig.COMMON.silverHidden.get(), 0F, 0.8F));
        public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> SILVER_ORE_HIGH = registerOre("silver_ore_high", OBlocks.SILVER_ORE, OBlocks.DEEPSLATE_SILVER_ORE, 2, 1F - Mth.clamp(OreganizedConfig.COMMON.silverHidden.get(), 0F, 1F));
        public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> LEAD_ORE = registerOre("lead_ore", OBlocks.LEAD_ORE, OBlocks.DEEPSLATE_LEAD_ORE, OreganizedConfig.COMMON.leadClusterSize.get() / 2 + 1);
        public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> LEAD_ORE_EXTRA = registerOre("lead_ore_extra", OBlocks.LEAD_ORE, OBlocks.DEEPSLATE_LEAD_ORE, OreganizedConfig.COMMON.leadClusterSize.get());

        private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
            return CONFIGURED_FEATURES.register(name, feature);
        }

        private static RegistryObject<ConfiguredFeature<OreConfiguration, ?>> registerOre(String name, Supplier<? extends Block> stoneOre, Supplier<? extends Block> deepslateOre, int size, float discardChanceOnAirExposure) {
            return register(name, () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, stoneOre.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), size, discardChanceOnAirExposure)));
        }

        private static RegistryObject<ConfiguredFeature<OreConfiguration, ?>> registerOre(String name, Supplier<? extends Block> stoneOre, Supplier<? extends Block> deepslateOre, int size) {
            return registerOre(name, stoneOre, deepslateOre, size, 0.0F);
        }
    }

    public static final class Placed {
        public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Oreganized.MOD_ID);

        public static final RegistryObject<PlacedFeature> SILVER_ORE_LOW = register("silver_ore", Configured.SILVER_ORE_LOW,
                CountPlacement.of(OreganizedConfig.COMMON.silverFrequency.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(OreganizedConfig.COMMON.silverMinHeight.get()), VerticalAnchor.absolute(OreganizedConfig.COMMON.silverMaxHeight.get()))
        );
        public static final RegistryObject<PlacedFeature> SILVER_ORE_HIGH = register("silver_ore_high", Configured.SILVER_ORE_HIGH,
                CountPlacement.of(OreganizedConfig.COMMON.silverFrequency.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(140), VerticalAnchor.absolute(160))
        );
        public static final RegistryObject<PlacedFeature> LEAD_ORE = register("lead_ore", Configured.LEAD_ORE,
                CountPlacement.of(OreganizedConfig.COMMON.leadFrequency.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(OreganizedConfig.COMMON.leadMinHeight.get()), VerticalAnchor.absolute(OreganizedConfig.COMMON.leadMaxHeight.get()))
        );
        public static final RegistryObject<PlacedFeature> LEAD_ORE_EXTRA = register("lead_ore_extra", Configured.LEAD_ORE_EXTRA,
                CountPlacement.of(OreganizedConfig.COMMON.leadFrequency.get()),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(80))
        );

        private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
            return register(name, feature, List.of(placementModifiers));
        }

        @SuppressWarnings("unchecked")
        private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placementModifiers) {
            return PLACED_FEATURES.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) feature.getHolder().get(), ImmutableList.copyOf(placementModifiers)));
        }
    }
}
