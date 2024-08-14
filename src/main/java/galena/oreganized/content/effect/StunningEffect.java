package galena.oreganized.content.effect;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT)
public class StunningEffect extends MobEffect {

    private static final String SLOWNESS_UUID = "019150f6-e85e-777f-a566-1eafd7c7e1a5";
    public static final int MAX_AMPLIFIER = 7;

    public StunningEffect() {
        super(MobEffectCategory.HARMFUL, 0x3B3B63);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, SLOWNESS_UUID, -0.15000000596046448, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static double getTurnModifier(LivingEntity entity) {
        var effect = entity.getEffect(OEffects.STUNNING.get());
        if (effect == null) return 1.0;

        return 0.95 - effect.getAmplifier() * 0.05;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity.level().getGameTime() % 5 != 0L) return;

        var health = entity.getHealth() / entity.getMaxHealth();
        var targetAmplifier = (int) Math.ceil((1.0 - health) * MAX_AMPLIFIER);

        if (targetAmplifier == amplifier) return;
        var instance = entity.getEffect(this);
        if (instance == null) return;

        var step = targetAmplifier > amplifier ? 1 : -1;

        instance.update(new MobEffectInstance(this, instance.getDuration() + 1, amplifier + step, instance.isAmbient(), instance.isVisible(), instance.showIcon()));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
