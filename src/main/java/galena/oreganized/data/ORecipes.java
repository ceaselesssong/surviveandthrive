package galena.oreganized.data;

import galena.oreganized.data.provider.ORecipeProvider;
import galena.oreganized.content.index.OBlocks;
import galena.oreganized.content.index.OItems;
import galena.oreganized.content.index.OTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ORecipes extends ORecipeProvider {

    public ORecipes(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        quadTransform(OBlocks.POLISHED_GLANCE, OBlocks.GLANCE).save(consumer);
        quadTransform(OBlocks.GLANCE_BRICKS, OBlocks.POLISHED_GLANCE).save(consumer);

        compact(OBlocks.SILVER_BLOCK.get().asItem(), OItems.SILVER_INGOT.get()).save(consumer);
        compact(OBlocks.LEAD_BLOCK.get().asItem(), OItems.LEAD_INGOT.get()).save(consumer);
        compact(OBlocks.ELECTRUM_BLOCK.get().asItem(), OItems.ELECTRUM_INGOT.get()).save(consumer);

        compact(OBlocks.RAW_SILVER_BLOCK.get().asItem(), OItems.RAW_SILVER.get()).save(consumer);
        compact(OBlocks.RAW_LEAD_BLOCK.get().asItem(), OItems.RAW_LEAD.get()).save(consumer);

        compact(OItems.SILVER_INGOT.get(), OItems.SILVER_NUGGET.get()).save(consumer);
        compact(OItems.LEAD_INGOT.get(), OItems.LEAD_NUGGET.get()).save(consumer);
        compact(OItems.ELECTRUM_INGOT.get(), OItems.ELECTRUM_NUGGET.get()).save(consumer, "electrum_ingot_from_nuggets");
        compact(Items.NETHERITE_INGOT, OItems.NETHERITE_NUGGET.get()).save(consumer);

        unCompact(OItems.SILVER_INGOT.get(), OBlocks.SILVER_BLOCK.get().asItem()).save(consumer, "silver_ingot_from_block");
        unCompact(OItems.LEAD_INGOT.get(), OBlocks.LEAD_BLOCK.get().asItem()).save(consumer, "lead_ingot_from_block");
        unCompact(OItems.ELECTRUM_INGOT.get(), OBlocks.ELECTRUM_BLOCK.get().asItem()).save(consumer, "electrum_ingot_from_block");

        unCompact(OItems.RAW_SILVER.get(), OBlocks.RAW_SILVER_BLOCK.get().asItem()).save(consumer, "raw_silver_from_block");
        unCompact(OItems.RAW_LEAD.get(), OBlocks.RAW_LEAD_BLOCK.get().asItem()).save(consumer, "raw_lead_from_block");

        unCompact(OItems.SILVER_NUGGET.get(), OItems.SILVER_INGOT.get()).save(consumer);
        unCompact(OItems.LEAD_NUGGET.get(), OItems.LEAD_INGOT.get()).save(consumer);
        unCompact(OItems.ELECTRUM_NUGGET.get(), OItems.ELECTRUM_INGOT.get()).save(consumer);
        unCompact(OItems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT).save(consumer);

        makeWaxed(OBlocks.WAXED_SPOTTED_GLANCE, OBlocks.SPOTTED_GLANCE).save(consumer);

        smithingElectrum(Items.DIAMOND_SWORD, OItems.ELECTRUM_SWORD).save(consumer, "electrum_sword");
        smithingElectrum(Items.DIAMOND_SHOVEL, OItems.ELECTRUM_SHOVEL).save(consumer, "electrum_shovel");
        smithingElectrum(Items.DIAMOND_PICKAXE, OItems.ELECTRUM_PICKAXE).save(consumer, "electrum_pickaxe");
        smithingElectrum(Items.DIAMOND_AXE, OItems.ELECTRUM_AXE).save(consumer, "electrum_axe");
        smithingElectrum(Items.DIAMOND_HOE, OItems.ELECTRUM_HOE).save(consumer, "electrum_hoe");
        smithingElectrum(Items.DIAMOND_HELMET, OItems.ELECTRUM_HELMET).save(consumer, "electrum_helmet");
        smithingElectrum(Items.DIAMOND_CHESTPLATE, OItems.ELECTRUM_CHESTPLATE).save(consumer, "electrum_chestplate");
        smithingElectrum(Items.DIAMOND_LEGGINGS, OItems.ELECTRUM_LEGGINGS).save(consumer, "electrum_leggings");
        smithingElectrum(Items.DIAMOND_BOOTS, OItems.ELECTRUM_BOOTS).save(consumer, "electrum_boots");

        crystalGlass(OBlocks.BLACK_CRYSTAL_GLASS, Blocks.BLACK_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.BLUE_CRYSTAL_GLASS, Blocks.BLUE_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.BROWN_CRYSTAL_GLASS, Blocks.BROWN_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.CYAN_CRYSTAL_GLASS, Blocks.CYAN_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.GRAY_CRYSTAL_GLASS, Blocks.GRAY_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.GREEN_CRYSTAL_GLASS, Blocks.GREEN_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.LIGHT_BLUE_CRYSTAL_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.LIGHT_GRAY_CRYSTAL_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.LIME_CRYSTAL_GLASS, Blocks.LIME_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.MAGENTA_CRYSTAL_GLASS, Blocks.MAGENTA_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.ORANGE_CRYSTAL_GLASS, Blocks.ORANGE_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.PINK_CRYSTAL_GLASS, Blocks.PINK_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.PURPLE_CRYSTAL_GLASS, Blocks.PURPLE_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.RED_CRYSTAL_GLASS, Blocks.RED_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.WHITE_CRYSTAL_GLASS, Blocks.WHITE_STAINED_GLASS).save(consumer);
        crystalGlass(OBlocks.YELLOW_CRYSTAL_GLASS, Blocks.YELLOW_STAINED_GLASS).save(consumer);

        for (int i = 0; OBlocks.CRYSTAL_GLASS_PANES.size() > i; i++) {
            makeBars(OBlocks.CRYSTAL_GLASS_PANES.get(i), OBlocks.CRYSTAL_GLASS.get(i)).save(consumer);
        }

        ShapelessRecipeBuilder.shapeless(OItems.ELECTRUM_INGOT.get())
                .requires(OTags.Items.INGOTS_SILVER)
                .requires(OTags.Items.INGOTS_SILVER)
                .requires(OTags.Items.INGOTS_SILVER)
                .requires(OTags.Items.INGOTS_SILVER)
                .requires(OTags.Items.INGOTS_SILVER)
                .requires(Tags.Items.INGOTS_GOLD)
                .requires(Tags.Items.INGOTS_GOLD)
                .requires(Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_silver_ingot", has(OTags.Items.INGOTS_SILVER))
                .save(consumer);


        ShapedRecipeBuilder.shaped(OItems.BUSH_HAMMER.get())
                .pattern("AA")
                .pattern("B ")
                .define('A', OTags.Items.INGOTS_LEAD)
                .define('B', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_lead_ingot", has(OTags.Items.INGOTS_LEAD))
                .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
                .save(consumer);

        ShapedRecipeBuilder.shaped(OItems.SILVER_MIRROR.get())
                .pattern("ABA")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Tags.Items.INGOTS_GOLD)
                .define('B', OTags.Items.INGOTS_SILVER)
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_silver_ingot", has(OTags.Items.INGOTS_SILVER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(OBlocks.EXPOSER.get())
                .pattern("AAA")
                .pattern("BBC")
                .pattern("AAA")
                .define('A', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('B', Tags.Items.DUSTS_REDSTONE)
                .define('C', OTags.Items.INGOTS_SILVER)
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .unlockedBy("has_silver_ingot", has(OTags.Items.INGOTS_SILVER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(OBlocks.SHRAPNEL_BOMB.get())
                .pattern("ABA")
                .pattern("BAB")
                .pattern("ABA")
                .define('A', Tags.Items.GUNPOWDER)
                .define('B', OTags.Items.NUGGETS_LEAD)
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .unlockedBy("has_lead_nugget", has(OTags.Items.NUGGETS_LEAD))
                .save(consumer);
    }
}
