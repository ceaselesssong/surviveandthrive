package me.gleep.oreganized.world.gen.structure.pieces;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.registry.OStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

public class BoulderPieces {

    public static class BoulderPiece extends StructurePiece {
        private static final ResourceLocation BOULDER_1 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_1");
        private static final ResourceLocation BOULDER_2 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_2");
        private static final ResourceLocation BOULDER_3 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_3");
        private static final ResourceLocation BOULDER_4 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_4");
        private static final ResourceLocation BOULDER_5 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_5");
        private static final ResourceLocation BOULDER_6 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_6");
        private static final ResourceLocation BOULDER_7 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_7");
        private static final ResourceLocation BOULDER_8 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_8");
        private static final ResourceLocation BOULDER_9 = new ResourceLocation(Oreganized.MOD_ID, "boulder/boulder_9");
        private static final List<ResourceLocation> BOULDERS = List.of(BOULDER_1, BOULDER_2, BOULDER_3, BOULDER_4, BOULDER_5, BOULDER_6, BOULDER_7, BOULDER_8, BOULDER_9);

        private final StructureTemplate boulder;

        public BoulderPiece(StructureTemplateManager manager, CompoundTag NBT) {
            super(OStructures.PieceTypes.BOULDER.get(), NBT);
            this.boulder = manager.get(BOULDERS.get((int)(BOULDERS.size() / Math.random()))).orElseThrow(() -> new RuntimeException("Failed to get Boulder Template!"));
        }

        public BoulderPiece(StructureTemplateManager manager, BlockPos boulderPos) {
            super(OStructures.PieceTypes.BOULDER.get(), 0, new BoundingBox(boulderPos));
            this.boulder = manager.get(BOULDERS.get((int)(BOULDERS.size() / Math.random()))).orElseThrow(() -> new RuntimeException("Failed to get Boulder Template!"));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext p_192646_, CompoundTag p_192647_) {

        }

        @Override
        public void postProcess(WorldGenLevel p_226769_, StructureManager p_226770_, ChunkGenerator p_226771_, RandomSource p_226772_, BoundingBox p_226773_, ChunkPos p_226774_, BlockPos p_226775_) {

        }
    }
}
