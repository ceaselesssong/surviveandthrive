package galena.oreganized.content.effect;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT)
public class StunningEffect extends MobEffect {

    public StunningEffect() {
        super(MobEffectCategory.HARMFUL, 0x3B3B63);
    }

    public static double getTurnModifier(LivingEntity entity) {
        var effect = entity.getEffect(OEffects.STUNNING.get());
        if(effect == null) return 1.0;

        return 0.5;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity cause, @Nullable Entity owner, LivingEntity entity, int p_19465_, double p_19466_) {
        super.applyInstantenousEffect(cause, owner, entity, p_19465_, p_19466_);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
