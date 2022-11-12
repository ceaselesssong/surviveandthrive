package galena.oreganized.content.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class OMinecartItem extends MinecartItem {

    private static Item followItem = Items.HOPPER_MINECART;

    public OMinecartItem(AbstractMinecart.Type minecart) {
        super(minecart, new Properties().tab(CreativeModeTab.TAB_TRANSPORTATION));
    }

    public OMinecartItem(AbstractMinecart.Type minecart, ItemLike follow) {
        super(minecart, new Properties()
                .tab(Objects.requireNonNull(follow.asItem().getItemCategory()))
        );
        followItem = follow.asItem();
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (this.allowedIn(tab))
            insert(new ItemStack(this), false, items, stack -> stack.getItem() == followItem);
    }

    public static void insert(ItemStack stack, boolean before, NonNullList<ItemStack> items, Predicate<ItemStack> filter) {
        if (items.stream().anyMatch(filter)) {
            Optional<ItemStack> optional = items.stream().filter(filter).max((a, b) ->
            {
                int valA = items.indexOf(a);
                int valB = items.indexOf(b);
                if (valA == -1 && valB == -1)
                    return 0;
                if (valA == -1)
                    return valB;
                if (valB == -1)
                    return valA;
                return before ? valB - valA : valA - valB;
            });
            if (optional.isPresent()) {
                items.add(items.indexOf(optional.get()) + (before ? 0 : 1), stack);
                return;
            }
        }
        items.add(stack);
    }
}
