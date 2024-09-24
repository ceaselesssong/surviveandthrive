package galena.oreganized.content.block;

import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MoltenLeadBlock extends LiquidBlock {

    public static final BooleanProperty MOVING = BooleanProperty.create("moving");

    public static final VoxelShape STABLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public MoltenLeadBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties.noCollission().strength(-1.0F, 3600000.0F).noLootTable().lightLevel((state) -> 8));
        this.registerDefaultState(this.stateDefinition.any().setValue(MOVING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MOVING);
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.WALKABLE;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pIsMoving) {
            if (!pOldState.getFluidState().is(FluidTags.WATER)) {
                pLevel.setBlock(pPos, pState.setValue(MOVING, true), 3);
            } else {
                pLevel.levelEvent(1501, pPos, 0);
                pLevel.setBlock(pPos, OBlocks.LEAD_BLOCK.get().defaultBlockState(), 3);
            }
        } else {
            if (!pOldState.getFluidState().is(FluidTags.WATER)) {
                pLevel.setBlock(pPos, pState.setValue(MOVING, false), 3);
            } else {
                pLevel.levelEvent(1501, pPos, 0);
                pLevel.setBlock(pPos, OBlocks.LEAD_BLOCK.get().defaultBlockState(), 3);
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter world, BlockPos blockPos, CollisionContext ctx) {
        if (ctx instanceof EntityCollisionContext eCtx && eCtx.getEntity() != null)
            return ctx.isAbove(STABLE_SHAPE, blockPos, true) && isEntityLighterThanLead(eCtx.getEntity()) ? STABLE_SHAPE : Shapes.empty();

        return super.getCollisionShape(blockState, world, blockPos, ctx);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState replacedWith, boolean dropXp) {
        super.onRemove(state, level, pos, replacedWith, dropXp);

        if (replacedWith.isAir() || !replacedWith.getFluidState().isEmpty()) return;

        tryEscape(state, level, pos);
    }

    private boolean tryEscape(BlockState state, Level level, BlockPos pos, Direction direction) {
        var adjancentPos = pos.relative(direction);
        var adjacentState = level.getBlockState(adjancentPos);
        if (adjacentState.canBeReplaced(getFluid())) {
            level.setBlockAndUpdate(adjancentPos, state);

            getPickupSound().ifPresent(sound -> {
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1F, 1F);
            });

            return true;
        }

        return false;
    }

    private boolean tryEscape(BlockState state, Level level, BlockPos pos) {
        if (tryEscape(state, level, pos, Direction.DOWN)) return true;
        for (var direction : Direction.allShuffled(level.random)) {
            if (direction.getAxis().isHorizontal() && tryEscape(state, level, pos, direction)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.getY() < pos.getY() + STABLE_SHAPE.max(Direction.Axis.Y)) {
            if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
                entity.makeStuckInBlock(state, new Vec3(0.9F, 1.0D, 0.9F));
            }

            entity.setSecondsOnFire(10);
            if (!world.isClientSide) entity.setSharedFlagOnFire(true);
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!((double) fallDistance < 4.0D) && entity instanceof LivingEntity living) {
            LivingEntity.Fallsounds fallSound = living.getFallSounds();
            SoundEvent sound = (double) fallDistance < 7.0D ? fallSound.small() : fallSound.big();
            entity.playSound(sound, 1.0F, 1.0F);
        }
    }

    public static boolean isEntityLighterThanLead(Entity entity) {
        if (entity.getType().is(OTags.Entities.LIGHTER_THAN_LEAD)) {
            return true;
        } else {
            return entity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.FEET).is(OTags.Items.LIGHTER_THAN_LEAD);
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType pathFinder) {
        return true;
    }
}
