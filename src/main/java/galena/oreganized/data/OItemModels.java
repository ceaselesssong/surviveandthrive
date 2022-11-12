package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.data.provider.OItemModelProvider;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OItems;
import galena.oreganized.integration.farmersdelight.FDCompatRegistry;
import galena.oreganized.integration.nethersdelight.NDCompatRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class OItemModels extends OItemModelProvider {

    public OItemModels(DataGenerator gen, ExistingFileHelper help) {
        super(gen, help);
    }

    @Override
    public String getName() {
        return Oreganized.MOD_ID + " Item Models";
    }

    @Override
    protected void registerModels() {
        normalItem(OItems.MUSIC_DISC_PILLAGED);
        normalItem(OItems.MUSIC_DISC_18);
        normalItem(OItems.MUSIC_DISC_SHULK);
        normalItem(OItems.RAW_SILVER);
        normalItem(OItems.SILVER_INGOT);
        normalItem(OItems.SILVER_NUGGET);
        normalItem(OItems.RAW_LEAD);
        normalItem(OItems.LEAD_INGOT);
        normalItem(OItems.LEAD_NUGGET);
        normalItem(OItems.ELECTRUM_INGOT);
        normalItem(OItems.ELECTRUM_NUGGET);
        normalItem(OItems.NETHERITE_NUGGET);
        normalItem(OItems.MOLTEN_LEAD_BUCKET);
        normalItem(OItems.ELECTRUM_HELMET);
        normalItem(OItems.ELECTRUM_CHESTPLATE);
        normalItem(OItems.ELECTRUM_LEGGINGS);
        normalItem(OItems.ELECTRUM_BOOTS);
        normalItem(OItems.SHRAPNEL_BOMB_MINECART);

        toolItem(OItems.BUSH_HAMMER);
        toolItem(OItems.ELECTRUM_SWORD);
        toolItem(OItems.ELECTRUM_SHOVEL);
        toolItem(OItems.ELECTRUM_PICKAXE);
        toolItem(OItems.ELECTRUM_AXE);
        toolItem(OItems.ELECTRUM_HOE);
        toolItem(FDCompatRegistry.ELECTRUM_KNIFE);
        toolItem(NDCompatRegistry.ELECTRUM_MACHETE);

        block(OBlocks.GLANCE);
        block(OBlocks.POLISHED_GLANCE);
        block(OBlocks.GLANCE_BRICKS);
        block(OBlocks.CHISELED_GLANCE);
        block(OBlocks.GLANCE_SLAB);
        block(OBlocks.GLANCE_BRICK_SLAB);
        block(OBlocks.GLANCE_STAIRS);
        block(OBlocks.GLANCE_BRICK_STAIRS);
        wall(OBlocks.GLANCE_WALL, OBlocks.GLANCE);
        wall(OBlocks.GLANCE_BRICK_WALL, OBlocks.GLANCE_BRICKS);
        block(OBlocks.SPOTTED_GLANCE);
        block(OBlocks.WAXED_SPOTTED_GLANCE, blockName(OBlocks.SPOTTED_GLANCE));
        block(OBlocks.SILVER_ORE);
        block(OBlocks.DEEPSLATE_SILVER_ORE);
        block(OBlocks.LEAD_ORE);
        block(OBlocks.DEEPSLATE_LEAD_ORE);
        block(OBlocks.RAW_SILVER_BLOCK);
        block(OBlocks.RAW_LEAD_BLOCK);
        block(OBlocks.LEAD_BLOCK);
        block(OBlocks.ELECTRUM_BLOCK);
        block(OBlocks.EXPOSER, "exposer_level_0_south");
        block(OBlocks.SHRAPNEL_BOMB);

        for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
            Supplier<? extends Block> crystalGlassBlock = OBlocks.CRYSTAL_GLASS.get(i);
            block(crystalGlassBlock);
            blockFlat(OBlocks.CRYSTAL_GLASS_PANES.get(i), crystalGlassBlock);
        }

        for (Supplier<? extends Block> blocks : OBlocks.WAXED_CONRETE_POWDER) {
            withExistingParent(blockName(blocks), "minecraft:block/" + blockName(blocks).replace("waxed_", ""));
        }
    }
}
