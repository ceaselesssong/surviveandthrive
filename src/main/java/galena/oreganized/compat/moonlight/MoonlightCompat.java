package galena.oreganized.compat.moonlight;

import com.mojang.datafixers.util.Pair;
import galena.oreganized.Oreganized;
import galena.oreganized.index.OVillagerTrades;
import galena.oreganized.index.OVillagerTypes;
import net.mehvahdjukaar.moonlight.api.events.IVillagerBrainEvent;
import net.mehvahdjukaar.moonlight.api.events.MoonlightEventsHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;

import java.util.Optional;
import java.util.function.Supplier;

public class MoonlightCompat {

    public static final Supplier<MemoryModuleType<GlobalPos>> PUMPKIN_POS =
            RegHelper.registerMemoryModule(Oreganized.modLoc("pumpkin_pos"), () ->
                    new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));

    public static final Supplier<MemoryModuleType<GlobalPos>> NEAREST_PUMPKIN =
            RegHelper.registerMemoryModule(Oreganized.modLoc("nearest_pumpkin"), () ->
                    new MemoryModuleType<>(Optional.empty()));


    public static final Supplier<SensorType<CandlePoiSensor>> PUMPKIN_POI_SENSOR =
            RegHelper.registerSensor(Oreganized.modLoc("candle_poi"), () ->
                    new SensorType<>(CandlePoiSensor::new));

    public static void init() {
        MoonlightEventsHelper.addListener(MoonlightCompat::onVillagerBrainInitialize, IVillagerBrainEvent.class);
    }

    public static void onVillagerBrainInitialize(IVillagerBrainEvent event) {
        Villager villager = event.getVillager();

        if (!villager.isBaby() && villager.getVillagerData().getProfession() == OVillagerTypes.GRAVETENDER.get()) {
         //   event.addTaskToActivity(Activity.WORK, Pair.of(3, new LightUpCandles(0.5f)));
            event.addTaskToActivity(Activity.IDLE, Pair.of(3, new TurnOffCandles(0.5f)));
        }
    }
}
