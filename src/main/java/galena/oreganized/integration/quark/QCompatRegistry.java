package galena.oreganized.integration.quark;

import galena.oreganized.integration.quark.entity.Boltarang;
import galena.oreganized.integration.quark.item.BoltarangItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import vazkii.quark.content.tools.client.render.entity.PickarangRenderer;
import vazkii.quark.content.tools.module.PickarangModule;

import static galena.oreganized.index.OEntityTypes.ENTITIES;
import static galena.oreganized.index.OItems.ITEMS;

public class QCompatRegistry {

    public static final RegistryObject<EntityType<Boltarang>> BOLTARANG = ENTITIES.register("boltarang", () -> EntityType.Builder.<Boltarang>of(Boltarang::new, MobCategory.MISC).sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(10).build("bolterang"));

    public static final RegistryObject<BoltarangItem> BOLTARANG_ITEM = ITEMS.register("boltarang", () -> new BoltarangItem(PickarangModule.flamerang));

    public static void register() {

    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        EntityRenderers.register(BOLTARANG.get(), PickarangRenderer::new);
    }
}
