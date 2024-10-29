package galena.doom_and_gloom.data.provider;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;
import java.util.function.Supplier;

public abstract class OBlockLootProvider extends BlockLootSubProvider {

    protected OBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public void dropSelf(Supplier<? extends Block> block) {
        super.dropSelf(block.get());
    }

    public void dropOther(Supplier<? extends Block> brokenBlock, ItemLike droppedBlock) {
        super.dropOther(brokenBlock.get(), droppedBlock);
    }

    public void dropNothing(Supplier<? extends Block> block) {
        dropOther(block, Blocks.AIR);
    }

    public void vigilCandle(Supplier<? extends Block> block) {
        add(block.get(), createCandleDrops(block.get()));
    }

}
