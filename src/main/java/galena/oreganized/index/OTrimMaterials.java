package galena.oreganized.index;

import galena.oreganized.Oreganized;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class OTrimMaterials {
    public static final ResourceKey<TrimMaterial> LEAD = registerKey("lead");
    public static final ResourceKey<TrimMaterial> SILVER = registerKey("silver");
    public static final ResourceKey<TrimMaterial> ELECTRUM = registerKey("electrum");

    private static ResourceKey<TrimMaterial> registerKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, Oreganized.modLoc(name));
    }

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        register(context, LEAD, OItems.LEAD_INGOT.getHolder().get(), Style.EMPTY.withColor(6119556), 0.12F);
        register(context, SILVER, OItems.SILVER_INGOT.getHolder().get(), Style.EMPTY.withColor(10663869), 0.23F);
        register(context, ELECTRUM, OItems.ELECTRUM_INGOT.getHolder().get(), Style.EMPTY.withColor(13747326), 0.34F);
    }

    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Holder<Item> trimItem, Style color, float itemModelIndex) {
        TrimMaterial material = new TrimMaterial(trimKey.location().getPath(), trimItem, itemModelIndex, Map.of(), Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(color));
        context.register(trimKey, material);
    }
}
