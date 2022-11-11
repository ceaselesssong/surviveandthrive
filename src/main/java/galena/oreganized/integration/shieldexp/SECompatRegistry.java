package galena.oreganized.integration.shieldexp;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.shieldexp.items.NewShieldItem;

import static galena.oreganized.index.OItems.ITEMS;
import static org.infernalstudios.shieldexp.init.ItemsInit.SHIELDS;

public class SECompatRegistry {

    //movement speed factors are modified to align with the vanilla shield modifier, actual results are 75%, 90%, 60%, 40~%, and 20~% as intended
    public static final RegistryObject<NewShieldItem> ELECTRUM_SHIELD = registerShield("electrum_shield", 640, 35, 0.60F, 0.70F, 4);

    public static RegistryObject<NewShieldItem> registerShield(String id, int durability, int blockTicks, float speedFactor, double damageBack, int parryTicks) {
        RegistryObject shield = ITEMS.register(id, () -> new NewShieldItem((new Item.Properties()).durability(durability).tab(CreativeModeTab.TAB_COMBAT), blockTicks, speedFactor, damageBack, parryTicks));
        SHIELDS.add(shield);
        return shield;
    }

    public static void register() {

    }
}
