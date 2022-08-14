package galena.oreganized.client.render.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.oreganized.Oreganized;
import galena.oreganized.content.index.OEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.MinecraftForge;

@OnlyIn(Dist.CLIENT)
public class StunningOverlay implements IGuiOverlay {

    protected static final ResourceLocation STUNNING_LOCATION = new ResourceLocation(Oreganized.MOD_ID, "textures/misc/stunning.png");
    protected static final ResourceLocation STUNNING_VIGNETTE_LOCATION = new ResourceLocation(Oreganized.MOD_ID, "textures/misc/stunning_vignette.png");

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        OGui fakeGui = new OGui(minecraft);

        fakeGui.render(poseStack, partialTick);
    }
}
