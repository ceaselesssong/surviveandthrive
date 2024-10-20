package galena.oreganized.client.render.entity;

import galena.oreganized.Oreganized;
import galena.oreganized.client.OModelLayers;
import galena.oreganized.client.model.HollerModel;
import galena.oreganized.content.entity.holler.Holler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HollerRender extends MobRenderer<Holler, HollerModel<Holler>> {

    private static final ResourceLocation HOLLER_TEXTURE = new ResourceLocation(Oreganized.MOD_ID, "textures/entity/holler.png");

    public HollerRender(EntityRendererProvider.Context context) {
            super(context, new HollerModel<>(context.bakeLayer(OModelLayers.HOLLER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Holler holler) {
        return HOLLER_TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(Holler p_114496_, BlockPos p_114497_) {
        return 15;
    }
}
