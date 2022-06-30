package me.gleep.oreganized.registry;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.world.gen.structure.BoulderStructure;
import me.gleep.oreganized.world.gen.structure.pieces.BoulderPieces;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class OStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Oreganized.MOD_ID);
    public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, Oreganized.MOD_ID);

    public static final RegistryObject<StructureType<BoulderStructure>> BOULDER_TYPE = STRUCTURE_TYPES.register("boulder", () -> (StructureType<BoulderStructure>) () -> BoulderStructure.CODEC);

    public static final RegistryObject<BoulderStructure> BOULDER = STRUCTURES.register("boulder", () -> new BoulderStructure(new Structure.StructureSettings(biomes(OTags.Biomes.HAS_BOULDER), Map.of(), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));

    public static final class PieceTypes {
        public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, Oreganized.MOD_ID);

        public static final RegistryObject<StructurePieceType.StructureTemplateType> BOULDER = STRUCTURE_PIECE_TYPES.register("boulder", () -> BoulderPieces.BoulderPiece::new);
    }

    @SuppressWarnings("deprecation")
    private static HolderSet<Biome> biomes(TagKey<Biome> tagKey) {
        return BuiltinRegistries.BIOME.getOrCreateTag(tagKey);
    }

}
