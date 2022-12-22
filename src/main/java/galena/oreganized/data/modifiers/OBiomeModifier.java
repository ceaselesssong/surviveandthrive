package galena.oreganized.data.modifiers;

import com.mojang.serialization.JsonOps;
import galena.oreganized.Oreganized;
import galena.oreganized.index.OFeatures;
import galena.oreganized.index.OTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OBiomeModifier {
    private static final RegistryAccess access = RegistryAccess.builtinCopy();
    private static final Registry<Biome> biomeRegistry = access.registryOrThrow(Registry.BIOME_REGISTRY);
    private static final Registry<PlacedFeature> placedFeatures = access.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
    private static final HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

    public static JsonCodecProvider<BiomeModifier> register(GatherDataEvent event) {
        addFeatures();
        return JsonCodecProvider.forDatapackRegistry(event.getGenerator(), event.getExistingFileHelper(), Oreganized.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, access), ForgeRegistries.Keys.BIOME_MODIFIERS, modifiers);
    }

    private static void addFeatures() {
        addFeature("silver_ore", BiomeTags.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES, OFeatures.Placed.SILVER_ORE_LOW);
        addFeature("silver_ore_high", BiomeTags.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES, OFeatures.Placed.SILVER_ORE_HIGH);
        addFeature("lead_ore", BiomeTags.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES, OFeatures.Placed.LEAD_ORE);
        addFeature("lead_ore_extra", OTags.Biomes.RICH_IN_LEAD_ORE, GenerationStep.Decoration.UNDERGROUND_ORES, OFeatures.Placed.LEAD_ORE_EXTRA);
    }

    @SafeVarargs
    private static void addFeature(String name, TagKey<Biome> tagKey, GenerationStep.Decoration step, RegistryObject<PlacedFeature>... features) {
        modifiers.put(new ResourceLocation(Oreganized.MOD_ID, "features/" + name),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(new HolderSet.Named<>(biomeRegistry, tagKey), featureSet(features), step));
    }

    private static void removeFeature(String name, TagKey<Biome> tagKey, GenerationStep.Decoration step, Holder<PlacedFeature> feature) {
        modifiers.put(new ResourceLocation(Oreganized.MOD_ID, "features/" + name),
                new ForgeBiomeModifiers.RemoveFeaturesBiomeModifier(new HolderSet.Named<>(biomeRegistry, tagKey), featureSet(feature), Set.of(step)));
    }

    @SafeVarargs
    @SuppressWarnings("ConstantConditions")
    private static HolderSet<PlacedFeature> featureSet(RegistryObject<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(registryObject -> placedFeatures.getOrCreateHolderOrThrow(registryObject.getKey())).collect(Collectors.toList()));
    }

    @SafeVarargs
    @SuppressWarnings("ConstantConditions")
    private static HolderSet<PlacedFeature> featureSet(Holder<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(holder -> placedFeatures.getOrCreateHolderOrThrow(holder.unwrapKey().get())).collect(Collectors.toList()));
    }
}
