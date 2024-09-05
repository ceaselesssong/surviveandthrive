package galena.oreganized.compat.farmers_delight;

import galena.oreganized.index.OItemTiers;
import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.function.Function;

public class FarmersDelightCompat {

    public static final Function<Item.Properties, ? extends Item> KNIFE_FACTORY = (it) ->
            new KnifeItem(OItemTiers.ELECTRUM, 0.5F, -1.8F, it);

}
