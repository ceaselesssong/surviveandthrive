package galena.doom_and_gloom.index;

import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import galena.doom_and_gloom.DoomAndGloom;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OSoundEvents {
    public static SoundSubRegistryHelper HELPER = DoomAndGloom.REGISTRY_HELPER.getSoundSubHelper();

    public static final RegistryObject<SoundEvent> MUSIC_DISC_AFTERLIFE = HELPER.createSoundEvent("music.disc.afterlife");

    public static final RegistryObject<SoundEvent> BONE_PILE_BREAK = HELPER.createSoundEvent("block.bone_pile.break");
    public static final RegistryObject<SoundEvent> BONE_PILE_STEP = HELPER.createSoundEvent("block.bone_pile.step");
    public static final RegistryObject<SoundEvent> BONE_PILE_FALL = HELPER.createSoundEvent("block.bone_pile.fall");
    public static final RegistryObject<SoundEvent> BONE_PILE_HIT = HELPER.createSoundEvent("block.bone_pile.hit");
    public static final RegistryObject<SoundEvent> BONE_PILE_PLACE = HELPER.createSoundEvent("block.bone_pile.place");

    public static final RegistryObject<SoundEvent> SEPULCHER_BREAK = HELPER.createSoundEvent("block.sepulcher.break");
    public static final RegistryObject<SoundEvent> SEPULCHER_STEP = HELPER.createSoundEvent("block.sepulcher.step");
    public static final RegistryObject<SoundEvent> SEPULCHER_FALL = HELPER.createSoundEvent("block.sepulcher.fall");
    public static final RegistryObject<SoundEvent> SEPULCHER_HIT = HELPER.createSoundEvent("block.sepulcher.hit");
    public static final RegistryObject<SoundEvent> SEPULCHER_PLACE = HELPER.createSoundEvent("block.sepulcher.place");
    public static final RegistryObject<SoundEvent> SEPULCHER_CORPSE_STUFFED = HELPER.createSoundEvent("block.sepulcher.corpse_stuffed");
    public static final RegistryObject<SoundEvent> SEPULCHER_FILLED = HELPER.createSoundEvent("block.sepulcher.filled");
    public static final RegistryObject<SoundEvent> SEPULCHER_ROTTING = HELPER.createSoundEvent("block.sepulcher.rotting");
    public static final RegistryObject<SoundEvent> SEPULCHER_SEALING = HELPER.createSoundEvent("block.sepulcher.sealing");
    public static final RegistryObject<SoundEvent> SEPULCHER_UNSEALING = HELPER.createSoundEvent("block.sepulcher.unsealing");
    public static final RegistryObject<SoundEvent> SEPULCHER_HARVEST = HELPER.createSoundEvent("block.sepulcher.harvest");

    public static final RegistryObject<SoundEvent> GRAVETENDER_WORK = HELPER.createSoundEvent("entity.villager.work_carpenter");

    public static final RegistryObject<SoundEvent> HOLLER_DEATH = HELPER.createSoundEvent("entity.holler_death");
    public static final RegistryObject<SoundEvent> HOLLER_HURTS = HELPER.createSoundEvent("entity.holler_hurts");
    public static final RegistryObject<SoundEvent> HOLLER_HOLLERS = HELPER.createSoundEvent("entity.holler_hollers");
    public static final RegistryObject<SoundEvent> HOLLER_SHRIEKS = HELPER.createSoundEvent("entity.holler_shrieks");

    public static final RegistryObject<SoundEvent> FOG_AMBIENCE = HELPER.createSoundEvent("ambient.fog");
}
