package galena.oreganized.network.packet;

import galena.oreganized.content.entity.GargoyleBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GargoyleParticlePacket {

    private final BlockPos pos;

    public GargoyleParticlePacket(BlockPos pos) {
        this.pos = pos;
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        var context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;

            var blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof GargoyleBlockEntity gargoyle) {
                gargoyle.spawnParticles();
            }
        });

        context.setPacketHandled(true);
    }

    public static GargoyleParticlePacket from(FriendlyByteBuf buffer) {
        var pos = buffer.readBlockPos();
        return new GargoyleParticlePacket(pos);
    }

}
