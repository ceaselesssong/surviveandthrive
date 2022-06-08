package me.gleep.oreganized.blocks;

import me.gleep.oreganized.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SilverOrnamentBars extends SilverBars {

    public static final float RANGE = 24.0f;
    boolean isUndeadNearby = false;

    public SilverOrnamentBars(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, final Level level, final BlockPos pos, final @NotNull Random random) {
        int dist = 4;

        List<Entity> list = level.getEntities((Entity) null,
                new AABB(pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE,
                        pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE), (Entity entity) -> entity instanceof LivingEntity
        );

        for (Entity e : list) {
            LivingEntity living = (LivingEntity) e;
            if (living.isInvertedHealAndHarm()) {
                isUndeadNearby = true;
                double distance = Math.sqrt(living.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()));
                if (((int) Math.ceil(distance / (RANGE / 4))) < 4) {
                    dist = (int) Math.ceil(distance / (RANGE / 4));

                    /*if (dist > 7) {
                        dist = 7;
                    }*/
                }
            }
        }

        if (!isUndeadNearby) {
            dist = 4;
        }
        level.setBlockAndUpdate(pos, state.setValue(LEVEL, dist - 1));
        level.scheduleTick( pos, state.getBlock(), 1);
    }
}
