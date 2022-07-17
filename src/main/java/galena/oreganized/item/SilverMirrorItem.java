package galena.oreganized.item;

import galena.oreganized.api.ISilver;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilverMirrorItem extends Item implements ISilver {

    public static final int TexturedFrames = 8;

    public SilverMirrorItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int i, boolean idk) {
        if (!(entity instanceof Player player)) return;
        BlockPos pos = player.getOnPos();
        int dist = getUndeadDistance(world, pos, player, TexturedFrames);

        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("Level", dist);
    }
}
