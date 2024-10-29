package galena.doom_and_gloom.compat.moonlight;

import com.mojang.datafixers.util.Pair;
import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.index.OVillagerTypes;
import net.mehvahdjukaar.moonlight.api.entity.VillagerAIHooks;
import net.mehvahdjukaar.moonlight.api.events.IVillagerBrainEvent;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;
import java.util.function.Supplier;

public class MoonlightCompat {

    public static final Supplier<MemoryModuleType<GlobalPos>> PUMPKIN_POS =
            RegHelper.registerMemoryModule(DoomAndGloom.modLoc("pumpkin_pos"), () ->
                    new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));

    public static final Supplier<MemoryModuleType<GlobalPos>> NEAREST_PUMPKIN =
            RegHelper.registerMemoryModule(DoomAndGloom.modLoc("nearest_pumpkin"), () ->
                    new MemoryModuleType<>(Optional.empty()));


    public static final Supplier<SensorType<CandlePoiSensor>> PUMPKIN_POI_SENSOR =
            RegHelper.registerSensor(DoomAndGloom.modLoc("candle_poi"), () ->
                    new SensorType<>(CandlePoiSensor::new));

    public static void init() {
        VillagerAIHooks.addBrainModification(MoonlightCompat::onVillagerBrainInitialize);
        VillagerAIHooks.registerMemory(PUMPKIN_POS.get());
        VillagerAIHooks.registerMemory(NEAREST_PUMPKIN.get());
    }

    public static void onVillagerBrainInitialize(IVillagerBrainEvent event) {
        Villager villager = event.getVillager();

        if (!villager.isBaby() && villager.getVillagerData().getProfession() == OVillagerTypes.GRAVETENDER.get()) {
         //   event.addTaskToActivity(Activity.WORK, Pair.of(3, new LightUpCandles(0.5f)));
            event.addTaskToActivity(Activity.IDLE, Pair.of(3, new TurnOffCandles(0.5f)));
        }
    }
}
