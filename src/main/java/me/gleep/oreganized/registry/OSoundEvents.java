package me.gleep.oreganized.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import me.gleep.oreganized.Oreganized;

public class OSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Oreganized.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_PILLAGED = register("music.disc.pillaged");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_18 = register("music.disc.18");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHULK = register("music.disc.shulk");
    public static final RegistryObject<SoundEvent> MUSIC_DISC_STRUCTURE = register("music.disc.structure");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Oreganized.MOD_ID, name)));
    }
}
