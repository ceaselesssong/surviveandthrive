package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.data.provider.OBlockStateProvider;
import galena.oreganized.index.OBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class OBlockStates extends OBlockStateProvider {

    public OBlockStates(DataGenerator gen, ExistingFileHelper help) {
        super(gen, help);
    }

    @Override
    public String getName() {
        return Oreganized.MOD_ID + " Block States";
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(OBlocks.GLANCE);
        simpleBlock(OBlocks.POLISHED_GLANCE);
        simpleBlock(OBlocks.GLANCE_BRICKS);
        simpleBlock(OBlocks.CHISELED_GLANCE);
        slabBlock(OBlocks.GLANCE_SLAB, OBlocks.GLANCE);
        slabBlock(OBlocks.GLANCE_BRICK_SLAB, OBlocks.GLANCE_BRICKS);
        stairsBlock(OBlocks.GLANCE_STAIRS, OBlocks.GLANCE);
        stairsBlock(OBlocks.GLANCE_BRICK_STAIRS, OBlocks.GLANCE_BRICKS);
        wallBlock(OBlocks.GLANCE_WALL, OBlocks.GLANCE);
        wallBlock(OBlocks.GLANCE_BRICK_WALL, OBlocks.GLANCE_BRICKS);
        simpleBlock(OBlocks.SPOTTED_GLANCE);
        waxedBlock(OBlocks.WAXED_SPOTTED_GLANCE, OBlocks.SPOTTED_GLANCE.get());
        simpleBlock(OBlocks.SILVER_ORE);
        simpleBlock(OBlocks.DEEPSLATE_SILVER_ORE);
        simpleBlock(OBlocks.LEAD_ORE);
        simpleBlock(OBlocks.DEEPSLATE_LEAD_ORE);
        simpleBlock(OBlocks.RAW_SILVER_BLOCK);
        simpleBlock(OBlocks.RAW_LEAD_BLOCK);
        simpleBlock(OBlocks.LEAD_BLOCK);
        simpleBlock(OBlocks.ELECTRUM_BLOCK);
        simpleBlock(OBlocks.SHRAPNEL_BOMB.get(), cubeBottomTop(OBlocks.SHRAPNEL_BOMB));

        waxedBlock(OBlocks.WAXED_WHITE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_ORANGE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_MAGENTA_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_YELLOW_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_LIME_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_PINK_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_GRAY_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_LIGHT_GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_CYAN_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_PURPLE_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_BLUE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_BROWN_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_GREEN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_RED_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER);
        waxedBlock(OBlocks.WAXED_BLACK_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);

        exposer(OBlocks.EXPOSER);

        //moltenCauldron(OBlocks.MOLTEN_LEAD_CAULDRON, OBlocks.LEAD_BLOCK);

        for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
            Supplier<? extends Block> crystalGlassBlock = OBlocks.CRYSTAL_GLASS.get(i);
            crystalGlassBlock(crystalGlassBlock);
            crystalGlassPaneBlock(OBlocks.CRYSTAL_GLASS_PANES.get(i), crystalGlassBlock);
        }
    }

}
