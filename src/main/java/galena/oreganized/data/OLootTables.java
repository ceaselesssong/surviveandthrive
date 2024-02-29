package galena.oreganized.data;

import galena.oreganized.data.provider.OBlockLootProvider;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEntityTypes;
import galena.oreganized.index.OItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OLootTables extends LootTableProvider {

    public OLootTables(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(Entities::new, LootContextParamSets.ENTITY)
        ));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext tracker) {
    }

    public static class Blocks extends OBlockLootProvider {

        protected void generate() {
            //dropNothing(OBlocks.MOLTEN_LEAD);
            cauldron(OBlocks.MOLTEN_LEAD_CAULDRON);

            dropSelf(OBlocks.GLANCE);
            dropSelf(OBlocks.POLISHED_GLANCE);
            dropSelf(OBlocks.GLANCE_BRICKS);
            dropSelf(OBlocks.CHISELED_GLANCE);
            slab(OBlocks.GLANCE_SLAB);
            slab(OBlocks.POLISHED_GLANCE_SLAB);
            slab(OBlocks.GLANCE_BRICK_SLAB);
            dropSelf(OBlocks.GLANCE_STAIRS);
            dropSelf(OBlocks.POLISHED_GLANCE_STAIRS);
            dropSelf(OBlocks.GLANCE_BRICK_STAIRS);
            dropSelf(OBlocks.GLANCE_WALL);
            dropSelf(OBlocks.GLANCE_BRICK_WALL);
            dropSelf(OBlocks.SPOTTED_GLANCE);
            dropSelf(OBlocks.WAXED_SPOTTED_GLANCE);
            ore(OBlocks.SILVER_ORE, OItems.RAW_SILVER);
            ore(OBlocks.DEEPSLATE_SILVER_ORE, OItems.RAW_SILVER);
            ore(OBlocks.LEAD_ORE, OItems.RAW_LEAD);
            ore(OBlocks.DEEPSLATE_LEAD_ORE, OItems.RAW_LEAD);
            dropSelf(OBlocks.RAW_SILVER_BLOCK);
            dropSelf(OBlocks.RAW_LEAD_BLOCK);
            dropSelf(OBlocks.SILVER_BLOCK);
            dropSelf(OBlocks.LEAD_BLOCK);
            dropSelf(OBlocks.LEAD_BRICKS);
            dropSelf(OBlocks.LEAD_PILLAR);
            dropSelf(OBlocks.LEAD_BULB);
            dropSelf(OBlocks.CUT_LEAD);
            dropSelf(OBlocks.ELECTRUM_BLOCK);
            dropSelf(OBlocks.EXPOSER);
            dropSelf(OBlocks.SHRAPNEL_BOMB);
            dropSelf(OBlocks.LEAD_BOLT_CRATE);

            //dropSelf(QCompatRegistry.GLANCE_PILLAR);
            //slab(QCompatRegistry.RAW_LEAD_BRICK_SLAB);
            //slab(QCompatRegistry.RAW_SILVER_BRICK_SLAB);
            //slab(QCompatRegistry.GLANCE_VERTICAL_SLAB);
            //slab(QCompatRegistry.POLISHED_GLANCE_VERTICAL_SLAB);
            //slab(QCompatRegistry.GLANCE_BRICK_VERTICAL_SLAB);

            for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
                dropAsSilk(OBlocks.CRYSTAL_GLASS.get(i));
                dropAsSilk(OBlocks.CRYSTAL_GLASS_PANES.get(i));
            }

            for (Supplier<? extends Block> blocks : OBlocks.WAXED_CONRETE_POWDER) {
                dropSelf(blocks);
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return OBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    public static class Entities extends EntityLootSubProvider {

        public Entities() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {

        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return OEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get);
        }
    }
}
