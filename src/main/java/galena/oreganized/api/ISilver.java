package galena.oreganized.api;

import galena.oreganized.block.SilverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public interface ISilver {
    float RANGE = 24.0F;

    default List<Entity> getEntities(Level world, BlockPos pos, @Nullable Player player) {
        return world.getEntities((Entity) player,
                new AABB(pos.getX() + SilverBlock.RANGE, pos.getY() + SilverBlock.RANGE, pos.getZ() + SilverBlock.RANGE,
                        pos.getX() - SilverBlock.RANGE, pos.getY() - SilverBlock.RANGE, pos.getZ() - SilverBlock.RANGE),
                (Entity living) -> living instanceof LivingEntity && ((LivingEntity) living).isInvertedHealAndHarm()
        );
    }

    default boolean isUndeadNearby(Level world, BlockPos pos, @Nullable Player player) {
        for (Entity e : getEntities(world, pos, player)) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                return true;
            }
        }
        return false;
    }

    default int getUndeadDistance(Level world, BlockPos pos, @Nullable Player player, int frames) {
        int dist = frames;

        for (Entity e : getEntities(world, pos, player)) {
            LivingEntity living = (LivingEntity) e;
            double distance = player != null ? living.distanceTo(player) : Math.sqrt(living.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()));
            double ceil = Math.ceil(distance / (SilverBlock.RANGE / frames));
            if (distance < SilverBlock.RANGE && ((int) ceil) < dist) {
                if (distance < 6) {
                    dist = 1;
                } else dist = Math.max( (int) ceil, 2);

                if (dist > frames) {
                    dist = frames;
                }
            }
        }

        if (!this.isUndeadNearby(world, pos, player)) {
            dist = frames;
        }

        return dist;
    }
}
