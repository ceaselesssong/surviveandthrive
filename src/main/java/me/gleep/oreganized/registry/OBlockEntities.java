package me.gleep.oreganized.registry;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.entities.tileentities.ExposerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Oreganized.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExposerBlockEntity>> EXPOSER = BLOCK_ENTITIES.register("exposer", () ->
            BlockEntityType.Builder.of(ExposerBlockEntity::new, OBlocks.EXPOSER.get()).build(null)
    );
}
