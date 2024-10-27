package galena.oreganized.client;

import com.mojang.math.Axis;
import galena.oreganized.Oreganized;
import galena.oreganized.client.model.HollerModel;
import galena.oreganized.client.render.entity.HollerRender;
import galena.oreganized.client.render.entity.LeadBoltRender;
import galena.oreganized.client.render.entity.ShrapnelBombMinecartRender;
import galena.oreganized.client.render.entity.ShrapnelBombRender;
import galena.oreganized.client.render.gui.StunningOverlay;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OEntityTypes;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OParticleTypes;
import galena.oreganized.world.IDoorProgressHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreganizedClient {

    private static void render(Supplier<? extends Block> block, RenderType render) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), render);
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        OreganizedClient.registerBlockRenderers();
        OreganizedClient.registerItemProperties();
    }

    private static void registerItemProperties() {
        ItemProperties.register(OItems.SILVER_MIRROR.get(), new ResourceLocation("level"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 8;
            } else {
                return stack.getOrCreateTag().getInt("Level");
            }
        });

        ItemProperties.register(Items.CROSSBOW, new ResourceLocation(Oreganized.MOD_ID, "lead_bolt"), (stack, level, user, i) ->
                CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, OItems.LEAD_BOLT.get()) ? 1.0F : 0.0F
        );

        ItemProperties.register(OItems.ELECTRUM_SHIELD.get(), new ResourceLocation("blocking"), (stack, level, user, i) ->
                user != null && user.isUsingItem() && user.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    private static void registerBlockRenderers() {
        RenderType cutout = RenderType.cutout();
        RenderType mipped = RenderType.cutoutMipped();
        RenderType translucent = RenderType.translucent();

        render(OBlocks.LEAD_DOOR, cutout);
        render(OBlocks.LEAD_TRAPDOOR, cutout);
        render(OBlocks.LEAD_BARS, cutout);
        render(OBlocks.GARGOYLE, cutout);
        render(OBlocks.SEPULCHER, cutout);

        OBlocks.vigilCandles().forEach(block -> render(block, cutout));

        for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
            render(OBlocks.CRYSTAL_GLASS.get(i), translucent);
            render(OBlocks.CRYSTAL_GLASS_PANES.get(i), translucent);
        }

        render(OBlocks.GROOVED_ICE, translucent);
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new OReloadListener());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OEntityTypes.SHRAPNEL_BOMB.get(), ShrapnelBombRender::new);
        event.registerEntityRenderer(OEntityTypes.SHRAPNEL_BOMB_MINECART.get(), ShrapnelBombMinecartRender::new);
        event.registerEntityRenderer(OEntityTypes.LEAD_BOLT.get(), LeadBoltRender::new);
        event.registerEntityRenderer(OEntityTypes.HOLLER.get(), HollerRender::new);
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OModelLayers.HOLLER, HollerModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        MinecraftForge.EVENT_BUS.register(new StunningOverlay());
        event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "stunning", new StunningOverlay());
    }

    public static void renderThirdPersonArm(ModelPart arm, boolean rightArm) {
        arm.xRot = -1.7F;
        arm.yRot = rightArm ? -0.1F : 0.2F;
    }

    @Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {

        private static MobEffectInstance fogEffect;

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            if (!(Minecraft.getInstance().gameRenderer.getMainCamera().getEntity() instanceof Player player)) return;
            fogEffect = player.getEffect(OEffects.FOG.get());

            if (fogEffect == null) return;
            if (Minecraft.getInstance().isPaused()) return;
            var level = Minecraft.getInstance().level;
            if (level == null) return;

            var range = 24;
            var at = player.position().add((level.random.nextDouble() - 0.5) * range, level.random.nextDouble() * 4 - 2, (level.random.nextDouble() - 0.5) * range);
            var blockAt = BlockPos.containing(at.x, at.y, at.z);

            if (!level.getBlockState(blockAt).canBeReplaced()) return;
            if (level.getBlockState(blockAt.below(1)).canBeReplaced()) return;

            if (level.random.nextInt(4) != 0) return;

            level.addParticle(OParticleTypes.FOG.get(), at.x, at.y + 0.5 + level.random.nextDouble(), at.z, level.random.nextFloat() + 0.5F, 0.0, 0.0);
        }

        @SubscribeEvent
        public static void fogEffectFog(ViewportEvent.RenderFog event) {
            if (fogEffect != null && fogEffect.getFactorData().isPresent()) {
                LivingEntity entity = (LivingEntity) Minecraft.getInstance().gameRenderer.getMainCamera().getEntity();
                float f = Mth.lerp(fogEffect.getFactorData().get().getFactor(entity, (float) event.getPartialTick()), event.getFarPlaneDistance(), 15F);
                event.setNearPlaneDistance(event.getMode() == FogRenderer.FogMode.FOG_SKY ? -2F : f * -0.5F);
                event.setFarPlaneDistance(f);
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void fogEffectColor(ViewportEvent.ComputeFogColor event) {
            if (fogEffect != null && fogEffect.getFactorData().isPresent()) {
                var color = new Color(0x697180);
                LivingEntity entity = (LivingEntity) Minecraft.getInstance().gameRenderer.getMainCamera().getEntity();
                float factor = (fogEffect.getFactorData().get()).getFactor(entity, (float) event.getPartialTick());
                event.setRed(color.getRed() / 255F * factor);
                event.setGreen(color.getGreen() / 255F * factor);
                event.setBlue(color.getBlue() / 255F * factor);
            }
        }

        @SubscribeEvent
        public static void addTooltips(ItemTooltipEvent event) {
            if (event.getItemStack().is(OItems.BUSH_HAMMER.get())) {
                List<Component> tooltip = event.getToolTip();
                MutableComponent wipTitle = Component.translatable("tooltip.oreganized.wip.title");
                MutableComponent wipDesc = Component.translatable("tooltip.oreganized.wip.description");

                tooltip.add(wipTitle.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
                tooltip.add(wipDesc.withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
            }
        }

        @SubscribeEvent
        public static void renderHand(RenderHandEvent event) {
            var player = Minecraft.getInstance().player;
            if (!(player instanceof IDoorProgressHolder progressHolder)) return;
            var progress = progressHolder.oreganised$getOpeningProgress();
            if (progress == 0) return;
            if (event.getHand() == InteractionHand.OFF_HAND) return;

            var poseStack = event.getPoseStack();

            poseStack.pushPose();

            var rightArm = player.getMainArm() == HumanoidArm.RIGHT;
            float factor = rightArm ? 1.0F : -1.0F;
            poseStack.translate(factor * 0.84000005F, -0.4F, -0.4F);
            poseStack.mulPose(Axis.YP.rotationDegrees(factor * -20F - event.getSwingProgress()));
            poseStack.mulPose(Axis.ZP.rotationDegrees(factor * 45F));
            poseStack.mulPose(Axis.XP.rotationDegrees(-45F));

            var renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);

            if (rightArm) {
                renderer.renderRightHand(poseStack, event.getMultiBufferSource(), event.getPackedLight(), player);
            } else {
                renderer.renderLeftHand(poseStack, event.getMultiBufferSource(), event.getPackedLight(), player);
            }

            poseStack.popPose();

            event.setCanceled(true);
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, i) -> switch (i) {
                case 0 -> 0x84EED2;
                case 1 -> 0x24352F;
                default -> -1;
            }, OItems.HOLLER_SPAWN_EGG.get());
        }

    }
}
