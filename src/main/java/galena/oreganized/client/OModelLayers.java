package galena.oreganized.client;

import com.google.common.collect.Sets;
import galena.oreganized.Oreganized;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class OModelLayers {

    public static final ModelLayerLocation HOLLER = new ModelLayerLocation(new ResourceLocation(Oreganized.MOD_ID, "holler"), "main");
}
