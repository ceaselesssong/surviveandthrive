package galena.doom_and_gloom.index;

import galena.doom_and_gloom.DoomAndGloom;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OVillagerTrades {


    @SubscribeEvent
    public static void tadeEvent(VillagerTradesEvent event){
        VillagerProfession type = event.getType();
        if (type == OVillagerTypes.GRAVETENDER.get()){
             addGravetenderTrades(event);
        }
    }

    private static void addGravetenderTrades(VillagerTradesEvent event) {
     //   event.getTrades().put(1, List.of(VillagerTrades.ItemListing))
    }

}
