package galena.doom_and_gloom.compat.moonlight;

import com.mojang.datafixers.util.Pair;
import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.index.OVillagerTypes;
import net.mehvahdjukaar.moonlight.api.entity.VillagerAIHooks;
import net.mehvahdjukaar.moonlight.api.events.IVillagerBrainEvent;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.core.MoonlightClient;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class MoonlightCompat {

    public static final Supplier<MemoryModuleType<List<GlobalPos>>> CANDLES_I_LIT =
            RegHelper.registerMemoryModule(DoomAndGloom.modLoc("candles_i_lit"), () ->
                    new MemoryModuleType<>(Optional.of(GlobalPos.CODEC.listOf())));

    //list as these could be lit
    public static final Supplier<MemoryModuleType<GlobalPos>> NEAREST_UNLIT_CANDLE =
            RegHelper.registerMemoryModule(DoomAndGloom.modLoc("nearest_candle"), () ->
                    new MemoryModuleType<>(Optional.empty()));


    public static final Supplier<SensorType<CandleSensor>> CANDLES_SENSOR =
            RegHelper.registerSensor(DoomAndGloom.modLoc("candle_poi"), () ->
                    new SensorType<>(CandleSensor::new));

    public static void init() {
        VillagerAIHooks.addBrainModification(MoonlightCompat::onVillagerBrainInitialize);
        PlatHelper.addCommonSetup(() -> {
            VillagerAIHooks.registerMemory(CANDLES_I_LIT.get());
            VillagerAIHooks.registerMemory(NEAREST_UNLIT_CANDLE.get());
        });
    }

    public static void onVillagerBrainInitialize(IVillagerBrainEvent event) {
        Villager villager = event.getVillager();

        if (!villager.isBaby() && villager.getVillagerData().getProfession() == OVillagerTypes.GRAVETENDER.get()) {
            event.addTaskToActivity(Activity.WORK, Pair.of(3, new LightUpCandles(0.5f)));
            event.addTaskToActivity(Activity.IDLE, Pair.of(3, new TurnOffCandles(0.5f)));
            event.addSensor(CANDLES_SENSOR.get());
        }
    }

}
