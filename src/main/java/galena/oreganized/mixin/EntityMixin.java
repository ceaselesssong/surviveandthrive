package galena.oreganized.mixin;

import galena.oreganized.world.IMotionHolder;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements IMotionHolder {

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
}
