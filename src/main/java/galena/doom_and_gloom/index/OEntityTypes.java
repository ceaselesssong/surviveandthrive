package galena.doom_and_gloom.index;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.content.entity.DirtMound;
import galena.doom_and_gloom.content.entity.holler.Holler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DoomAndGloom.MOD_ID);

    public static final RegistryObject<EntityType<Holler>> HOLLER = ENTITIES.register("holler", () -> EntityType.Builder.of(Holler::new, MobCategory.CREATURE).sized(0.35F, 0.6F).clientTrackingRange(8).updateInterval(2).build("holler"));
    public static final RegistryObject<EntityType<DirtMound>> DIRT_MOUND = ENTITIES.register("dirt_mound", () -> EntityType.Builder.of(DirtMound::new, MobCategory.MISC).sized(0.8F, 0.25F).build("dirt_mound"));

}
