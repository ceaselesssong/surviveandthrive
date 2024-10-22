package galena.oreganized.network.packet;

import galena.oreganized.index.OBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SepulcherConsumesDeathPacket(Vec3 at) {

    public void write(FriendlyByteBuf buffer) {
        buffer.writeVector3f(at.toVector3f());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        var context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;

            var particles = Minecraft.getInstance().particleEngine;
            var state = OBlocks.ROTTING_FLESH.get().defaultBlockState();
            for (int i = 0; i < 20; i++) {
                var vec = at.add(level.random.nextDouble() - 0.5, level.random.nextDouble() * 2, level.random.nextDouble() - 0.5);
                particles.add(new TerrainParticle(level, vec.x, vec.y, vec.z, 0.0, 0.0, 0.0, state));
            }
        });

        context.setPacketHandled(true);
    }

    public static SepulcherConsumesDeathPacket from(FriendlyByteBuf buffer) {
        var at = new Vec3(buffer.readVector3f());
        return new SepulcherConsumesDeathPacket(at);
    }

}
