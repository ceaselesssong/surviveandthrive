package galena.oreganized.index;

import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import galena.oreganized.Oreganized;
import galena.oreganized.content.entity.ExposerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBlockEntities {
    public static final BlockEntitySubRegistryHelper HELPER = Oreganized.REGISTRY_HELPER.getBlockEntitySubHelper();

    public static final RegistryObject<BlockEntityType<ExposerBlockEntity>> EXPOSER = HELPER.createBlockEntity("exposer", ExposerBlockEntity::new, () -> Set.of(OBlocks.EXPOSER.get()));

}
