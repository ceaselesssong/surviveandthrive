package galena.doom_and_gloom.client;

import galena.doom_and_gloom.DoomAndGloom;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OModelLayers {

    public static final ModelLayerLocation HOLLER = new ModelLayerLocation(new ResourceLocation(DoomAndGloom.MOD_ID, "holler"), "main");
    public static final ModelLayerLocation DIRT_MOUND = new ModelLayerLocation(new ResourceLocation(DoomAndGloom.MOD_ID, "dirt_mound"), "main");
}
