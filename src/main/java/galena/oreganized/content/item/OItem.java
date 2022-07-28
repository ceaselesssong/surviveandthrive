package galena.oreganized.content.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class OItem extends Item {

    public OItem() {
        super(new Properties());
    }

    public OItem(CreativeModeTab tab) {
        super(new Properties()
                .tab(tab)
        );
    }

    public OItem(int stackSize, CreativeModeTab tab) {
        super(new Properties()
                .tab(tab)
                .stacksTo(stackSize)
        );
    }

    public OItem(boolean inflammable, CreativeModeTab tab) {
        super(new Properties()
                .tab(tab)
                .fireResistant()
        );
    }
}
