package me.gleep.oreganized.registry;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.armors.OreganizedArmorMaterials;
import me.gleep.oreganized.items.*;
import me.gleep.oreganized.items.tiers.ElectrumPickaxeItem;
import me.gleep.oreganized.items.tiers.OreganizedTiers;
import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.gleep.oreganized.util.RegistryHandler.*;

public class OreganizedItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oreganized.MOD_ID);

    // Discs
    public static final RegistryObject<Item> MUSIC_DISC_PILLAGED_ITEM = ITEMS.register("music_disc_pillaged", () -> new ModMusicDisc(13, MUSIC_DISC_PILLAGED));
    public static final RegistryObject<Item> MUSIC_DISC_18_ITEM = ITEMS.register("music_disc_18", () -> new ModMusicDisc(14, MUSIC_DISC_18));
    public static final RegistryObject<Item> MUSIC_DISC_SHULK_ITEM = ITEMS.register("music_disc_shulk", () -> new ModMusicDisc(15, MUSIC_DISC_SHULK));

    // Crafting Materials
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", ItemBase::new);
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", SilverIngot::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", ItemBase::new);

    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", ItemBase::new);
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", ItemBase::new);
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", ItemBase::new);

    public static final RegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot", ItemBase::new);
    public static final RegistryObject<Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget", ItemBase::new);

    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget", () -> new ItemBase(true));


    // Tools
    public static final RegistryObject<Item> ELECTRUM_SWORD = ITEMS.register("electrum_sword",
            () -> new SwordItem(OreganizedTiers.ELECTRUM, 3, -2.2F, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT))
    );
    public static final RegistryObject<Item> ELECTRUM_PICKAXE = ITEMS.register("electrum_pickaxe",
            () -> new ElectrumPickaxeItem(OreganizedTiers.ELECTRUM, 1, -2.6F)
    );
    public static final RegistryObject<Item> ELECTRUM_AXE = ITEMS.register("electrum_axe",
            () -> new AxeItem(OreganizedTiers.ELECTRUM, 5.0F, -3.0F, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS))
    );
    public static final RegistryObject<Item> ELECTRUM_SHOVEL = ITEMS.register("electrum_shovel",
            () -> new ShovelItem(OreganizedTiers.ELECTRUM, 1.5F, -3.0F, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS))
    );
    public static final RegistryObject<Item> ELECTRUM_HOE = ITEMS.register("electrum_hoe",
            () -> new HoeItem(OreganizedTiers.ELECTRUM, -4, 0.0F, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS))
    );

    public static final RegistryObject<Item> BUSH_HAMMER = ITEMS.register("bush_hammer", BushHammer::new);

    // Misc tools
    public static final RegistryObject<Item> SILVER_MIRROR = ITEMS.register("silver_mirror", SilverMirror::new);

    public static final RegistryObject<Item> LEAD_BOLT = ITEMS.register("lead_bolt", ItemBase::new);

    public static final RegistryObject<Item> MOLTEN_LEAD_BUCKET = ITEMS.register("molten_lead_bucket", () -> new SolidBucketItem(OreganizedBlocks.MOLTEN_LEAD_BLOCK.get(),
            SoundEvents.BUCKET_EMPTY_LAVA, new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).stacksTo(1))
    );

    // Armor
    public static final RegistryObject<Item> ELECTRUM_HELMET = ITEMS.register("electrum_helmet",
            () -> new ElectrumArmorItem(OreganizedArmorMaterials.ELECTRUM, EquipmentSlot.HEAD)
    );
    public static final RegistryObject<Item> ELECTRUM_CHESTPLATE = ITEMS.register("electrum_chestplate",
            () -> new ElectrumArmorItem(OreganizedArmorMaterials.ELECTRUM, EquipmentSlot.CHEST)
    );
    public static final RegistryObject<Item> ELECTRUM_LEGGINGS = ITEMS.register("electrum_leggings",
            () -> new ElectrumArmorItem(OreganizedArmorMaterials.ELECTRUM, EquipmentSlot.LEGS)
    );
    public static final RegistryObject<Item> ELECTRUM_BOOTS = ITEMS.register("electrum_boots",
            () -> new ElectrumArmorItem(OreganizedArmorMaterials.ELECTRUM, EquipmentSlot.FEET)
    );

}
