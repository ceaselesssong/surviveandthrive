package galena.oreganized;

//import com.redlimerl.detailab.api.DetailArmorBarAPI;
//import com.redlimerl.detailab.api.render.ArmorBarRenderManager;
//import com.redlimerl.detailab.api.render.TextureOffset;
import galena.oreganized.client.OreganizedClient;
import galena.oreganized.content.index.*;
import galena.oreganized.data.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(Oreganized.MOD_ID)
public class Oreganized {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "oreganized";

    public static final List<DyeColor> DYE_COLORS = List.of();

    public Oreganized() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::gatherData);



        DeferredRegister<?>[] registers = {
                //OBlockEntities.BLOCK_ENTITIES,
                OBlocks.BLOCKS,
                OEffects.EFFECTS,
                OEntityTypes.ENTITIES,
                OFluids.FLUIDS,
                OItems.ITEMS,
                OParticleTypes.PARTICLES,
                OPotions.POTIONS,
                OSoundEvents.SOUNDS,
                OStructures.STRUCTURE_TYPES,
                OStructures.STRUCTURES,
                OBiomeModifiers.BIOME_MODIFIERS,
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OConfiguredFeatures.register();
            OPlacedFeatures.register();
            //OCauldronInteractions.register();

            PotionBrewing.addMix(Potions.WATER, OItems.LEAD_INGOT.get(), OPotions.STUNNING.get());
            PotionBrewing.addMix(OPotions.STUNNING.get(), Items.REDSTONE, OPotions.LONG_STUNNING.get());
            PotionBrewing.addMix(OPotions.STUNNING.get(), Items.GLOWSTONE_DUST, OPotions.STRONG_STUNNING.get());
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        OreganizedClient.registerBlockRenderers();

        ItemProperties.register(OItems.SILVER_MIRROR.get(), new ResourceLocation("level"), (stack, world, entity, seed) -> {
            if(entity == null) {
                return 8;
            } else {
                return stack.getOrCreateTag().getInt("Level");
            }
        });

        /*if (ModList.get().isLoaded("detailab")) {
            ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/armor_bar.png");
            DetailArmorBarAPI.customArmorBarBuilder().armor((ArmorItem) OItems.ELECTRUM_CHESTPLATE.get(), (ArmorItem) OItems.ELECTRUM_HELMET.get(), (ArmorItem) OItems.ELECTRUM_LEGGINGS.get(), (ArmorItem) OItems.ELECTRUM_BOOTS.get())
                    .render((ItemStack itemStack) ->
                            new ArmorBarRenderManager(texture, 18, 18,
                                    new TextureOffset(9, 9), new TextureOffset(0, 9), new TextureOffset(9, 0), new TextureOffset(0, 0))
                    ).register();
        }*/
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if(event.includeClient()) {
            generator.addProvider(true, new OBlockStates(generator, helper));
            generator.addProvider(true, new OItemModels(generator, helper));
            generator.addProvider(true, new OLang(generator));
            generator.addProvider(true, new OSoundDefinitions(generator, helper));
        }
        if(event.includeServer()) {
            // TODO
            generator.addProvider(true, new ORecipes(generator));
            generator.addProvider(true, new OLootTables(generator));
            OBlockTags blockTags = new OBlockTags(generator, helper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new OItemTags(generator, blockTags, helper));
            generator.addProvider(true, new OEntityTags(generator, helper));
            //generator.addProvider(new OAdvancements(generator, helper));
            //generator.addProvider(new OFluidTags(generator, helper));
        }
    }
}
