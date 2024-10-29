package galena.oreganized.index;

import galena.oreganized.Oreganized;
import galena.oreganized.client.particle.BoneFragmentParticle;
import galena.oreganized.client.particle.CustomDrippingParticle;
import galena.oreganized.client.particle.FogParticle;
import galena.oreganized.client.particle.KineticHitParticle;
import galena.oreganized.client.particle.LeadCloudParticleProvider;
import galena.oreganized.client.particle.LeadShrapnelParticle;
import galena.oreganized.client.particle.VengeanceParticleProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ExplodeParticle;
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

@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Oreganized.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_LEAD = PARTICLES.register("dripping_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FALLING_LEAD = PARTICLES.register("falling_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LANDING_LEAD = PARTICLES.register("landing_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LEAD_SHRAPNEL = PARTICLES.register( "lead_shrapnel", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VENGEANCE = PARTICLES.register( "vengeance", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> KINETIC_HIT = PARTICLES.register( "kinetic_hit", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LEAD_CLOUD = PARTICLES.register( "lead_cloud", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LEAD_BLOW = PARTICLES.register( "lead_blow", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BONE_FRAGMENT = PARTICLES.register( "bone_fragment", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOG = PARTICLES.register( "fog", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOG_WATER = PARTICLES.register( "fog_water", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HOLLERING_SOUL = PARTICLES.register( "hollering_soul", () -> new SimpleParticleType(true));


    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        engine.register(DRIPPING_LEAD.get(), CustomDrippingParticle.LeadHangProvider::new);
        engine.register(FALLING_LEAD.get(), CustomDrippingParticle.LeadFallProvider::new);
        engine.register(LANDING_LEAD.get(), CustomDrippingParticle.LeadLandProvider::new);
        engine.register(LEAD_SHRAPNEL.get(), LeadShrapnelParticle.Provider::new);
        engine.register(VENGEANCE.get(), VengeanceParticleProvider::new);
        engine.register(KINETIC_HIT.get(), KineticHitParticle.Provider::new);
        engine.register(LEAD_CLOUD.get(), LeadCloudParticleProvider::new);
        engine.register(LEAD_BLOW.get(), ExplodeParticle.Provider::new);
        engine.register(LEAD_BLOW.get(), ExplodeParticle.Provider::new);
        engine.register(BONE_FRAGMENT.get(), BoneFragmentParticle.Provider::new);
        engine.register(FOG.get(), FogParticle.provider(200));
        engine.register(FOG_WATER.get(), FogParticle.provider(100));
        engine.register(HOLLERING_SOUL.get(), SoulParticle.Provider::new);
    }
}
