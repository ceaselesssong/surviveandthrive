package me.gleep.oreganized.data;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.registry.OBlocks;
import me.gleep.oreganized.registry.OTags;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

import static me.gleep.oreganized.registry.OTags.Blocks.*;

public class OBlockTags extends BlockTagsProvider {

    public OBlockTags(DataGenerator generator, @Nullable ExistingFileHelper helper) {
        super(generator, Oreganized.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Oreganized Block Tags";
    }

    @Override
    protected void addTags() {
        // Oreganized
        tag(ENGRAVABLE).add(
                Blocks.STONE,
                Blocks.STONE_BRICKS, RegistryHandler.ENGRAVED_STONE_BRICKS.get(),
                Blocks.POLISHED_BLACKSTONE_BRICKS, RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(),
                Blocks.NETHER_BRICKS, RegistryHandler.ENGRAVED_NETHER_BRICKS.get(),
                Blocks.RED_NETHER_BRICKS, RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(),
                Blocks.BRICKS, RegistryHandler.ENGRAVED_BRICKS.get(),
                Blocks.CUT_COPPER, RegistryHandler.ENGRAVED_CUT_COPPER.get(),
                Blocks.EXPOSED_CUT_COPPER, RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(),
                Blocks.WEATHERED_CUT_COPPER, RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(),
                Blocks.OXIDIZED_CUT_COPPER, RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(),
                Blocks.WAXED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(),
                Blocks.WAXED_EXPOSED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(),
                Blocks.WAXED_WEATHERED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(),
                Blocks.WAXED_OXIDIZED_CUT_COPPER, RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(),
                Blocks.DEEPSLATE_BRICKS, RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(),
                Blocks.END_STONE_BRICKS, RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(),
                Blocks.QUARTZ_BRICKS, RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(),
                Blocks.PRISMARINE_BRICKS, RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(),
                OBlocks.GLANCE_BRICKS.get(), RegistryHandler.ENGRAVED_GLANCE_BRICKS.get()
        );
        tag(CRYSTAL_GLASS).add(
                RegistryHandler.BLACK_CRYSTAL_GLASS.get()
        );
        tag(CRYSTAL_GLASS_PANES).add(
                RegistryHandler.BLACK_CRYSTAL_GLASS.get()
        );

        // Oreganized Forge
        tag(ORES_SILVER).add(OBlocks.SILVER_ORE.get(), OBlocks.DEEPSLATE_SILVER_ORE.get());
        tag(ORES_LEAD).add(OBlocks.LEAD_ORE.get(), OBlocks.DEEPSLATE_LEAD_ORE.get());

        tag(STORAGE_BLOCKS_SILVER).add(OBlocks.SILVER_BLOCK.get());
        tag(STORAGE_BLOCKS_LEAD).add(OBlocks.LEAD_BLOCK.get());
        tag(STORAGE_BLOCKS_ELECTRUM).add(OBlocks.ELECTRUM_BLOCK.get());

        tag(STORAGE_BLOCKS_RAW_SILVER).add(OBlocks.RAW_SILVER_BLOCK.get());
        tag(STORAGE_BLOCKS_RAW_LEAD).add(OBlocks.RAW_LEAD_BLOCK.get());

        // Vanilla
        tag(BlockTags.WALLS).add(OBlocks.GLANCE_WALL.get(), OBlocks.GLANCE_BRICKS_WALL.get());
        tag(BlockTags.STAIRS).add(OBlocks.GLANCE_STAIRS.get(), OBlocks.GLANCE_BRICKS_STAIRS.get());
        tag(BlockTags.SLABS).add(OBlocks.GLANCE_SLAB.get(), OBlocks.GLANCE_BRICKS_SLAB.get());
        tag(BlockTags.BEACON_BASE_BLOCKS).add(OBlocks.ELECTRUM_BLOCK.get());
        tag(BlockTags.IMPERMEABLE).addTag(CRYSTAL_GLASS);

        // Forge
        tag(Tags.Blocks.ORES).addTags(ORES_SILVER, ORES_LEAD);
        tag(Tags.Blocks.ORE_RATES_SINGULAR).addTags(ORES_SILVER, ORES_LEAD);
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_SILVER, STORAGE_BLOCKS_LEAD, STORAGE_BLOCKS_ELECTRUM, STORAGE_BLOCKS_RAW_SILVER, STORAGE_BLOCKS_RAW_LEAD);
        tag(Tags.Blocks.GLASS).addTag(CRYSTAL_GLASS);
        tag(Tags.Blocks.GLASS_PANES).addTag(CRYSTAL_GLASS_PANES);

        // Mineables!
        /*tag(MINEABLE_WITH_BUSH_HAMMER).add(

        );*/
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                OBlocks.DEEPSLATE_LEAD_ORE.get(),
                OBlocks.RAW_SILVER_BLOCK.get(),
                OBlocks.DEEPSLATE_SILVER_ORE.get(),
                OBlocks.GLANCE.get(),
                OBlocks.GLANCE_STAIRS.get(),
                OBlocks.GLANCE_SLAB.get(),
                OBlocks.POLISHED_GLANCE.get(),
                OBlocks.CHISELED_GLANCE.get(),
                OBlocks.GLANCE_WALL.get(),
                OBlocks.GLANCE_BRICKS.get(),
                OBlocks.GLANCE_BRICKS_STAIRS.get(),
                OBlocks.GLANCE_BRICKS_SLAB.get(),
                OBlocks.GLANCE_BRICKS_WALL.get(),
                OBlocks.SPOTTED_GLANCE.get(),
                OBlocks.WAXED_SPOTTED_GLANCE.get(),
                OBlocks.SILVER_ORE.get(),
                OBlocks.LEAD_ORE.get(),
                OBlocks.LEAD_BLOCK.get(),
                OBlocks.SILVER_BLOCK.get(),
                RegistryHandler.ENGRAVED_STONE_BRICKS.get(), RegistryHandler.ENGRAVED_POLISHED_BLACKSTONE_BRICKS.get(), RegistryHandler.ENGRAVED_NETHER_BRICKS.get(), RegistryHandler.ENGRAVED_RED_NETHER_BRICKS.get(), RegistryHandler.ENGRAVED_BRICKS.get(), RegistryHandler.ENGRAVED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WEATHERED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_OXIDIZED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get(), RegistryHandler.ENGRAVED_DEEPSLATE_BRICKS.get(), RegistryHandler.ENGRAVED_END_STONE_BRICKS.get(), RegistryHandler.ENGRAVED_QUARTZ_BRICKS.get(), RegistryHandler.ENGRAVED_PRISMARINE_BRICKS.get(), RegistryHandler.ENGRAVED_GLANCE_BRICKS.get()
        );

    }
}
