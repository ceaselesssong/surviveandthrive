package galena.oreganized.integration.jei;

import galena.oreganized.Oreganized;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@JeiPlugin
@ParametersAreNonnullByDefault
public class OreganizedJEI implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(Oreganized.MOD_ID, "jei_plugin");

    private IIngredientManager ingredientManager;
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
