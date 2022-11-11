package galena.oreganized.content.item;

import com.google.common.collect.ImmutableMap;
import galena.oreganized.OreganizedConfig;
import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;

public class BushHammerItem extends DiggerItem {
    protected static final Map<Block, Block> ENGRAVEABLES = (new ImmutableMap.Builder<Block, Block>().put(Blocks.STONE, Blocks.STONE)).build();

    public BushHammerItem(Tier tier, float attack, float modifier, Item.Properties properties) {
        super(attack, modifier, tier, OTags.Blocks.MINEABLE_WITH_BUSH_HAMMER, properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = world.getBlockState(pos);
        ItemStack item = context.getItemInHand();
        Direction facing = context.getClickedFace();
        Optional<BlockState> emptyState = Optional.empty();
        if (!world.isClientSide && OreganizedConfig.COMMON.engraving.get()) {
            Direction direction = facing.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : facing;
            world.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }


}
