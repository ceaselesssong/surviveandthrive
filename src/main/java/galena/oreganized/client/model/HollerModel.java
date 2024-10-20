package galena.oreganized.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class HollerModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    public HollerModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5, -2.5F, 5, 5, 5)
                .texOffs(10, 28).addBox(-1.5F, 0, -2.5F, 3, 2, 2)
                .texOffs(12, 16).addBox(-2.5F, -5, -2.5F, 5, 5, 5, new CubeDeformation(0.5F)), PartPose.offset(0, 18, 0));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, 0, -1, 3, 4, 2)
                .texOffs(0, 16).addBox(-1.5F, 4, -1, 3, 5, 2, new CubeDeformation(-0.2F))
                .texOffs(0, 16).addBox(-1.5F, 2.5F, -1, 3, 2, 2, new CubeDeformation(-0.2F)), PartPose.offset(0, 18, 0));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(22, 0).addBox(-0.75F, -0.5F, -1, 2, 6, 2)
                .texOffs(2, 24).mirror().addBox(-0.75F, -0.5F, -0.9F, 5, 6, 0).mirror(false)
                .texOffs(2, 24).mirror().addBox(1.25F, -0.5F, 0.9F, 5, 6, 0).mirror(false), PartPose.offsetAndRotation(-1.75F, 18.5F, 0, 0, 0, 1.5708F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(22, 26).addBox(-6.25F, -0.5F, 0.9F, 5, 6, 0)
                .texOffs(22, 26).addBox(-4.25F, -0.5F, -0.9F, 5, 6, 0)
                .texOffs(22, 8).addBox(-1.25F, -0.5F, -1, 2, 6, 2), PartPose.offsetAndRotation(1.75F, 18.5F, 0, 0, 0, -1.5708F));
        
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}