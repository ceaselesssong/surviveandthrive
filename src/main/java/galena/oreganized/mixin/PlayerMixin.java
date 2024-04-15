package galena.oreganized.mixin;

import galena.oreganized.world.IDoorProgressHolder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerMixin implements IDoorProgressHolder {

    @Unique
    private int oreganised$leadDoorOpeningProgress = 0;

    @Unique
    @Override
    public int oreganised$getOpeningProgress() {
        return oreganised$leadDoorOpeningProgress;
    }

    @Unique
    @Override
    public int oreganised$incrementOpeningProgress() {
        return ++oreganised$leadDoorOpeningProgress;
    }

    @Unique
    @Override
    public void oreganised$resetOpeningProgress() {
        oreganised$leadDoorOpeningProgress = 0;
    }

}
