package galena.oreganized.integration.modestmining;

import com.ncpbails.modestmining.item.ModItems;
import com.ncpbails.modestmining.item.custom.tools.HatchetItem;
import com.ncpbails.modestmining.item.custom.weapons.GlaiveItem;
import com.ncpbails.modestmining.item.custom.weapons.HammerItem;
import com.ncpbails.modestmining.item.custom.weapons.KatanaItem;
import com.ncpbails.modestmining.item.custom.weapons.MaceItem;
import galena.oreganized.content.item.OItem;
import galena.oreganized.index.OItemTiers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.registries.RegistryObject;

import static galena.oreganized.index.OItems.ITEMS;

public class MMCompatRegistry {

    public static final RegistryObject<Item> ELECTRUM_PLATING = ITEMS.register("electrum_plating", () -> new OItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> ELECTRUM_PIECE = ITEMS.register("electrum_piece", () -> new OItem(CreativeModeTab.TAB_MISC));

    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new OItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust", () -> new OItem(CreativeModeTab.TAB_MISC));

    public static final RegistryObject<Item> LEAD_HATCHET = ITEMS.register("lead_hatchet", () -> new HatchetItem(OItemTiers.LEAD, 4, -3, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> ELECTRUM_HAMMER = ITEMS.register("electrum_hammer", () -> new HammerItem(OItemTiers.ELECTRUM, 7, -3.2F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> ELECTRUM_GLAIVE = ITEMS.register("electrum_glaive", () -> new GlaiveItem(OItemTiers.ELECTRUM, 3, -3.3F, 3.0F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> ELECTRUM_KATANA = ITEMS.register("electrum_katana", () -> new KatanaItem(OItemTiers.ELECTRUM, 2, -2.7F, 3.0F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> ELECTRUM_MOUNTAIN_AXE = ITEMS.register("electrum_mountain_axe", () -> new PickaxeItem(OItemTiers.ELECTRUM, 1, -2.8F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> ELECTRUM_MACE = ITEMS.register("electrum_mace", () -> new MaceItem(OItemTiers.ELECTRUM, 1, -3.2F, 2.0F, 2.0F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static void register() {

    }
}
