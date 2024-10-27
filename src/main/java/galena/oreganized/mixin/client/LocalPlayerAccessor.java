package galena.oreganized.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessor {

    @Accessor
    List<AmbientSoundHandler> getAmbientSoundHandlers();

}
