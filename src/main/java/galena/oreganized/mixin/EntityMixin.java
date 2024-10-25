package galena.oreganized.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import galena.oreganized.index.OBlocks;
import galena.oreganized.world.IMotionHolder;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IMotionHolder {

    @Unique
    private double oreganized$Motion = 0.0;

    @Inject(
            method = "setOldPosAndRot",
            at = @At("HEAD")
    )
    private void updateMotion(CallbackInfo ci) {
        var self = (Entity) (Object) this;
        var deltaX = self.getX() - self.xOld;
        var deltaZ = self.getZ() - self.zOld;
        oreganized$Motion = deltaX * deltaX + deltaZ * deltaZ;
    }

    @Override
    public double oreganised$getMotion() {
        return oreganized$Motion;
    }

    @ModifyExpressionValue(
            method = "canSpawnSprintParticle()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isSprinting()Z")
    )
    public boolean isSprinting(boolean original) {
        var self = (Entity) (Object) this;
        return original || (
                self.level().getBlockState(self.getBlockPosBelowThatAffectsMyMovement()).is(OBlocks.BONE_PILE.get())
                    && (self.getDeltaMovement().x != 0.0 || self.getDeltaMovement().z != 0.0)
        );
    }

}
