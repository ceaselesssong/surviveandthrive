package galena.oreganized.integration.farmersdelight;

import galena.oreganized.index.OItemTiers;
import galena.oreganized.index.OItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class FDCompatRegistry {

    public static final RegistryObject<Item> ELECTRUM_KNIFE = OItems.ITEMS.register("electrum_knife",
            () -> new KnifeItem(OItemTiers.ELECTRUM, 0.5F, -1.8F, (new Item.Properties()).tab(FarmersDelight.CREATIVE_TAB)));

    public static void register() {

    }
}
