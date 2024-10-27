package galena.oreganized.mixin.client;

import galena.oreganized.client.FogAmbientSoundHandler;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void addFogSoundHandler(Minecraft minecraft, ClientLevel level, ClientPacketListener listener, StatsCounter state, ClientRecipeBook recipeBook, boolean pWasShiftKeyDown, boolean pWasSprinting, CallbackInfo ci) {
        var accessor = (LocalPlayerAccessor) this;
        var self = (LocalPlayer) (Object) this;
        accessor.getAmbientSoundHandlers().add(new FogAmbientSoundHandler(self, minecraft.getSoundManager()));
    }

}
