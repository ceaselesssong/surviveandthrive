package galena.oreganized.index;

import com.mojang.serialization.Codec;
import galena.oreganized.Oreganized;
import galena.oreganized.world.OreganizedBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Oreganized.MOD_ID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> OREGANIZED_BIOME_MODIFIER = BIOME_MODIFIERS.register("oreganized_biome_modifier", () -> Codec.unit(OreganizedBiomeModifier::new));
}
