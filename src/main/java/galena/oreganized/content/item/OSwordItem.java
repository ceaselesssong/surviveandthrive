package galena.oreganized.content.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

public class OSwordItem extends SwordItem {

    private final ItemLike followItem;

    public OSwordItem(Tier tier, int attack, float modifier, Properties itemProperties, ItemLike followItem) {
        super(tier, attack, modifier, itemProperties);
        this.followItem = followItem;
    }

    public OSwordItem(Tier tier, int attack, float modifier, Properties itemProperties) {
        this(tier, attack, modifier, itemProperties, Items.NETHERITE_SWORD);
    }
}
