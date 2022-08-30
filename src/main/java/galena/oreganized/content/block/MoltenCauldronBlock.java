package galena.oreganized.content.block;

import galena.oreganized.content.MoltenMetal;
import galena.oreganized.content.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.ToIntFunction;

/*
    An abstract class that can be used as a base for Molten Fluids
 */

public class MoltenCauldronBlock extends AbstractCauldronBlock {

    private static MoltenMetal moltenMetal;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    protected static Map<Item, CauldronInteraction> cauldronInteractions = CauldronInteraction.newInteractionMap();

    static CauldronInteraction FILL_BLOCK = (state, world, pos, player, hand, stack) ->
            placeBlock(world, pos, player, hand, stack, MoltenCauldronBlock.moltenMetal.getSolid().defaultBlockState(), SoundEvents.METAL_PLACE);

    static CauldronInteraction FILL_MOLTEN = (state, world, pos, player, hand, stack) ->
        CauldronInteraction.emptyBucket(world, pos, player, hand, stack, MoltenCauldronBlock.moltenMetal.getSolid().defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA);

    CauldronInteraction EMPTY_MOLTEN = (state, world, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, world, pos, player, hand, stack, new ItemStack(moltenMetal.getMolten().getBucket()), blockState -> state.getValue(AGE).equals(3), SoundEvents.BUCKET_FILL_LAVA);

    public MoltenCauldronBlock(MoltenMetal moltenMetal) {
        super(Properties.copy(Blocks.LAVA_CAULDRON), buildInteractions(moltenMetal));
        MoltenCauldronBlock.moltenMetal = moltenMetal;
    }

    protected static Map<Item, CauldronInteraction> buildInteractions(MoltenMetal moltenMetal) {
        Item bucket = moltenMetal.getMolten().getBucket();
        Item blockItem = moltenMetal.getSolid().asItem();

        CauldronInteraction.EMPTY.put(bucket, FILL_MOLTEN);
        CauldronInteraction.WATER.put(bucket, FILL_MOLTEN);
        CauldronInteraction.LAVA.put(bucket, FILL_MOLTEN);
        CauldronInteraction.POWDER_SNOW.put(bucket, FILL_MOLTEN);

        CauldronInteraction.EMPTY.put(blockItem, FILL_BLOCK);
        CauldronInteraction.WATER.put(blockItem, FILL_BLOCK);
        CauldronInteraction.LAVA.put(blockItem, FILL_BLOCK);
        CauldronInteraction.POWDER_SNOW.put(blockItem, FILL_BLOCK);

        cauldronInteractions.put(bucket, FILL_MOLTEN);
        return cauldronInteractions;
    }

    static InteractionResult placeBlock(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent sound) {
        if (!world.isClientSide) {
            Item item = stack.getItem();
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            world.setBlockAndUpdate(pos, state);
            world.playSound((Player)null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent((Entity)null, GameEvent.BLOCK_PLACE, pos);
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    protected double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    public static ToIntFunction<BlockState> moltenStageEmission() {
        return (state) -> {
            return state.getValue(AGE) * 2;
        };
    }

    public boolean isFull(BlockState state) {
        return true;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        if (!state.getValue(AGE).equals(3)) return Shapes.block();
        return super.getShape(state, world, pos, ctx);
    }

    public void entityInside(BlockState state, Level level, BlockPos blockPos, Entity entity) {
        if (this.isEntityInsideContent(state, blockPos, entity)) {
            entity.setSecondsOnFire(10);
        }
    }

    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        return 3;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (age < 3 && world.isAreaLoaded(pos, 4)) {
            BlockState below = world.getBlockState(pos.below());
            if (below.is(OTags.Blocks.FIRE_SOURCE) || below.getFluidState().is(FluidTags.LAVA)) {
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
            } else {
                world.setBlock(pos, state.setValue(AGE, 1), 2);
            }
        }
    }
}
