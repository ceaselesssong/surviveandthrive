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
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Gui.class)
public class GuiMixin {

    @Redirect(
            method = "renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V", ordinal = 1),
                    to = @At("TAIL")
            )
    )
    private void renderStunnedHeart(Gui instance, GuiGraphics graphics, Gui.HeartType type, int x, int y, int v, boolean flag1, boolean flag2, @Local Player player) {
        var accessor = (GuiAccessor) instance;

        if (player.hasEffect(OEffects.STUNNING.get())) {
            var renderedType = type == Gui.HeartType.POISIONED ? type : Gui.HeartType.NORMAL;
            var u = renderedType.getX(flag1, flag2);
            OGui.renderStunnedHeart(graphics, u - 52, x, y, v / 5);
        } else {
            accessor.callRenderHeart(graphics, type, x, y, v, flag1, flag2);
        }
    }

}
