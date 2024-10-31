package galena.doom_and_gloom.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.client.OModelLayers;
import galena.doom_and_gloom.client.model.HollerModel;
import galena.doom_and_gloom.content.entity.holler.Holler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class HollerRender extends MobRenderer<Holler, HollerModel<Holler>> {

    private static final ResourceLocation HOLLER_TEXTURE = DoomAndGloom.modLoc( "textures/entity/holler.png");

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
