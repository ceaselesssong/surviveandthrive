package galena.oreganized.mixin;

import galena.oreganized.network.OreganizedNetwork;
import galena.oreganized.network.packet.DoorPushingPacket;
import galena.oreganized.world.IDoorProgressHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends Entity implements IDoorProgressHolder {

    @Unique
    private int oreganised$leadDoorOpeningProgress = 0;

    @Unique
    private long oreganised$lastPress = 0;

    public PlayerMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    @Override
    public int oreganised$getOpeningProgress() {
        return oreganised$leadDoorOpeningProgress;
    }

    @Unique
    private void oreganized$syncProgress(boolean pushing) {
        var self = (Player) (Object) this;

        if (self instanceof ServerPlayer) {
            var packet = new DoorPushingPacket(self.getUUID(), pushing);
            OreganizedNetwork.CHANNEL.send(PacketDistributor.DIMENSION.with(self.level()::dimension), packet);
        }
    }

    @Unique
    @Override
    public int oreganised$incrementOpeningProgress() {
        oreganised$lastPress = this.level().getGameTime();
        oreganized$syncProgress(true);
        return ++oreganised$leadDoorOpeningProgress;
    }

    @Unique
    @Override
    public void oreganised$resetOpeningProgress() {
        oreganised$leadDoorOpeningProgress = 0;
        oreganized$syncProgress(false);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void oreganised$onTick(CallbackInfo ci) {
        if (oreganised$leadDoorOpeningProgress == 0) return;
        if (this.level().isClientSide) return;
        var time = this.level().getGameTime();
        if (time - oreganised$lastPress > 6) oreganised$resetOpeningProgress();
    }

}
