package galena.oreganized.client;

import galena.oreganized.Oreganized;
import galena.oreganized.client.render.entity.ShrapnelBombRender;
import galena.oreganized.client.render.gui.StunningOverlay;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEntityTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreganizedClient {

    private static void render(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }

    public static void registerBlockRenderers() {
        RenderType cutout = RenderType.cutout();
        RenderType mipped = RenderType.cutoutMipped();
        RenderType translucent = RenderType.translucent();

        for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
            render(OBlocks.CRYSTAL_GLASS.get(i), translucent);
            render(OBlocks.CRYSTAL_GLASS_PANES.get(i), translucent);
        }
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OEntityTypes.SHRAPNEL_BOMB.get(), ShrapnelBombRender::new);
        event.registerEntityRenderer(OEntityTypes.SHRAPNEL_BOMB_MINECART.get(), TntMinecartRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        MinecraftForge.EVENT_BUS.register(new StunningOverlay());
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "stunning", new StunningOverlay());
    }

    @Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {

        /*@SubscribeEvent
        public static void renderMoltenLeadFogColor(ViewportEvent.ComputeFogColor event) {
            Camera camera = event.getCamera();
            FluidState fluidState = camera.getBlockAtCamera().getFluidState();

            if(fluidState.getType().isSame(OFluids.MOLTEN_LEAD.get())) {
                event.setRed(57F / 255F);
                event.setGreen(57F / 255F);
                event.setBlue(95F / 255F);
            }
        }
        @SubscribeEvent
        public static void renderMoltenLeadFogDensity(ViewportEvent.RenderFog event) {
            Camera camera = event.getCamera();
            FluidState fluidState = camera.getBlockAtCamera().getFluidState();

            if(fluidState.getType().isSame(OFluids.MOLTEN_LEAD.get())) {
                event.setFarPlaneDistance(15.0F);
                event.setCanceled(true);
            }
        }*/
    }
}
