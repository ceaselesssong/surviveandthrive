package galena.oreganized.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import galena.oreganized.client.render.gui.OGui;
import galena.oreganized.index.OEffects;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Gui.class)
public class GuiMixin {

    @WrapOperation(
            method = "renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V", ordinal = 1),
                    to = @At("TAIL")
            )
    )
    private void renderStunnedHeart(Gui instance, GuiGraphics graphics, Gui.HeartType type, int x, int y, int v, boolean blinking, boolean half, Operation<Void> original, @Local Player player) {
        if (player.hasEffect(OEffects.STUNNING.get()) && (type == Gui.HeartType.NORMAL || type == Gui.HeartType.POISIONED)) {
            var u = type.getX(half, blinking);
            OGui.renderStunnedHeart(graphics, u - 52, x, y, v / 5);
        } else {
            original.call(instance, graphics, type, x, y, v, blinking, half);
        }
    }

}
