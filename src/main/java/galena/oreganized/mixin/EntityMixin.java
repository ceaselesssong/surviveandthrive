package galena.oreganized.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import galena.oreganized.index.OBlocks;
import galena.oreganized.world.IMotionHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IMotionHolder {

    @Shadow private Level level;

    @Shadow public abstract BlockPos getBlockPosBelowThatAffectsMyMovement();

    @Shadow public abstract Vec3 getDeltaMovement();

    @Shadow public abstract double getX();

    @Shadow public double xOld;
    @Shadow public double zOld;

    @Shadow public abstract double getZ();

    @Unique
    private double oreganized$Motion = 0.0;

    @Inject(
            method = "setOldPosAndRot",
            at = @At("HEAD")
    )
    private void updateMotion(CallbackInfo ci) {
        var deltaX = this.getX() - this.xOld;
        var deltaZ = this.getZ() - this.zOld;
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
        return original || (
                this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(OBlocks.BONE_PILE.get())
                    && (this.getDeltaMovement().x != 0.0 || this.getDeltaMovement().z != 0.0)
        );
    }

}
