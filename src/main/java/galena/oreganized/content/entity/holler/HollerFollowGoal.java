package galena.oreganized.content.entity.holler;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class HollerFollowGoal extends Goal {

    private final PathfinderMob mob;
    private final double maxDistanceSquared;
    private final double panicDistanceSquared;

    public HollerFollowGoal(PathfinderMob mob, double maxDistance, double panicDistance) {
        this.mob = mob;
        this.maxDistanceSquared = maxDistance * maxDistance;
        this.panicDistanceSquared = panicDistance * panicDistance;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && !mob.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING);
    }

    @Override
    public void start() {
        var target = mob.getTarget();
        var path = mob.getNavigation().createPath(target, 0);
        mob.getNavigation().moveTo(path, 1F);
    }

    @Override
    public void tick() {
        var target = mob.getTarget();
        var brain = mob.getBrain();
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));

        var distanceSquared = mob.distanceToSqr(target);

        if (distanceSquared < panicDistanceSquared) {
            brain.setMemory(MemoryModuleType.IS_PANICKING, true);
        }

        if (distanceSquared < maxDistanceSquared) {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
            mob.setTarget(null);
        } else {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(target, false), 1F, 2));
        }

    }

}
