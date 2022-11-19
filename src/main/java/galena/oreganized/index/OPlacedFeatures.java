package galena.oreganized.index;

import galena.oreganized.Oreganized;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class OPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Oreganized.MOD_ID);

    // Ores
    public static final RegistryObject<PlacedFeature> LEAD_ORE = PLACED_FEATURES.register("lead_ore", () -> new PlacedFeature(OConfiguredFeatures.LEAD_ORE.getHolder().get(), OrePlacements.commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(-20)))));
    public static final RegistryObject<PlacedFeature> LEAD_ORE_EXTRA = PLACED_FEATURES.register("lead_ore_extra", () -> new PlacedFeature(OConfiguredFeatures.LEAD_ORE.getHolder().get(), OrePlacements.commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.absolute(50), VerticalAnchor.absolute(80)))));

    public static final RegistryObject<PlacedFeature> SILVER_ORE = PLACED_FEATURES.register("silver_ore", () -> new PlacedFeature(OConfiguredFeatures.SILVER_ORE.getHolder().get(), OrePlacements.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(5)))));
    public static final RegistryObject<PlacedFeature> SILVER_ORE_EXTRA = PLACED_FEATURES.register("silver_ore_extra", () -> new PlacedFeature(OConfiguredFeatures.SILVER_ORE.getHolder().get(), OrePlacements.commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(140), VerticalAnchor.absolute(180)))));
}
