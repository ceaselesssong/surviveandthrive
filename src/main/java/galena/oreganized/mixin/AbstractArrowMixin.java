package galena.oreganized.mixin;

import galena.oreganized.content.entity.LeadBoltEntity;
import galena.oreganized.index.ODamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Redirect(
            method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;arrow(Lnet/minecraft/world/entity/projectile/AbstractArrow;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;")
    )
    public DamageSource injectLeadBolt(DamageSources instance, AbstractArrow arrow, Entity user) {
        if (arrow instanceof LeadBoltEntity) {
            return instance.source(ODamageSources.LEAD_BOLT, arrow, user);
        }

        return instance.arrow(arrow, user);
    }

}
