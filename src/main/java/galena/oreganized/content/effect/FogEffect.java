package galena.oreganized.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public class FogEffect extends MobEffect {
    public FogEffect() {
        super(MobEffectCategory.NEUTRAL, 2696993);
        this.setFactorDataFactory(() -> new MobEffectInstance.FactorData(22));
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

    }
}
