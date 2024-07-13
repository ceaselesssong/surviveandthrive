package galena.oreganized.mixin.client;

import galena.oreganized.content.effect.StunningEffect;
import galena.oreganized.index.OEffects;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @ModifyVariable(
            method = "turnPlayer()V",
            at = @At("STORE"),
            ordinal = 3
    )
    private double smoothTurn(double value) {
        var accessor = (MouseHandlerAccessor) this;
        var player = accessor.getMinecraft().player;
        return StunningEffect.getTurnModifier(player) * value;
    }

    @Redirect(
            method = "turnPlayer()V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;smoothCamera:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean injected(Options instance) {
        var accessor = (MouseHandlerAccessor) this;
        var player = accessor.getMinecraft().player;
        return instance.smoothCamera || player.hasEffect(OEffects.STUNNING.get());
    }

}
