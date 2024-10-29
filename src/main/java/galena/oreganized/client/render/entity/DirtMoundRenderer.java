package galena.oreganized.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import galena.oreganized.Oreganized;
import galena.oreganized.client.OModelLayers;
import galena.oreganized.client.model.DirtMoundModel;
import galena.oreganized.content.entity.DirtMound;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DirtMoundRenderer extends EntityRenderer<DirtMound> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Oreganized.MOD_ID, "textures/entity/dirt_mound.png");

    private final EntityModel<DirtMound> model;

    public DirtMoundRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DirtMoundModel(context.bakeLayer(OModelLayers.DIRT_MOUND));
    }

    @Override
    public void render(DirtMound entity, float pEntityYaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int packedLight) {
        pose.pushPose();

        RenderType rendertype = this.model.renderType(getTextureLocation(entity));
        VertexConsumer vertexconsumer = buffer.getBuffer(rendertype);
        pose.scale(-1.0F, -1.0F, 1.0F);
        pose.translate(0.0F, -1.501F, 0.0F);
        this.model.renderToBuffer(pose, vertexconsumer, packedLight, 655360, 1.0F, 1.0F, 1.0F, 1.0F);

        pose.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DirtMound entity) {
        return TEXTURE;
    }

}
