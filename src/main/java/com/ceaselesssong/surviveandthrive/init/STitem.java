package com.ceaselesssong.surviveandthrive.init;

import net.minecraft.world.item.Item;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import com.ceaselesssong.surviveandthrive.SurviveAndThrive;
import java.util.function.Supplier;

public class STitem {
    public static void init(){
    }

    protected static final Supplier<Item> PENIS = RegHelper.registerItem(
            SurviveAndThrive.res("penis"), () -> new Item(new Item.Properties())
    );
    protected static final Supplier<Item> GASTER = RegHelper.registerItem(
            SurviveAndThrive.res("gaster_item"), () -> new Item(new Item.Properties())
    );


}
