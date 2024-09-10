package galena.oreganized.content.item;

import galena.oreganized.content.block.ICrystalGlass;
import galena.oreganized.index.OBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static galena.oreganized.index.OTags.Blocks.MINEABLE_WITH_SCRIBE;
import static galena.oreganized.index.OTags.Blocks.SILKTOUCH_WITH_SCRIBE;

public class ScribeItem extends Item {

    private static final Map<Block, Supplier<Block>> GROOVED_BLOCKS = new HashMap<>();

    public static void registerGroovedBlock(Block from, Supplier<Block> to) {
        GROOVED_BLOCKS.put(from, to);
    }

    static {
        registerGroovedBlock(Blocks.ICE, OBlocks.GROOVED_ICE);
        registerGroovedBlock(Blocks.PACKED_ICE, OBlocks.GROOVED_PACKED_ICE);
        registerGroovedBlock(Blocks.BLUE_ICE, OBlocks.GROOVED_BLUE_ICE);
    }

    public ScribeItem(Properties properties) {
        super(properties);
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity user) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0F) {
            stack.hurtAndBreak(1, user, it -> {
                it.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(MINEABLE_WITH_SCRIBE)) return 32F;
        else if (isCorrectToolForDrops(stack, state)) return 0.3F;
        return super.getDestroySpeed(stack, state);
    }

    public boolean dropsLikeSilktouch(ItemStack stack, BlockState state) {
        return isCorrectToolForDrops(state);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.is(SILKTOUCH_WITH_SCRIBE);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairStack) {
        return repairStack.is(Items.AMETHYST_SHARD);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category == EnchantmentCategory.DIGGER) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    private InteractionResult replaceBlock(UseOnContext context, BlockState to) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var from = level.getBlockState(pos);

        level.setBlockAndUpdate(pos, to);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(context.getPlayer(), from));
        level.addDestroyBlockEffect(pos, from);

        var vec = Vec3.atCenterOf(pos);
        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, from), vec.x, vec.y + 1, vec.z, 0.0, 0.0, 0.0);

        if (context.getPlayer() != null) {
            context.getPlayer().playSound(SoundEvents.GRINDSTONE_USE, 1F, 1.5F);

            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), player -> {
                player.broadcastBreakEvent(context.getHand());
            });
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var state = context.getLevel().getBlockState(context.getClickedPos());

        if (state.hasProperty(ICrystalGlass.TYPE)) {
            var type = state.getValue(ICrystalGlass.TYPE);
            return replaceBlock(context, state.setValue(ICrystalGlass.TYPE, (type + 1) % (ICrystalGlass.MAX_TYPE + 1)));
        }

        var grooved = GROOVED_BLOCKS.get(state.getBlock());
        if (grooved != null) {
            return replaceBlock(context, grooved.get().defaultBlockState());
        }

        return super.useOn(context);
    }
}
