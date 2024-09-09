package galena.oreganized.content.item;

import galena.oreganized.content.block.ICrystalGlass;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static galena.oreganized.index.OTags.Blocks.MINEABLE_WITH_SCRIBE;
import static galena.oreganized.index.OTags.Blocks.SILKTOUCH_WITH_SCRIBE;

public class ScribeItem extends Item {

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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var pos = context.getClickedPos();
        var level = context.getLevel();
        var state = level.getBlockState(pos);

        if (state.hasProperty(ICrystalGlass.TYPE)) {
            var type = state.getValue(ICrystalGlass.TYPE);
            level.setBlockAndUpdate(pos, state.setValue(ICrystalGlass.TYPE, (type + 1) % (ICrystalGlass.MAX_TYPE + 1)));
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(context.getPlayer(), state));
            if (context.getPlayer() != null) {
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), player -> {
                    player.broadcastBreakEvent(context.getHand());
                });
            }
        }

        return super.useOn(context);
    }
}
