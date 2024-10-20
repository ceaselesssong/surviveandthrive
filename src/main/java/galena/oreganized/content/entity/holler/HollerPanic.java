package galena.oreganized.content.entity.holler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Predicate;

public class HollerPanic extends AnimalPanic {
    private final float speedMultiplier;

    public HollerPanic(float p_147385_) {
        super(p_147385_);
        this.speedMultiplier = p_147385_;
    }

    @Override
    protected void tick(ServerLevel level, PathfinderMob mob, long p_147405_) {
        if (mob.getNavigation().isDone()) {
            Vec3 $$3 = this.getPanicPos(level, mob);
            if ($$3 != null) {
                mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$3, speedMultiplier, 0));
            }
        }
    }

    private Vec3 getPanicPos(ServerLevel level, PathfinderMob mob) {
        return LandRandomPos.getPosTowards(mob, 5, 4, mob.position().subtract(0, 10, 0));
    }
}
