package galena.doom_and_gloom.content.entity.holler;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class HollerAvoidGoal extends Goal {

    private final Holler mob;
    private final double atDistance;
    private final float speedModifier;
    private final TargetingConditions targetingCondition;
    private Path path = null;


    public HollerAvoidGoal(Holler mob, double atDistance, float speedModifier) {
        this.mob = mob;
        this.atDistance = atDistance;
        this.speedModifier = speedModifier;
        this.targetingCondition = TargetingConditions.forNonCombat().range(atDistance);
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
    }

    @Override
    public boolean canUse() {
        var avoid = mob.level().getNearestEntity(mob.level().getEntitiesOfClass(Player.class, mob.getBoundingBox().inflate(atDistance)), targetingCondition, mob, mob.getX(), mob.getY(), mob.getZ());
        if (avoid != null) {
            var target = mob.position().subtract(avoid.position());
            var away = AirRandomPos.getPosTowards(mob, 4, 1, 0, target, 1F);
            if (away != null) path = mob.getNavigation().createPath(away.x, away.y, away.z, 0);
        } else {
            path = null;
        }
        return path != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone() || canUse();
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(path, speedModifier);
    }

}
