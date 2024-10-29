package galena.doom_and_gloom.data;

import galena.doom_and_gloom.data.provider.ORecipeProvider;
import galena.doom_and_gloom.index.OBlocks;
import galena.doom_and_gloom.index.OItems;
import galena.doom_and_gloom.index.OTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ORecipes extends ORecipeProvider {

    public ORecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, OItems.BUSH_HAMMER.get())
                .pattern("AA")
                .pattern("B ")
                .define('A', OTags.Items.INGOTS_LEAD)
                .define('B', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_lead_ingot", has(OTags.Items.INGOTS_LEAD))
                .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
                .save(consumer);

        vigilCandle(OBlocks.VIGIL_CANDLE, Blocks.CANDLE).save(consumer);

        OBlocks.COLORED_VIGIL_CANDLES.forEach((color, block) -> {
            var candle = BuiltInRegistries.BLOCK.get(new ResourceLocation(color.getSerializedName() + "_candle"));
            vigilCandle(block, candle).save(consumer);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, block.get())
                    .requires(OBlocks.VIGIL_CANDLE.get())
                    .requires(DyeItem.byColor(color))
                    .unlockedBy("has_vigil_candle", has(OBlocks.VIGIL_CANDLE.get()))
                    .group("vigil_candle")
                    .save(consumer, RecipeBuilder.getDefaultRecipeId(block.get()).withSuffix("_dyeing"));
        });

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OBlocks.SEPULCHER.get())
                .pattern("# #")
                .pattern("# #")
                .pattern("###")
                .define('#', OTags.Items.INGOTS_SILVER)
                .unlockedBy("has_silver", has(OTags.Items.INGOTS_SILVER))
                .save(consumer);

        compact(OBlocks.BONE_PILE.get().asItem(), Items.BONE).save(consumer);
        unCompact(Items.BONE, OBlocks.BONE_PILE.get().asItem()).save(consumer);
    }
}
