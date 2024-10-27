package galena.oreganized.index;

import galena.oreganized.Oreganized;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Oreganized.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OVillagerTrades {


    @SubscribeEvent
    public static void tadeEvent(VillagerTradesEvent event){
        VillagerProfession type = event.getType();
        if (type == OVillagerTypes.GRAVETENDER.get()){
             addGravetenderTrades(event);
        }
        if (type == VillagerProfession.MASON) {
            addMasonTrades(event);
        }
    }

    private static void addGravetenderTrades(VillagerTradesEvent event) {
    }

    private static void addMasonTrades(VillagerTradesEvent event) {
        event.getTrades().get(5).add(new BasicItemListing(14, new ItemStack(OBlocks.GARGOYLE.get()), 5, 30, 0.05F));
    }
}
