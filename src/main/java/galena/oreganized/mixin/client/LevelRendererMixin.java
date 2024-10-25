package galena.oreganized.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import galena.oreganized.index.OEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(
            method = "renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FDDD)V",
            cancellable = true,
            at = @At("HEAD")
    )
    private void disableClouds(PoseStack pose, Matrix4f matrix, float p_254364_, double p_253843_, double p_253663_, double p_253795_, CallbackInfo ci) {
        var player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(OEffects.FOG.get())) {
            ci.cancel();
        }
    }

}
