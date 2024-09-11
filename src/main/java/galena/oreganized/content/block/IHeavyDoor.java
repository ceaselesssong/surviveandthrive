package galena.oreganized.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface IHeavyDoor {

    void sound(@Nullable Player player, Level level, BlockPos pos, boolean open);

}
