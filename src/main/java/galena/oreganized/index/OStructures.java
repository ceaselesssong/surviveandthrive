package galena.oreganized.index;

import com.mojang.serialization.Codec;
import galena.oreganized.Oreganized;
import galena.oreganized.world.gen.structure.BoulderStructure;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class OStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Oreganized.MOD_ID);

    public static final RegistryObject<StructureType<BoulderStructure>> BOULDER = STRUCTURES.register("boulder", () -> explicitStructureTypeTyping(BoulderStructure.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }
}