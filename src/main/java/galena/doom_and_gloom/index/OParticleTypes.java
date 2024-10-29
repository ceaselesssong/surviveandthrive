package galena.doom_and_gloom.index;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.client.particle.BoneFragmentParticle;
import galena.doom_and_gloom.client.particle.FogParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DoomAndGloom.MOD_ID);

    public static final RegistryObject<SimpleParticleType> BONE_FRAGMENT = PARTICLES.register( "bone_fragment", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOG = PARTICLES.register( "fog", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOG_WATER = PARTICLES.register( "fog_water", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HOLLERING_SOUL = PARTICLES.register( "hollering_soul", () -> new SimpleParticleType(true));


    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        engine.register(BONE_FRAGMENT.get(), BoneFragmentParticle.Provider::new);
        engine.register(FOG.get(), FogParticle.provider(200));
        engine.register(FOG_WATER.get(), FogParticle.provider(100));
        engine.register(HOLLERING_SOUL.get(), SoulParticle.Provider::new);
    }
}
