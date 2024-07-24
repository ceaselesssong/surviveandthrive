package galena.oreganized.content.entity;

import galena.oreganized.index.OBlockEntities;
import galena.oreganized.index.OTags;
import galena.oreganized.world.ScaredOfGargoyleGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class GargoyleBlockEntity extends BlockEntity {

    private int outputSignal = 0;
    private int updateCooldown = 0;
    private int growlCooldown = 0;

    public GargoyleBlockEntity(BlockPos pos, BlockState state) {
        super(OBlockEntities.GARGOYLE.get(), pos, state);
    }

    private static Collection<Mob> getTargets(Level level, BlockPos pos) {
        var box = new AABB(pos).inflate(10.0);
        return level.getEntitiesOfClass(Mob.class, box, it -> it.getMobType() == MobType.UNDEAD);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GargoyleBlockEntity be) {
        if (--be.updateCooldown > 0) return;

        var targets = getTargets(level, pos);
        var vec = Vec3.atCenterOf(pos);
        var closestDistance = targets.stream()
                .mapToDouble(it -> it.distanceToSqr(vec))
                .map(Math::sqrt)
                .map(Math::floor)
                .sorted()
                .findFirst()
                .orElse(100.0);

        var newOutputSignal = Math.max(14 - (int) closestDistance, 0);

        if (newOutputSignal != be.outputSignal) {
            be.outputSignal = newOutputSignal;
            level.updateNeighbourForOutputSignal(pos, be.getBlockState().getBlock());
        }

        be.updateCooldown = 10;
    }

    public int getAnalogOutputSignal() {
        return outputSignal;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("cooldown", growlCooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("cooldown")) growlCooldown = tag.getInt("cooldown");
    }

    public InteractionResult interact(Level level, BlockPos pos, @Nullable Player player, ItemStack stack) {
        if (!stack.is(OTags.Items.GARGOYLE_SNACK)) return InteractionResult.PASS;

        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        getTargets(level, pos).forEach(mob -> {
            mob.getPersistentData().put(ScaredOfGargoyleGoal.AVOID_TAG_KEY, NbtUtils.writeBlockPos(pos));
        });

        return InteractionResult.SUCCESS;
    }
}
