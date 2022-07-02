package me.gleep.oreganized.util;

import me.gleep.oreganized.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.gleep.oreganized.Oreganized.MOD_ID;
import static me.gleep.oreganized.registry.OBlocks.*;

public class RegistryHandler {
    //Mod
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    /*//////////////////////////////////            FLUIDS            //////////////////////////////////*/
    //MOLTEN LEAD
    /*public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID = FLUIDS.register("lead_fluid", LeadFluid.Source::new);
    public static final RegistryObject<ForgeFlowingFluid> LEAD_FLUID_FLOW = FLUIDS.register("lead_fluid_flow", LeadFluid.Flowing::new);
    public static final RegistryObject<LiquidBlock> LEAD_FLUID_BLOCK = BLOCKS.register("lead_fluid_block",
            () -> new LeadFluidBlock(RegistryHandler.LEAD_FLUID)
    );*/

    /*//////////////////////////////////            BLOCKS            //////////////////////////////////*/

    //Engraved Bricks
    public static final RegistryObject<EngravedBlock> ENGRAVED_NETHER_BRICKS = BLOCKS.register("engraved_nether_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_RED_NETHER_BRICKS = BLOCKS.register("engraved_red_nether_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_POLISHED_BLACKSTONE_BRICKS = BLOCKS.register("engraved_polished_blackstone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_BRICKS = BLOCKS.register("engraved_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS))
    );
    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_OXIDIZED_CUT_COPPER = BLOCKS.register("engraved_waxed_oxidized_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER))
    );
    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_WEATHERED_CUT_COPPER = BLOCKS.register("engraved_waxed_weathered_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER))
    );
    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_EXPOSED_CUT_COPPER = BLOCKS.register("engraved_waxed_exposed_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER))
    );
    public static final RegistryObject<CopperEngravedBlock> ENGRAVED_WAXED_CUT_COPPER = BLOCKS.register("engraved_waxed_cut_copper",
            () -> new CopperEngravedBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER))
    );
    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_CUT_COPPER = BLOCKS.register("engraved_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.copy(Blocks.CUT_COPPER), ENGRAVED_WAXED_CUT_COPPER.get())
    );
    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_EXPOSED_CUT_COPPER = BLOCKS.register("engraved_exposed_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER), ENGRAVED_WAXED_EXPOSED_CUT_COPPER.get())
    );
    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_WEATHERED_CUT_COPPER = BLOCKS.register("engraved_weathered_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER), ENGRAVED_WAXED_WEATHERED_CUT_COPPER.get())
    );
    public static final RegistryObject<EngravedWeatheringCopperBlock> ENGRAVED_OXIDIZED_CUT_COPPER = BLOCKS.register("engraved_oxidized_cut_copper",
            () -> new EngravedWeatheringCopperBlock(CustomWeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER), ENGRAVED_WAXED_OXIDIZED_CUT_COPPER.get())
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_DEEPSLATE_BRICKS = BLOCKS.register("engraved_deepslate_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_END_STONE_BRICKS = BLOCKS.register("engraved_end_stone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_PRISMARINE_BRICKS = BLOCKS.register("engraved_prismarine_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_QUARTZ_BRICKS = BLOCKS.register("engraved_quartz_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_STONE_BRICKS = BLOCKS.register("engraved_stone_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))
    );
    public static final RegistryObject<EngravedBlock> ENGRAVED_GLANCE_BRICKS = BLOCKS.register("engraved_glance_bricks",
            () -> new EngravedBlock(BlockBehaviour.Properties.copy(GLANCE_BRICKS.get()))
    );

    /*//////////////////////////////////            BLOCKTAGS            //////////////////////////////////*/
    //Oreganized
    public static final TagKey<Block> ENGRAVED_TEXTURED_BLOCKS_BLOCKTAG = BlockTags.create(new ResourceLocation(MOD_ID, "engraved_textured_blocks"));

    public static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}
