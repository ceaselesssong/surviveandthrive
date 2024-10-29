package galena.doom_and_gloom.data;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.data.provider.OBlockStateProvider;
import galena.doom_and_gloom.index.OBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class OBlockStates extends OBlockStateProvider {

    public OBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    public String getName() {
        return DoomAndGloom.MOD_ID + " Block States";
    }

    @Override
    protected void registerStatesAndModels() {
        sepulcherBlock(OBlocks.SEPULCHER);
        simpleBlock(OBlocks.BONE_PILE.get(), models().cubeColumn(blockTexture(OBlocks.BONE_PILE.get()).getPath(), DoomAndGloom.modLoc("block/bone_pile_side"), DoomAndGloom.modLoc("block/sepulcher_rot_5")));
        simpleBlock(OBlocks.ROTTING_FLESH.get(), models().cubeAll(blockTexture(OBlocks.ROTTING_FLESH.get()).getPath(), DoomAndGloom.modLoc("block/sepulcher_rot_1")));
        vigilCandle(OBlocks.VIGIL_CANDLE, null);
        OBlocks.COLORED_VIGIL_CANDLES.forEach((color, block) -> vigilCandle(block, color.getSerializedName()));
    }

}
