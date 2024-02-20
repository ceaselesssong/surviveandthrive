package galena.oreganized.index;

import galena.oreganized.Oreganized;
import galena.oreganized.content.item.BushHammerItem;
import galena.oreganized.content.item.ElectrumArmorItem;
import galena.oreganized.content.item.LeadBoltItem;
import galena.oreganized.content.item.MinecartShrapnelBombItem;
import galena.oreganized.content.item.OMusicDiscItem;
import galena.oreganized.content.item.OSmithingTemplateItem;
import galena.oreganized.content.item.OSwordItem;
import galena.oreganized.content.item.SilverMirrorItem;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oreganized.MOD_ID);

    // Discs
    public static final RegistryObject<RecordItem> MUSIC_DISC_STRUCTURE = ITEMS.register("music_disc_structure", () -> new OMusicDiscItem(13, OSoundEvents.MUSIC_DISC_STRUCTURE, (new Item.Properties()), 2980));

    // Crafting Materials
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget", () -> new Item(new Item.Properties()));


    // Tools
    public static final RegistryObject<Item> BUSH_HAMMER = ITEMS.register("bush_hammer", () -> new BushHammerItem(OItemTiers.LEAD, 2.5F, -2.8F, (new Item.Properties()).stacksTo(1)));

    public static final RegistryObject<Item> ELECTRUM_SWORD = ITEMS.register("electrum_sword", () -> new OSwordItem(OItemTiers.ELECTRUM, 3, -2.2F, (new Item.Properties())));
    public static final RegistryObject<Item> ELECTRUM_SHOVEL = ITEMS.register("electrum_shovel", () -> new ShovelItem(OItemTiers.ELECTRUM, 1.5F, -2.8F, (new Item.Properties())));
    public static final RegistryObject<Item> ELECTRUM_PICKAXE = ITEMS.register("electrum_pickaxe", () -> new PickaxeItem(OItemTiers.ELECTRUM, 1, -2.6F, (new Item.Properties())));
    public static final RegistryObject<Item> ELECTRUM_AXE = ITEMS.register("electrum_axe", () -> new AxeItem(OItemTiers.ELECTRUM, 6.0F, -2.8F, (new Item.Properties())));
    public static final RegistryObject<Item> ELECTRUM_HOE = ITEMS.register("electrum_hoe", () -> new HoeItem(OItemTiers.ELECTRUM, 0, -2.8F, (new Item.Properties())));

    // Misc Tools
    public static final RegistryObject<Item> SILVER_MIRROR = ITEMS.register("silver_mirror", () -> new SilverMirrorItem(new Item.Properties().stacksTo(1)));
    //public static final RegistryObject<Item> BROKEN_SILVER_MIRROR = ITEMS.register("broken_silver_mirror", () -> new OItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> MOLTEN_LEAD_BUCKET = ITEMS.register("molten_lead_bucket", () -> new BucketItem(OFluids.MOLTEN_LEAD, new Item.Properties().stacksTo(1)));

    // Armor
    public static final RegistryObject<Item> ELECTRUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("electrum_upgrade_smithing_template",
            OSmithingTemplateItem::createElectrumUpgradeTemplate);
    public static final RegistryObject<ArmorItem> ELECTRUM_HELMET = ITEMS.register("electrum_helmet",
            () -> new ElectrumArmorItem(ArmorItem.Type.HELMET));
    public static final RegistryObject<ArmorItem> ELECTRUM_CHESTPLATE = ITEMS.register("electrum_chestplate",
            () -> new ElectrumArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<ArmorItem> ELECTRUM_LEGGINGS = ITEMS.register("electrum_leggings",
            () -> new ElectrumArmorItem(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<ArmorItem> ELECTRUM_BOOTS = ITEMS.register("electrum_boots",
            () -> new ElectrumArmorItem(ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> LEAD_BOLT = ITEMS.register("lead_bolt", () -> new LeadBoltItem(new Item.Properties()));

    // Transportation
    public static final RegistryObject<Item> SHRAPNEL_BOMB_MINECART = ITEMS.register("shrapnel_bomb_minecart", () -> new MinecartShrapnelBombItem(AbstractMinecart.Type.TNT, OEntityTypes.SHRAPNEL_BOMB_MINECART));
}