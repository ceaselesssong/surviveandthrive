package com.ceaselesssong.surviveandthrive.init;

import com.ceaselesssong.surviveandthrive.SurviveAndThrive;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class STblock {
    public static void init(){
    }
    public static <T extends Block> Supplier<T> regBlockItem(String name, Supplier<T> blockFactory) {
        Supplier<T> block = RegHelper.registerBlock(SurviveAndThrive.res(name), blockFactory);
        RegHelper.registerItem(SurviveAndThrive.res(name), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    protected static final Supplier<Block> ABYSSMAL = regBlockItem("abyssmal_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE))
    );


}
