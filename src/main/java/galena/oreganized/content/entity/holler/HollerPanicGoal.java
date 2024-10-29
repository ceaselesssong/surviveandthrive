package galena.oreganized.content.entity.holler;

import galena.oreganized.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class HollerPanicGoal extends PanicGoal {
    private final Holler holler;

    public HollerPanicGoal(Holler mob, float speedMultiplier) {
        super(mob, speedMultiplier);
        holler = mob;
    }

    @Override
    public void stop() {
        var target = new Vec3(posX, posY, posZ);
        holler.panicFinish(target);
        super.stop();
    }

    @Override
    protected boolean findRandomPosition() {
        var pos = getPanicPos();

        posX = pos.x;
        posY = pos.y;
        posZ = pos.z;

        return true;
    }

    private Vec3 getPanicPos() {
        Optional<Vec3> dirtPosition = findTargetPos().map(Vec3::atBottomCenterOf);
        return dirtPosition.orElseGet(() -> LandRandomPos.getPos(mob, 5, 5));
    }

    private Optional<BlockPos> findTargetPos() {
        BlockPos hollerPos = mob.blockPosition();
        return BlockPos.findClosestMatch(hollerPos, 10, 10, pos -> {
            var state = mob.level().getBlockState(pos);
            return state.is(Blocks.JUKEBOX) && !state.getValue(JukeboxBlock.HAS_RECORD);
        }).or(() -> BlockPos.findClosestMatch(hollerPos, 10, 10, pos -> {
            var state = mob.level().getBlockState(pos);
            return state.is(OTags.Blocks.CAN_TURN_INTO_BURIAL_DIRT);
        }));
    }
}
