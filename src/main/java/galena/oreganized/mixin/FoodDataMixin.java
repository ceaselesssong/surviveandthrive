package galena.oreganized.mixin;

import galena.oreganized.index.OEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @Redirect(
            method = "tick(Lnet/minecraft/world/entity/player/Player;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V")
    )
    private void modifyHealthAmount(Player player, float value) {
        if (player.hasEffect(OEffects.STUNNING.get())) player.heal(value / 2);
        else player.heal(value);
    }

}
