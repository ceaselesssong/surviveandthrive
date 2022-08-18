package galena.oreganized.content.block;

import galena.oreganized.content.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.function.Supplier;

public class MoltenLeadBlock extends LiquidBlock {

    public MoltenLeadBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties.noCollission().strength(-1.0F, 3600000.0F).noLootTable().lightLevel((state) -> 8));
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            entity.makeStuckInBlock(state, new Vec3(0.9F, 1.0D, 0.9F));
        }

        entity.setSecondsOnFire(10);
        if (!world.isClientSide) entity.setSharedFlagOnFire(true);
    }

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!((double)fallDistance < 4.0D) && entity instanceof LivingEntity living) {
            LivingEntity.Fallsounds fallSound = living.getFallSounds();
            SoundEvent sound = (double)fallDistance < 7.0D ? fallSound.small() : fallSound.big();
            entity.playSound(sound, 1.0F, 1.0F);
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if(entity != null) {
                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || isEntityLighterThanLead(entity) && context.isAbove(Shapes.block(), pos, false)) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }

        return Shapes.empty();
    }

    public static boolean isEntityLighterThanLead(Entity entity) {
        if (entity.getType().is(OTags.Entities.LIGHTER_THAN_LEAD)) {
            return true;
        } else {
            return entity instanceof LivingEntity && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(Items.IRON_BOOTS);
        }
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType pathFinder) {
        return true;
    }
}
