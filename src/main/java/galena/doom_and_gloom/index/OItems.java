package galena.doom_and_gloom.index;

import com.teamabnormals.blueprint.common.item.BlueprintRecordItem;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.content.item.BushHammerItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OItems {
    public static final ItemSubRegistryHelper HELPER = DoomAndGloom.REGISTRY_HELPER.getItemSubHelper();

    // Discs
    public static final RegistryObject<RecordItem> MUSIC_DISC_AFTERLIFE = HELPER.createItem("music_disc_afterlife", () -> new BlueprintRecordItem(13, OSoundEvents.MUSIC_DISC_AFTERLIFE, (new Item.Properties().stacksTo(1).rarity(Rarity.RARE)), 155));

    // Crafting Materials
    public static final RegistryObject<Item> BUSH_HAMMER = HELPER.createItem("bush_hammer", () -> new BushHammerItem(OItemTiers.LEAD, 2.5F, -2.8F, (new Item.Properties()).stacksTo(1)));

    // Misc
    public static final RegistryObject<SpawnEggItem> HOLLER_SPAWN_EGG = HELPER.createItem("holler_spawn_egg", () -> new ForgeSpawnEggItem(OEntityTypes.HOLLER, 0x84EED2, 0x24352F, new Item.Properties()));
}