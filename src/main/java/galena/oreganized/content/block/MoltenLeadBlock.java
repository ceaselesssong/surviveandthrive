package galena.oreganized.content.block;

import galena.oreganized.content.index.OItems;
import galena.oreganized.content.index.OParticleTypes;
import galena.oreganized.content.index.OTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
    /*
        The following code is a mishmash of PowderSnowBlock with some extras taken from Fluid classes.
     */
    private static final BooleanProperty MOVING = BooleanProperty.create("ismoving");

    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);

    public MoltenLeadBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties.noCollission().strength(-1.0F, 3600000.0F).noLootTable().lightLevel((state) -> 8));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOVING);
    }


    public boolean skipRendering(BlockState thisState, BlockState adjacentState, Direction direction) {
        return adjacentState.is(this) || super.skipRendering(thisState, adjacentState, direction);
    }

    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.getValue(MOVING)) {
            BlockState belowState = world.getBlockState(pos.below());
            if (belowState.isAir()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 67);
                world.setBlock(pos.below(), this.defaultBlockState(), 67);
            }
        } else {
            for (int i = 0; i < random.nextInt(7) + 1; i++) {
                this.trySpawnDripParticles(world, pos, state);
            }
        }

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
                if (entity.fallDistance > 2.5F) {
                    return FALLING_COLLISION_SHAPE;
                }

                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || isEntityLighterThanLead(entity) && context.isAbove(Shapes.block(), pos, false)) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }

        return Shapes.empty();
    }

    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public static boolean isEntityLighterThanLead(Entity entity) {
        if (entity.getType().is(OTags.Entities.LIGHTER_THAN_LEAD)) {
            return true;
        } else {
            return entity instanceof LivingEntity && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(Items.IRON_BOOTS);
        }
    }

    public ItemStack pickupBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        if (!world.isClientSide()) {
            world.levelEvent(2001, pos, Block.getId(state));
        }

        return new ItemStack(OItems.MOLTEN_LEAD_BUCKET.get());
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType pathFinder) {
        return true;
    }

    // I'm 2000% sure that this particle code can be simplified significantly
    private void trySpawnDripParticles(Level world, BlockPos pos, BlockState state) {
        if(state.getFluidState().isEmpty() && !(world.random.nextFloat() < 0.5F)) {
            double d0 = Shapes.block().max(Direction.Axis.Y);
            if (d0 >= 1.0D) {
                double d1 = Shapes.block().min(Direction.Axis.Y);
                if(d1 > 0.0) {
                    this.spawnParticle(world, pos, Shapes.block(), (double) pos.getY() + d1 - 0.05D);
                } else {
                    BlockPos belowPos = pos.below();
                    BlockState belowState = world.getBlockState(belowPos);
                    VoxelShape belowShape = belowState.getCollisionShape(world, belowPos);
                    double d2 = belowShape.max(Direction.Axis.Y);
                    if ((d2 < 1.0D || !belowState.isCollisionShapeFullBlock(world, belowPos)) && belowState.getFluidState().isEmpty()) {
                        this.spawnParticle(world, pos, Shapes.block(), (double) pos.getY() - 0.05D);
                    }
                }
            }
        }
    }

    private void spawnParticle( Level pLevel , BlockPos pPos , VoxelShape pShape , double pY ){
        this.spawnDripParticle( pLevel , (double) pPos.getX() + pShape.min( Direction.Axis.X ) , (double) pPos.getX() + pShape.max( Direction.Axis.X ) , (double) pPos.getZ() + pShape.min( Direction.Axis.Z ) , (double) pPos.getZ() + pShape.max( Direction.Axis.Z ) , pY );
    }


    private void spawnDripParticle(Level world, double x1, double x2, double z1, double z2, double y) {
        world.addParticle(OParticleTypes.DRIPPING_LEAD.get(), Mth.lerp(world.random.nextDouble(), x1, x2), y, Mth.lerp(world.random.nextDouble(), z1, z2), 0.0D, 0.0D, 0.0D);
    }
}
