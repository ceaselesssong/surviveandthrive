package galena.oreganized.world;

import com.mojang.serialization.Codec;
import galena.oreganized.index.OBiomeModifiers;
import galena.oreganized.index.OPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class OreganizedBiomeModifier implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OPlacedFeatures.SILVER_ORE_LOW);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return OBiomeModifiers.OREGANIZED_BIOME_MODIFIER.get();
    }
}
