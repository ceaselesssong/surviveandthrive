package galena.oreganized.mixin;

import galena.oreganized.content.effect.StunningEffect;
import galena.oreganized.index.OEffects;
import net.minecraft.world.effect.MobEffectInstance;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin {

    @Redirect(
            method = "update(Lnet/minecraft/world/effect/MobEffectInstance;)Z",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;amplifier:I",
                    ordinal = 0,
                    opcode = Opcodes.GETFIELD
            )
    )
    private int replaceStunning(MobEffectInstance instance) {
        if (instance.getEffect() == OEffects.STUNNING.get()) return StunningEffect.MAX_AMPLIFIER + 1;
        return instance.getAmplifier();
    }

}
