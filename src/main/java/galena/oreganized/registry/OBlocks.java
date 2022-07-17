package galena.oreganized.registry;

import galena.oreganized.Oreganized;
import galena.oreganized.block.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static galena.oreganized.block.MoltenLeadCauldronBlock.moltenStageEmission;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oreganized.MOD_ID);


    public static final RegistryObject<Block> MOLTEN_LEAD = register("molten_lead_block", () ->
            new MoltenLeadBlock(BlockBehaviour.Properties.of(OMaterials.MOLTEN_LEAD).strength(-1.0F, 3600000.0F)
                    .dynamicShape().lightLevel((light) -> 8)));
    public static final RegistryObject<Block> MOLTEN_LEAD_CAULDRON = register("molten_lead_cauldron", () -> new MoltenLeadCauldronBlock(BlockBehaviour.Properties.copy(Blocks.LAVA_CAULDRON).lightLevel(moltenStageEmission())));

    // Glance
    public static final RegistryObject<Block> GLANCE = register("glance", () -> new Block(BlockBehaviour.Properties.of(OMaterials.GLANCE).explosionResistance(6).strength(1.5F)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> POLISHED_GLANCE = register("polished_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GLANCE_BRICKS = register("glance_bricks", () -> new Block(BlockBehaviour.Properties.copy(POLISHED_GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_GLANCE = register("chiseled_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<SlabBlock> GLANCE_SLAB = register("glance_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<SlabBlock> GLANCE_BRICK_SLAB = register("glance_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE_BRICKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<StairBlock> GLANCE_STAIRS = register("glance_stairs", () -> new StairBlock(GLANCE.get()::defaultBlockState, BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<StairBlock> GLANCE_BRICK_STAIRS = register("glance_brick_stairs", () -> new StairBlock(GLANCE_BRICKS.get()::defaultBlockState, BlockBehaviour.Properties.copy(GLANCE_BRICKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<WallBlock> GLANCE_WALL = register("glance_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<WallBlock> GLANCE_BRICK_WALL = register("glance_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE_BRICKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SPOTTED_GLANCE = register("spotted_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> WAXED_SPOTTED_GLANCE = register("waxed_spotted_glance", () -> new Block(BlockBehaviour.Properties.copy(SPOTTED_GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);

    // Ores
    public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> LEAD_ORE = register("lead_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.STONE), UniformInt.of(0, 3)), CreativeModeTab.TAB_BUILDING_BLOCKS
    );
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = register("deepslate_lead_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    // Storage Blocks
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = register("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = register("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () -> new SilverBlock(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0F, 6.0F).sound(SoundType.METAL)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LEAD_BLOCK = register("lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)), CreativeModeTab.TAB_BUILDING_BLOCKS
    );
    public static final RegistryObject<Block> ELECTRUM_BLOCK = register("electrum_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    // Redstone components
    public static final RegistryObject<Block> EXPOSER = register("exposer", () -> new ExposerBlock(BlockBehaviour.Properties.copy(Blocks.OBSERVER)), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> SHRAPNEL_BOMB = register("shrapnel_bomb", () -> new ShrapnelBombBlock(BlockBehaviour.Properties.copy(Blocks.TNT)), CreativeModeTab.TAB_REDSTONE);

    // Crystal Glass
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS = register("black_crystal_glass", () -> new CrystalGlassBlock(DyeColor.BLACK, BlockBehaviour.Properties.copy(Blocks.BLACK_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS = register("blue_crystal_glass", () -> new CrystalGlassBlock(DyeColor.BLUE, BlockBehaviour.Properties.copy(Blocks.BLUE_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS = register("brown_crystal_glass", () -> new CrystalGlassBlock(DyeColor.BROWN, BlockBehaviour.Properties.copy(Blocks.BROWN_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS = register("cyan_crystal_glass", () -> new CrystalGlassBlock(DyeColor.CYAN, BlockBehaviour.Properties.copy(Blocks.CYAN_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS = register("gray_crystal_glass", () -> new CrystalGlassBlock(DyeColor.GRAY, BlockBehaviour.Properties.copy(Blocks.GRAY_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS = register("green_crystal_glass", () -> new CrystalGlassBlock(DyeColor.GREEN, BlockBehaviour.Properties.copy(Blocks.GREEN_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS = register("light_blue_crystal_glass", () -> new CrystalGlassBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS = register("light_gray_crystal_glass", () -> new CrystalGlassBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS = register("lime_crystal_glass", () -> new CrystalGlassBlock(DyeColor.LIME, BlockBehaviour.Properties.copy(Blocks.LIME_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS = register("magenta_crystal_glass", () -> new CrystalGlassBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.copy(Blocks.MAGENTA_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS = register("orange_crystal_glass", () -> new CrystalGlassBlock(DyeColor.ORANGE, BlockBehaviour.Properties.copy(Blocks.ORANGE_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS = register("pink_crystal_glass", () -> new CrystalGlassBlock(DyeColor.PINK, BlockBehaviour.Properties.copy(Blocks.PINK_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS = register("purple_crystal_glass", () -> new CrystalGlassBlock(DyeColor.PURPLE, BlockBehaviour.Properties.copy(Blocks.PURPLE_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS = register("red_crystal_glass", () -> new CrystalGlassBlock(DyeColor.RED, BlockBehaviour.Properties.copy(Blocks.RED_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS = register("white_crystal_glass", () -> new CrystalGlassBlock(DyeColor.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS = register("yellow_crystal_glass", () -> new CrystalGlassBlock(DyeColor.YELLOW, BlockBehaviour.Properties.copy(Blocks.YELLOW_STAINED_GLASS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final List<RegistryObject<Block>> CRYSTAL_GLASS = List.of(
            BLACK_CRYSTAL_GLASS, BLUE_CRYSTAL_GLASS, BROWN_CRYSTAL_GLASS, CYAN_CRYSTAL_GLASS, GRAY_CRYSTAL_GLASS, GREEN_CRYSTAL_GLASS,
            LIGHT_BLUE_CRYSTAL_GLASS, LIGHT_GRAY_CRYSTAL_GLASS, LIME_CRYSTAL_GLASS, MAGENTA_CRYSTAL_GLASS, ORANGE_CRYSTAL_GLASS,
            PINK_CRYSTAL_GLASS, PURPLE_CRYSTAL_GLASS, RED_CRYSTAL_GLASS, WHITE_CRYSTAL_GLASS, YELLOW_CRYSTAL_GLASS
    );

    // Crystal Glass Panes
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS_PANE = register("black_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.BLACK, BlockBehaviour.Properties.copy(Blocks.BLACK_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS_PANE = register("blue_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.BLUE, BlockBehaviour.Properties.copy(Blocks.BLUE_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS_PANE = register("brown_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.BROWN, BlockBehaviour.Properties.copy(Blocks.BROWN_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS_PANE = register("cyan_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.CYAN, BlockBehaviour.Properties.copy(Blocks.CYAN_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS_PANE = register("gray_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.GRAY, BlockBehaviour.Properties.copy(Blocks.GRAY_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS_PANE = register("green_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.GREEN, BlockBehaviour.Properties.copy(Blocks.GREEN_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS_PANE = register("light_blue_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS_PANE = register("light_gray_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS_PANE = register("lime_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.LIME, BlockBehaviour.Properties.copy(Blocks.LIME_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS_PANE = register("magenta_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.copy(Blocks.MAGENTA_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS_PANE = register("orange_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.ORANGE, BlockBehaviour.Properties.copy(Blocks.ORANGE_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS_PANE = register("pink_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.PINK, BlockBehaviour.Properties.copy(Blocks.PINK_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS_PANE = register("purple_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.PURPLE, BlockBehaviour.Properties.copy(Blocks.PURPLE_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS_PANE = register("red_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.RED, BlockBehaviour.Properties.copy(Blocks.RED_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS_PANE = register("white_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS_PANE = register("yellow_crystal_glass_pane", () -> new CrystalGlassPaneBlock(DyeColor.YELLOW, BlockBehaviour.Properties.copy(Blocks.YELLOW_STAINED_GLASS)), CreativeModeTab.TAB_DECORATIONS);
    public static final List<RegistryObject<Block>> CRYSTAL_GLASS_PANES = List.of(
            BLACK_CRYSTAL_GLASS_PANE, BLUE_CRYSTAL_GLASS_PANE, BROWN_CRYSTAL_GLASS_PANE, CYAN_CRYSTAL_GLASS_PANE, GRAY_CRYSTAL_GLASS_PANE,
            GREEN_CRYSTAL_GLASS_PANE, LIGHT_BLUE_CRYSTAL_GLASS_PANE, LIGHT_GRAY_CRYSTAL_GLASS_PANE,
            LIME_CRYSTAL_GLASS_PANE, MAGENTA_CRYSTAL_GLASS_PANE, ORANGE_CRYSTAL_GLASS_PANE, PINK_CRYSTAL_GLASS_PANE, PURPLE_CRYSTAL_GLASS_PANE,
            RED_CRYSTAL_GLASS_PANE, WHITE_CRYSTAL_GLASS_PANE, YELLOW_CRYSTAL_GLASS_PANE
    );

    // Waxed Concrete Powder


    public static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> block, CreativeModeTab tab) {
        RegistryObject<B> blocks = BLOCKS.register(name, block);
        OItems.ITEMS.register(name, () -> new BlockItem(blocks.get(), new Item.Properties().tab(tab)));
        return blocks;
    }

    public static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> block) {
        return BLOCKS.register(name, block);
    }

    // For Blocks that only register when other mods are loaded (Compatibility)
    public static <B extends Block> RegistryObject<B> register(String modid, String name, Supplier<? extends B> block, CreativeModeTab tab) {
        if (ModList.get().isLoaded(modid)) {
            return register(name, block, tab);
        }
        return null;
    }
}
