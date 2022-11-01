package galena.oreganized.world.event;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;

import java.util.Map;

public interface CauldronInteractions extends CauldronInteraction {

    Map<Item, CauldronInteraction> LEAD = CauldronInteraction.newInteractionMap();


}
