package galena.oreganized.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import galena.oreganized.client.render.gui.OGui;
import galena.oreganized.index.OEffects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixin {

    @Redirect(
            method = "renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V")
    )
    private void renderStunnedHeart(Gui instance, GuiGraphics graphics, Gui.HeartType type, int x, int y, int v, boolean flag1, boolean flag2, @Local Player player) {
        var accessor = (GuiAccessor) instance;

        if (player.hasEffect(OEffects.STUNNING.get())) {
            OGui.renderStunnedHeart(graphics, x, y, v);
        } else {
            accessor.callRenderHeart(graphics, type, x, y, v, flag1, flag2);
        }
    }

}
