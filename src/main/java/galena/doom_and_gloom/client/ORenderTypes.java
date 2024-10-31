package galena.doom_and_gloom.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import galena.doom_and_gloom.DoomAndGloom;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiFunction;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class ORenderTypes extends RenderType {

    private static ShaderInstance ADDITIVE_SHADER;

    public static final Function<ResourceLocation, RenderType> ADDITIVE_TRANSLUCENCY = Util.memoize((t) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(t, false, false))
                .setTransparencyState(TransparencyStateShard.LIGHTNING_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP).setOverlayState(OVERLAY)
                .createCompositeState(false);
        return create("doom_and_gloom_entity_additive_translucency", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
    });

    public ORenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance shader = new ShaderInstance(event.getResourceProvider(),
                    DoomAndGloom.modLoc("rendertype_entity_translucent_additive"), DefaultVertexFormat.NEW_ENTITY);

            event.registerShader(shader, s -> ADDITIVE_SHADER = s);
        } catch (Exception e) {
            DoomAndGloom.LOGGER.error("Failed to register shader", e);
        }

    }
}
