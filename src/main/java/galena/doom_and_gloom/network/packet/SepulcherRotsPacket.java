package galena.doom_and_gloom.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public record SepulcherRotsPacket(BlockPos at) {

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(at);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        var context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;

            var effectColor = new Color(8889187);
            for (int i = 0; i < 20; i++) {
                var vec = Vec3.atBottomCenterOf(at).add(level.random.nextDouble() - 0.5, 0.8, level.random.nextDouble() - 0.5);
                level.addParticle(ParticleTypes.ENTITY_EFFECT, vec.x, vec.y, vec.z, effectColor.getRed() / 255D, effectColor.getGreen() / 255D, effectColor.getBlue() / 255D);
            }
        });

        context.setPacketHandled(true);
    }

    public static SepulcherRotsPacket from(FriendlyByteBuf buffer) {
        var at = buffer.readBlockPos();
        return new SepulcherRotsPacket(at);
    }

}
