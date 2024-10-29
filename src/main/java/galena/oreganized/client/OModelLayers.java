package galena.oreganized.client;

import galena.oreganized.Oreganized;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OModelLayers {

    public static final ModelLayerLocation HOLLER = new ModelLayerLocation(new ResourceLocation(Oreganized.MOD_ID, "holler"), "main");
    public static final ModelLayerLocation DIRT_MOUND = new ModelLayerLocation(new ResourceLocation(Oreganized.MOD_ID, "dirt_mound"), "main");
}
