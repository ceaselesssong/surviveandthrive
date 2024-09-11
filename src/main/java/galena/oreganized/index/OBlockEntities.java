package galena.oreganized.index;

import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import galena.oreganized.Oreganized;
import galena.oreganized.content.block.AnimatedDoorBlockEntity;
import galena.oreganized.content.entity.GargoyleBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBlockEntities {
    public static final BlockEntitySubRegistryHelper HELPER = Oreganized.REGISTRY_HELPER.getBlockEntitySubHelper();

    public static final RegistryObject<BlockEntityType<GargoyleBlockEntity>> GARGOYLE = HELPER.createBlockEntity("gargoyle", GargoyleBlockEntity::new, () -> Set.of(OBlocks.GARGOYLE.get()));
    public static final RegistryObject<BlockEntityType<AnimatedDoorBlockEntity>> ANIMATED_DOOR = HELPER.createBlockEntity("animated_door", AnimatedDoorBlockEntity::new, () -> Set.of(OBlocks.LEAD_DOOR.get(), OBlocks.LEAD_TRAPDOOR.get()));

}
