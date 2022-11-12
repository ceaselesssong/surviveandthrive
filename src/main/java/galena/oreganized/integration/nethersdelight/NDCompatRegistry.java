package galena.oreganized.integration.nethersdelight;

import galena.oreganized.index.OItemTiers;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import umpaz.nethersdelight.NethersDelight;
import umpaz.nethersdelight.common.item.MacheteItem;

import static galena.oreganized.index.OItems.ITEMS;

public class NDCompatRegistry {

    public static final RegistryObject<MacheteItem> ELECTRUM_MACHETE = ITEMS.register("electrum_machete", () -> new MacheteItem(OItemTiers.ELECTRUM, 2, -2.4F, (new Item.Properties()).tab(NethersDelight.CREATIVE_TAB)));

    public static void register() {

    }
}
