package me.gleep.oreganized.data;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import me.gleep.oreganized.block.*;
import me.gleep.oreganized.data.provider.OBlockLootProvider;
import me.gleep.oreganized.registry.OBlocks;
import me.gleep.oreganized.registry.OEntityTypes;
import me.gleep.oreganized.registry.OItems;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OLootTables extends LootTableProvider {


    public OLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Oreganized Loot Tables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootContextParamSets.BLOCK), Pair.of(Entities::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {}

    public static class Blocks extends OBlockLootProvider {

        @Override
        protected void addTables() {
            dropSelf(OBlocks.GLANCE);
            slab(OBlocks.GLANCE_SLAB);
            slab(OBlocks.GLANCE_BRICKS_SLAB);
            dropSelf(OBlocks.GLANCE_STAIRS);
            dropSelf(OBlocks.GLANCE_BRICKS_STAIRS);
            dropSelf(OBlocks.GLANCE_WALL);
            dropSelf(OBlocks.GLANCE_BRICKS_WALL);
            dropSelf(OBlocks.POLISHED_GLANCE);
            dropSelf(OBlocks.GLANCE_BRICKS);
            dropSelf(OBlocks.CHISELED_GLANCE);
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
            dropSelf(OBlocks.ELECTRUM_BLOCK);
            for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
                dropAsSilk(OBlocks.CRYSTAL_GLASS.get(i));
                dropAsSilk(OBlocks.CRYSTAL_GLASS_PANES.get(i));
            }
            dropSelf(OBlocks.WAXED_WHITE_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_ORANGE_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_MAGENTA_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_LIGHT_BLUE_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_YELLOW_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_LIME_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_PINK_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_GRAY_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_LIGHT_GRAY_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_CYAN_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_PURPLE_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_BLUE_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_BROWN_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_GREEN_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_RED_CONCRETE_POWDER);
            dropSelf(OBlocks.WAXED_BLACK_CONCRETE_POWDER);
            dropSelf(OBlocks.EXPOSER);
            dropSelf(OBlocks.SHRAPNEL_BOMB);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return OBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    public static class Entities extends EntityLoot {

        @Override
        protected void addTables() {

        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return OEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

}
