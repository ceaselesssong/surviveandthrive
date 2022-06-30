package me.gleep.oreganized.world.gen.structure;

import com.mojang.serialization.Codec;
import me.gleep.oreganized.registry.OStructures;
import me.gleep.oreganized.world.gen.structure.pieces.BoulderPieces;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.*;

import java.util.Optional;

public class BoulderStructure extends Structure {
    public static final Codec<BoulderStructure> CODEC = simpleCodec(BoulderStructure::new);

    public BoulderStructure(Structure.StructureSettings settings) {
        super(settings);
    }

    /**
     *        : WARNING!!! DO NOT FORGET THIS METHOD!!!! :
     * If you do not override step method, your structure WILL crash the biome as it is being parsed!
     *
     * Generation step for when to generate the structure. there are 10 stages you can pick from!
     * This surface structure stage places the structure before plants and ores are generated.
     */
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_DECORATION;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos boulderPos = context.chunkPos().getMiddleBlockPosition(0);
        int topLandY = context.chunkGenerator().getFirstFreeHeight(boulderPos.getX(), boulderPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos finalBoulderPos = boulderPos.above(topLandY - 7);
        return Optional.of(new Structure.GenerationStub(boulderPos, (builder) -> {
            builder.addPiece(new BoulderPieces.BoulderPiece(context.structureTemplateManager(), finalBoulderPos));
        }));
    }

    @Override
    public StructureType<?> type() {
        return OStructures.BOULDER_TYPE.get();
    }
}
