package me.gleep.oreganized.registry;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.block.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oreganized.MOD_ID);

    public static final RegistryObject<Block> MOLTEN_LEAD_BLOCK = register("molten_lead_block", () ->
            new MoltenLeadBlock(BlockBehaviour.Properties.of(OMaterials.MOLTEN_LEAD).strength( -1.0F , 3600000.0F )
                    .sound(OSoundTypes.MOLTEN_LEAD).dynamicShape()
                    .lightLevel((i) -> 8).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LEAD_CAULDRON = register("cauldron", ModCauldron::new);

    // Glance
    public static final RegistryObject<Block> GLANCE = register("glance", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIORITE).color(MaterialColor.TERRACOTTA_PURPLE)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<SlabBlock> GLANCE_SLAB = register("glance_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<SlabBlock> GLANCE_BRICKS_SLAB = register("glance_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<StairBlock> GLANCE_STAIRS = register("glance_stairs", () -> new StairBlock(GLANCE.get()::defaultBlockState,
            BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<StairBlock> GLANCE_BRICKS_STAIRS = register("glance_bricks_stairs", () -> new StairBlock(GLANCE.get()::defaultBlockState,
            BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<WallBlock> GLANCE_WALL = register("glance_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<WallBlock> GLANCE_BRICKS_WALL = register("glance_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> POLISHED_GLANCE = register("polished_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GLANCE_BRICKS = register("glance_bricks", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_GLANCE = register("chiseled_glance", () -> new Block(BlockBehaviour.Properties.copy(GLANCE.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SPOTTED_GLANCE = register("spotted_glance", SpottedGlance::new, CreativeModeTab.TAB_BUILDING_BLOCKS);
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
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", SilverBlock::new, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LEAD_BLOCK = register("lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)), CreativeModeTab.TAB_BUILDING_BLOCKS
    );
    public static final RegistryObject<Block> ELECTRUM_BLOCK = register("electrum_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)), CreativeModeTab.TAB_BUILDING_BLOCKS);


    // Crystal Glass
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS = register("black_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLACK), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS = register("blue_crystal_glass", () -> new CrystalGlassColored(DyeColor.BLUE), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS = register("brown_crystal_glass", () -> new CrystalGlassColored(DyeColor.BROWN), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS = register("cyan_crystal_glass", () -> new CrystalGlassColored(DyeColor.CYAN), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS = register("gray_crystal_glass", () -> new CrystalGlassColored(DyeColor.GRAY), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS = register("green_crystal_glass", () -> new CrystalGlassColored(DyeColor.GREEN), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS = register("light_blue_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS = register("light_gray_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS = register("lime_crystal_glass", () -> new CrystalGlassColored(DyeColor.LIME), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS = register("magenta_crystal_glass", () -> new CrystalGlassColored(DyeColor.MAGENTA), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS = register("orange_crystal_glass", () -> new CrystalGlassColored(DyeColor.ORANGE), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS = register("pink_crystal_glass", () -> new CrystalGlassColored(DyeColor.PINK), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS = register("purple_crystal_glass", () -> new CrystalGlassColored(DyeColor.PURPLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS = register("red_crystal_glass", () -> new CrystalGlassColored(DyeColor.RED), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS = register("white_crystal_glass", () -> new CrystalGlassColored(DyeColor.WHITE), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS = register("yellow_crystal_glass", () -> new CrystalGlassColored(DyeColor.YELLOW), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final List<RegistryObject<Block>> CRYSTAL_GLASS = List.of(
            BLACK_CRYSTAL_GLASS, BLUE_CRYSTAL_GLASS, BROWN_CRYSTAL_GLASS, CYAN_CRYSTAL_GLASS, GRAY_CRYSTAL_GLASS, GREEN_CRYSTAL_GLASS,
            LIGHT_BLUE_CRYSTAL_GLASS, LIGHT_GRAY_CRYSTAL_GLASS, LIME_CRYSTAL_GLASS, LIME_CRYSTAL_GLASS, MAGENTA_CRYSTAL_GLASS, ORANGE_CRYSTAL_GLASS,
            PINK_CRYSTAL_GLASS, PURPLE_CRYSTAL_GLASS, RED_CRYSTAL_GLASS, WHITE_CRYSTAL_GLASS, YELLOW_CRYSTAL_GLASS
    );
    public static final RegistryObject<Block> BLACK_CRYSTAL_GLASS_PANE = register("black_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BLACK), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLUE_CRYSTAL_GLASS_PANE = register("blue_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BROWN_CRYSTAL_GLASS_PANE = register("brown_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.BROWN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CYAN_CRYSTAL_GLASS_PANE = register("cyan_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.CYAN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GRAY_CRYSTAL_GLASS_PANE = register("gray_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> GREEN_CRYSTAL_GLASS_PANE = register("green_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.GREEN), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_BLUE_CRYSTAL_GLASS_PANE = register("light_blue_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIGHT_GRAY_CRYSTAL_GLASS_PANE = register("light_gray_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> LIME_CRYSTAL_GLASS_PANE = register("lime_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.LIME), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> MAGENTA_CRYSTAL_GLASS_PANE = register("magenta_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.MAGENTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ORANGE_CRYSTAL_GLASS_PANE = register("orange_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.ORANGE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PINK_CRYSTAL_GLASS_PANE = register("pink_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.PINK), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> PURPLE_CRYSTAL_GLASS_PANE = register("purple_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.PURPLE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> RED_CRYSTAL_GLASS_PANE = register("red_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.RED), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> WHITE_CRYSTAL_GLASS_PANE = register("white_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.WHITE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> YELLOW_CRYSTAL_GLASS_PANE = register("yellow_crystal_glass_pane", () -> new CrystalGlassPaneColored(DyeColor.YELLOW), CreativeModeTab.TAB_DECORATIONS);
    public static final List<RegistryObject<Block>> CRYSTAL_GLASS_PANES = List.of(
            BLACK_CRYSTAL_GLASS_PANE, BLUE_CRYSTAL_GLASS_PANE, BROWN_CRYSTAL_GLASS_PANE, CYAN_CRYSTAL_GLASS_PANE, GRAY_CRYSTAL_GLASS_PANE,
            GREEN_CRYSTAL_GLASS_PANE, LIGHT_BLUE_CRYSTAL_GLASS_PANE, LIGHT_GRAY_CRYSTAL_GLASS_PANE, LIME_CRYSTAL_GLASS_PANE,
            LIME_CRYSTAL_GLASS_PANE, MAGENTA_CRYSTAL_GLASS_PANE, ORANGE_CRYSTAL_GLASS_PANE, PINK_CRYSTAL_GLASS_PANE, PURPLE_CRYSTAL_GLASS_PANE,
            RED_CRYSTAL_GLASS_PANE, WHITE_CRYSTAL_GLASS_PANE, YELLOW_CRYSTAL_GLASS_PANE
    );


    // Waxed Concrete Powder
    public static final RegistryObject<FallingBlock> WAXED_WHITE_CONCRETE_POWDER = register("waxed_white_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.WHITE).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_ORANGE_CONCRETE_POWDER = register("waxed_orange_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.ORANGE).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_MAGENTA_CONCRETE_POWDER = register("waxed_magenta_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.MAGENTA).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_LIGHT_BLUE_CONCRETE_POWDER = register("waxed_light_blue_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIGHT_BLUE).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_YELLOW_CONCRETE_POWDER = register("waxed_yellow_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.YELLOW).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_LIME_CONCRETE_POWDER = register("waxed_lime_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIME).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_PINK_CONCRETE_POWDER = register("waxed_pink_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.PINK).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_GRAY_CONCRETE_POWDER = register("waxed_gray_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.GRAY).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_LIGHT_GRAY_CONCRETE_POWDER = register("waxed_light_gray_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.LIGHT_GRAY).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_CYAN_CONCRETE_POWDER = register("waxed_cyan_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.CYAN).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_PURPLE_CONCRETE_POWDER = register("waxed_purple_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.PURPLE).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_BLUE_CONCRETE_POWDER = register("waxed_blue_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BLUE).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_BROWN_CONCRETE_POWDER = register("waxed_brown_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BROWN).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_GREEN_CONCRETE_POWDER = register("waxed_green_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.GREEN).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_RED_CONCRETE_POWDER = register("waxed_red_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.RED).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<FallingBlock> WAXED_BLACK_CONCRETE_POWDER = register("waxed_black_concrete_powder", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, DyeColor.BLACK).strength(0.5F).sound(SoundType.SAND)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    // Redstone
    public static final RegistryObject<Block> EXPOSER = register("exposer", ExposerBlock::new, CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> SHRAPNEL_BOMB = register("shrapnel_bomb", () -> new ShrapnelBombBlock(BlockBehaviour.Properties.copy(Blocks.TNT)), CreativeModeTab.TAB_REDSTONE);

    // Long list of Engraved blocks



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
