package galena.oreganized.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static galena.oreganized.index.OTags.Blocks.MINEABLE_WITH_SCRIBE;

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
        return isCorrectToolForDrops(stack, state) ? 32F : 3F;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.is(MINEABLE_WITH_SCRIBE);
    }

}
