package galena.oreganized.index;

import com.google.common.collect.ImmutableMap;
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

    public static final Map<String, Float> TRIM_MATERIALS = new ImmutableMap.Builder<String, Float>()
            .put("quartz", 0.1F)
            .put("iron", 0.2F)
            .put("netherite", 0.3F)
            .put("redstone", 0.4F)
            .put("copper", 0.5F)
            .put("gold", 0.6F)
            .put("emerald", 0.7F)
            .put("diamond", 0.8F)
            .put("lapis", 0.9F)
            .put("amethyst", 1.0F)
            .put("lead", 0.9F)
            .put("silver", 0.2F)
            .put("electrum", 0.6F)
            .build();

    public static final ResourceKey<TrimMaterial> LEAD = registerKey("lead");
    public static final ResourceKey<TrimMaterial> SILVER = registerKey("silver");
    public static final ResourceKey<TrimMaterial> ELECTRUM = registerKey("electrum");

    private static ResourceKey<TrimMaterial> registerKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, Oreganized.modLoc(name));
    }

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        register(context, LEAD, OItems.LEAD_INGOT.getHolder().get(), Style.EMPTY.withColor(6119556));
        register(context, SILVER, OItems.SILVER_INGOT.getHolder().get(), Style.EMPTY.withColor(10663869));
        register(context, ELECTRUM, OItems.ELECTRUM_INGOT.getHolder().get(), Style.EMPTY.withColor(13747326));
    }

    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Holder<Item> trimItem, Style color) {
        float itemModelIndex = TRIM_MATERIALS.get(trimKey.location().getPath());
        TrimMaterial material = new TrimMaterial(trimKey.location().getPath(), trimItem, itemModelIndex, Map.of(), Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(color));
        context.register(trimKey, material);
    }
}
