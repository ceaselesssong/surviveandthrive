package me.gleep.oreganized.registry;

import me.gleep.oreganized.Oreganized;
import me.gleep.oreganized.effect.StunningEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Oreganized.MOD_ID);

    public static final RegistryObject<MobEffect> STUNNING = EFFECTS.register("stunning", StunningEffect::new);
}
