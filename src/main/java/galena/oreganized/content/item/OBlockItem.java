package galena.oreganized.content.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class OBlockItem extends BlockItem {

    private static Item followItem;

    public OBlockItem(Block block, CreativeModeTab tab) {
        super(block, new Properties()
                .tab(tab));
    }

    public OBlockItem(Block block, int stackSize, CreativeModeTab tab) {
        super(block, new Properties()
                .tab(tab)
                .stacksTo(stackSize)
        );
    }

    public OBlockItem(Block block, boolean inflammable, CreativeModeTab tab) {
        super(block, new Properties()
                .tab(tab)
                .fireResistant()
        );
    }

    public OBlockItem(Block block, ItemLike follow) {
        super(block, new Properties()
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
