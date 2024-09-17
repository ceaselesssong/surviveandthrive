package galena.oreganized.network.packet;

import galena.oreganized.world.KineticDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record KineticHitPacket(int target) {

    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(target);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        var context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;

            var target = level.getEntity(target());
            if (target == null) return;

            KineticDamage.spawnParticles(target);
        });

        context.setPacketHandled(true);
    }

    public static KineticHitPacket from(FriendlyByteBuf buffer) {
        var target = buffer.readInt();
        return new KineticHitPacket(target);
    }

}
