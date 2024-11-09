package com.ceaselesssong.surviveandthrive.init;

import net.minecraft.world.item.Item;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import com.ceaselesssong.surviveandthrive.SurviveAndThrive;
import net.minecraft.world.item.CreativeModeTab;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import java.util.Arrays;
import net.minecraft.world.level.ItemLike;

public class STtab {
    protected static final Supplier<Item> ICON = RegHelper.registerItem(
            SurviveAndThrive.res("tab_icon_item"), () -> new Item(new Item.Properties())
    );
    public static final RegSupplier<CreativeModeTab> STTAB = RegHelper.registerCreativeModeTab(SurviveAndThrive.res("surviveandthrive"),
                    (t) -> t.title(Component.translatable("itemGroup.surviveandthrive"))
                            .icon(() -> ICON.get().getDefaultInstance())
    );
    private static void addItems(RegHelper.ItemToTabEvent event, RegSupplier<CreativeModeTab> tab,Supplier<?>... items) {
        event.add(tab.getHolder().unwrapKey().get(), Arrays.stream(items).map((s -> (ItemLike) (s.get()))).toArray(ItemLike[]::new));
    }

    public static void init(){
        RegHelper.addItemsToTabsRegistration(STtab::registerItemsToTabs);
    }

    private static void registerItemsToTabs(RegHelper.ItemToTabEvent e) {
        addItems(e, STTAB,
                STitem.PENIS, STitem.GASTER, STblock.ABYSSMAL
        );
    }


}
