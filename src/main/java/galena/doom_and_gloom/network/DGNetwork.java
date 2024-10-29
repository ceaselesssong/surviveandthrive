package galena.doom_and_gloom.network;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.network.packet.EngraveStoneTabletPacket;
import galena.doom_and_gloom.network.packet.SepulcherConsumesDeathPacket;
import galena.doom_and_gloom.network.packet.SepulcherRotsPacket;
import galena.doom_and_gloom.network.packet.StoneTabletUpdatePacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class DGNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            DoomAndGloom.modLoc("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;

        CHANNEL.registerMessage(id++, SepulcherConsumesDeathPacket.class, SepulcherConsumesDeathPacket::write, SepulcherConsumesDeathPacket::from, SepulcherConsumesDeathPacket::handle);
        CHANNEL.registerMessage(id++, SepulcherRotsPacket.class, SepulcherRotsPacket::write, SepulcherRotsPacket::from, SepulcherRotsPacket::handle);
        CHANNEL.registerMessage(id++, StoneTabletUpdatePacket.class, StoneTabletUpdatePacket::write, StoneTabletUpdatePacket::from, StoneTabletUpdatePacket::handle);
        CHANNEL.registerMessage(id++, EngraveStoneTabletPacket.class, EngraveStoneTabletPacket::write, EngraveStoneTabletPacket::from, EngraveStoneTabletPacket::handle);
    }
}
