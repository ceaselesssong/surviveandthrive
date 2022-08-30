package galena.oreganized.client.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import galena.oreganized.api.TextureDeconstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class EngraveEditScreen extends Screen {

    private final String[] lines = new String[7];

    private int currentLine;

    private TextFieldHelper textField;

    private int frame;
    private final Block targetBlock;
    private final BlockPos targetPos;

    private final int textColor;

    public EngraveEditScreen(Block clickedBlock, BlockPos blockPos) {
        super(Component.translatable("engrave.edit"));
        this.targetBlock = clickedBlock;
        this.targetPos = blockPos;
        this.textColor = TextureDeconstructor.getBrightessColorFromBlock(Minecraft.getInstance().level.getBlockState(blockPos).getBlock(), blockPos);
        int i = 0;
        for (String line : lines) {
            this.lines[i] = "";
            i++;
        }
    }

    @Override
    public void tick() {
        frame++;
        if (this.minecraft == null || this.minecraft.level == null) return;
        if (this.minecraft.level.isEmptyBlock(targetPos) && frame > 20) {
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = this.minecraft;
        if (mc == null || mc.level == null) return;
        Level world = mc.level;

        Lighting.setupForFlatItems();
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 40, 16777215);
        poseStack.pushPose();
        poseStack.translate(this.width / 2.0D - 51.5D, 58, 50.0);
        poseStack.scale(0.4F, 0.4F, 0.4F);
        String id = mc.getBlockRenderer().getBlockModelShaper().getTexture(targetBlock.defaultBlockState(), world, this.targetPos).getName().getNamespace();
        ResourceLocation texture = new ResourceLocation(id, "textures/block" + ForgeRegistries.BLOCKS.getKey(targetBlock).getPath() + ".png");
        mc.textureManager.bindForSetup(texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(poseStack, 0, 0, 0, 0, 256, 256);
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.translate(this.width / 2.0D, 0.0D, 50.0D);
        float f1 = 93.75F;
        poseStack.scale(f1, -f1, f1);
        poseStack.translate(0.0D, -1.5325D, 0.0D);

        boolean flag = this.frame / 6 % 2 == 0;
        float f2 = (1F / 3F * 2F / 10) + 0.2F;
        poseStack.pushPose();
        poseStack.scale(f2, -f2, f2);
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        poseStack.popPose();
        float f3 = 0.010416667F;
        poseStack.translate(0.0D, 0.33333334D, 0.046666667D);
        poseStack.scale(f3, -f3, f3);
        int cursorPos = this.textField.getCursorPos();
        int selectionPos = this.textField.getSelectionPos();
        int currentLine = this.currentLine * 9 - this.lines.length * 5;
        Matrix4f matrix = poseStack.last().pose();

        for (int i = 0; i < this.lines.length; i++) {
            String text = this.lines[i];
            if (text == null) return;
            if (this.font.isBidirectional()) text = this.font.bidirectionalShaping(text);

            float width = -mc.font.width(text) / 2F - 0.5F;
            mc.font.drawInBatch(text, width + 1F, i * 9 - this.lines.length * 5 + 0.4F, textColor, false, matrix, bufferSource, false, 0, 15728880, false);

        }
    }
}
