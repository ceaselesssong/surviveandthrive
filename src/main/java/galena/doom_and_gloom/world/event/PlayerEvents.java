package galena.doom_and_gloom.world.event;

import galena.doom_and_gloom.DoomAndGloom;
import galena.doom_and_gloom.content.entity.SepulcherBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoomAndGloom.MOD_ID)
public class PlayerEvents {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player) return;
        if (SepulcherBlockEntity.wasConsumerBySepulcher(event.getEntity())) {
            event.setCanceled(true);
        }
    }

}
