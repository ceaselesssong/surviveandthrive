package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.stream.Stream;

public class OSoundDefinitions extends SoundDefinitionsProvider {

    public OSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Oreganized.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(OSoundEvents.MUSIC_DISC_STRUCTURE, definition().with(
                sound(Oreganized.MOD_ID + ":music/disc/structure").stream()
        ));

        add(OSoundEvents.SHRAPNEL_BOMB_PRIMED, definition().with(
                sound("minecraft:random/fuse")
        ).subtitle("subtitles.entity.shrapnel_bomb.primed"));

        add(OSoundEvents.BOLT_HIT, definition().with(
                sound(Oreganized.MOD_ID + ":entity/bolt_hit")
        ).subtitle("subtitles.entity.bolt_hit"));

        add(OSoundEvents.BOLT_HIT_ARMOR, definition().with(
                sound(Oreganized.MOD_ID + ":entity/bolt_hit_armor")
        ).subtitle("subtitles.entity.bolt_hit_armor"));

        add(OSoundEvents.GARGOYLE_GROWL, definition().with(
                sound(Oreganized.MOD_ID + ":block/gargoyle_growl_1"),
                sound(Oreganized.MOD_ID + ":block/gargoyle_growl_2"),
                sound(Oreganized.MOD_ID + ":block/gargoyle_growl_3")
        ).subtitle("subtitles.block.gargoyle.growl"));

        Stream.of(OSoundEvents.BONE_PILE_BREAK, OSoundEvents.BONE_PILE_PLACE).forEach(it -> {
            add(it, definition().with(
                    sound(Oreganized.MOD_ID + ":block/bone_pile_break_0"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_break_1"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_break_2"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_break_3"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_break_4")
            ).subtitle("subtitles.block.generic.place"));
        });

        Stream.of(OSoundEvents.BONE_PILE_HIT, OSoundEvents.BONE_PILE_STEP, OSoundEvents.BONE_PILE_FALL).forEach(it -> {
            add(it, definition().with(
                    sound(Oreganized.MOD_ID + ":block/bone_pile_step_0"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_step_1"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_step_2"),
                    sound(Oreganized.MOD_ID + ":block/bone_pile_step_3")
            ).subtitle("subtitles.block.generic.place"));
        });

        Stream.of(OSoundEvents.SEPULCHER_BREAK, OSoundEvents.SEPULCHER_PLACE).forEach(it -> {
            add(it, definition().with(
                    sound(Oreganized.MOD_ID + ":block/sepulcher_break_0"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_break_1"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_break_2"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_break_3")
            ).subtitle("subtitles.block.generic.place"));
        });

        Stream.of(OSoundEvents.SEPULCHER_HIT, OSoundEvents.SEPULCHER_STEP, OSoundEvents.SEPULCHER_FALL).forEach(it -> {
            add(it, definition().with(
                    sound(Oreganized.MOD_ID + ":block/sepulcher_step_0"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_step_1"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_step_2"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_step_3"),
                    sound(Oreganized.MOD_ID + ":block/sepulcher_step_4")
            ).subtitle("subtitles.block.generic.place"));
        });

        add(OSoundEvents.SEPULCHER_CORPSE_STUFFED, definition().with(
                sound(Oreganized.MOD_ID + ":block/sepulcher_corpse_stuffed_0"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_corpse_stuffed_1")
        ).subtitle("subtitles.block.generic.place"));

        add(OSoundEvents.SEPULCHER_FILLED, definition().with(
                sound(Oreganized.MOD_ID + ":block/sepulcher_filled_0"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_filled_1"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_filled_2"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_filled_3")
        ).subtitle("subtitles.block.generic.place"));

        add(OSoundEvents.SEPULCHER_ROTTING, definition().with(
                sound(Oreganized.MOD_ID + ":block/sepulcher_rotting_0"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_rotting_1"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_rotting_2"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_rotting_3")
        ).subtitle("subtitles.block.generic.place"));

        add(OSoundEvents.SEPULCHER_HARVEST, definition().with(
                sound(Oreganized.MOD_ID + ":block/sepulcher_harvest_0"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_harvest_1"),
                sound(Oreganized.MOD_ID + ":block/sepulcher_harvest_2")
        ).subtitle("subtitles.block.generic.place"));

        add(OSoundEvents.SEPULCHER_SEALING, definition()
                .with(sound(Oreganized.MOD_ID + ":block/sepulcher_sealing"))
                .subtitle("subtitles.block.generic.place"));

        add(OSoundEvents.SEPULCHER_UNSEALING, definition()
                .with(sound(Oreganized.MOD_ID + ":block/sepulcher_unsealing"))
                .subtitle("subtitles.block.generic.place"));
    }

}