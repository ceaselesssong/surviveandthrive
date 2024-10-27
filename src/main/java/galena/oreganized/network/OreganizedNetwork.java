package galena.oreganized.network;

import galena.oreganized.Oreganized;
import galena.oreganized.network.packet.*;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class OreganizedNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            Oreganized.modLoc("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;

        CHANNEL.registerMessage(id++, GargoyleParticlePacket.class, GargoyleParticlePacket::write, GargoyleParticlePacket::from, GargoyleParticlePacket::handle);
        CHANNEL.registerMessage(id++, DoorPushingPacket.class, DoorPushingPacket::write, DoorPushingPacket::from, DoorPushingPacket::handle);
        CHANNEL.registerMessage(id++, KineticHitPacket.class, KineticHitPacket::write, KineticHitPacket::from, KineticHitPacket::handle);
        CHANNEL.registerMessage(id++, SepulcherConsumesDeathPacket.class, SepulcherConsumesDeathPacket::write, SepulcherConsumesDeathPacket::from, SepulcherConsumesDeathPacket::handle);
        CHANNEL.registerMessage(id++, SepulcherRotsPacket.class, SepulcherRotsPacket::write, SepulcherRotsPacket::from, SepulcherRotsPacket::handle);
        CHANNEL.registerMessage(id++, StoneTabletUpdatePacket.class, StoneTabletUpdatePacket::write, StoneTabletUpdatePacket::from, StoneTabletUpdatePacket::handle);
        CHANNEL.registerMessage(id++, EngraveStoneTabletPacket.class, EngraveStoneTabletPacket::write, EngraveStoneTabletPacket::from, EngraveStoneTabletPacket::handle);
    }
}
