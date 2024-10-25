package galena.oreganized.content.entity.holler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
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
            Vec3 panicPosition = this.getPanicPos(level, (Holler) mob);
            if (panicPosition != null) {
                mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(panicPosition, speedMultiplier, 0));
            }
        }
    }

    private Vec3 getPanicPos(ServerLevel level, Holler holler) {
        Optional<Vec3> dirtPosition = lookForDirt(level, holler).map(Vec3::atBottomCenterOf);
        return dirtPosition.orElseGet(() -> LandRandomPos.getPos(holler, 5, 5));
    }

    private Optional<BlockPos> lookForDirt(BlockGetter blockGetter, Holler holler) {
        BlockPos hollerPos = holler.blockPosition();
        Predicate<BlockPos> isDirtBlock = (pos) -> (blockGetter.getBlockState(pos).getBlock() instanceof SnowyDirtBlock || blockGetter.getBlockState(pos).is(Blocks.DIRT));
        return BlockPos.findClosestMatch(hollerPos, 10, 10, isDirtBlock);
    }
}
