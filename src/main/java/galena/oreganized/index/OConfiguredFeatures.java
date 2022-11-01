package galena.oreganized.index;

import galena.oreganized.Oreganized;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class OConfiguredFeatures {

    public static void register() {

    }

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SILVER_ORE_LOW = registerConfiguredFeature("silver_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), OBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), OBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 6));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfiguration) {
        ResourceLocation modLoc = new ResourceLocation(Oreganized.MOD_ID, id);

        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(modLoc))
            throw new IllegalStateException("Placed Feature ID: \"" + modLoc + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, modLoc.toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }
}
