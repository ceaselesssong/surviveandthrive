package galena.oreganized.client.render.world;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.oreganized.Oreganized;
import galena.oreganized.content.capabilities.block.engraveable.IEngraveableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT)
public class EngravedBlocksRenderer {

    @SubscribeEvent
    public static void renderEngravedBlocksOverlay(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Level world = mc.player.level;
        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        //IEngraveableBlock capability = world.getCapability().orElse(null);
    }
}
