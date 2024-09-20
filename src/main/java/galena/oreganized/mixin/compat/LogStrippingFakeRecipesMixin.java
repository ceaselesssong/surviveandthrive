package galena.oreganized.mixin.compat;

import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.LogStrippingFakeRecipes;
import galena.oreganized.Oreganized;
import galena.oreganized.content.item.ScribeItem;
import galena.oreganized.index.OItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = LogStrippingFakeRecipes.class, remap = false)
public class LogStrippingFakeRecipesMixin {

    @Inject(method = "createRecipes", at = @At("RETURN"), require = 0, cancellable = true)
    private static void addGroovedRecipes(CallbackInfoReturnable<List<ManualApplicationRecipe>> cir) {
        var newList = new ArrayList<>(cir.getReturnValue());

        ScribeItem.getGroovedBlocks().forEach(entry -> {
            var blockId = BuiltInRegistries.BLOCK.getKey(entry.getKey()).getPath();
            newList.add(
                    new ProcessingRecipeBuilder<>(ManualApplicationRecipe::new, Oreganized.modLoc("manual_application/" + blockId))
                            .withItemIngredients(Ingredient.of(entry.getKey()), Ingredient.of(OItems.SCRIBE.get()))
                            .withItemOutputs(new ProcessingOutput(new ItemStack(entry.getValue().get()), 1F))
                            .toolNotConsumed()
                            .build()
            );
        });

        cir.setReturnValue(newList);
    }

}
