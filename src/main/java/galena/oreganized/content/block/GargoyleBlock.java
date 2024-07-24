package galena.oreganized.content.block;

import galena.oreganized.content.entity.GargoyleBlockEntity;
import galena.oreganized.index.OBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class GargoyleBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final EnumProperty<AttachmentType> ATTACHMENT = EnumProperty.create("attachment", AttachmentType.class);

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = (source, stack) -> {
        var dispenser = source.getBlockState();
        var facing = dispenser.getValue(DispenserBlock.FACING);
        var targetPos = source.getPos().relative(facing);
        var target = source.getLevel().getBlockEntity(targetPos);

        if (target instanceof GargoyleBlockEntity gargoyle) {
            gargoyle.interact(source.getLevel(), targetPos, null, stack);
        }

        return stack;
    };

    public GargoyleBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(HORIZONTAL_FACING, Direction.SOUTH).setValue(ATTACHMENT, AttachmentType.FLOOR));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING, ATTACHMENT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var base = super.getStateForPlacement(context);
        var clickedFace = context.getClickedFace();

        if (clickedFace.getAxis().isHorizontal()) {
            return base.setValue(HORIZONTAL_FACING, clickedFace.getOpposite()).setValue(ATTACHMENT, AttachmentType.WALL);
        } else {
            return base.setValue(HORIZONTAL_FACING, context.getHorizontalDirection()).setValue(ATTACHMENT, AttachmentType.FLOOR);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (type != OBlockEntities.GARGOYLE.get()) return null;
        BlockEntityTicker<GargoyleBlockEntity> ticker = GargoyleBlockEntity::tick;
        //noinspection unchecked
        return (BlockEntityTicker<T>) ticker;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof GargoyleBlockEntity gargoyle) {
            return gargoyle.getAnalogOutputSignal();
        }

        return super.getAnalogOutputSignal(state, level, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof GargoyleBlockEntity gargoyle) {
            var stack = player.getItemInHand(hand);
            return gargoyle.interact(level, pos, player, stack);
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (random.nextBoolean() && level.isRainingAt(pos.above())) {
            var facing = state.getValue(FACING).getOpposite();
            var attachment = state.getValue(ATTACHMENT);
            double spread = random.nextDouble() * 0.1 - 0.05;
            double offsetX = facing.getAxis() == Direction.Axis.X ? facing.getStepX() * attachment.horizontalOffset : spread;
            double offsetZ = facing.getAxis() == Direction.Axis.Z ? facing.getStepZ() * attachment.horizontalOffset : spread;

            double x = pos.getX() + 0.5 + offsetX;
            double y = pos.getY() + attachment.verticalOffset;
            double z = pos.getZ() + 0.5 + offsetZ;
            level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GargoyleBlockEntity(pos, state);
    }

    public enum AttachmentType implements StringRepresentable {
        FLOOR("floor", 0.55, 0.2),
        WALL("wall", 1.3, 0.12);

        private final String name;
        private final double horizontalOffset;
        private final double verticalOffset;

        AttachmentType(String name, double horizontalOffset, double verticalOffset) {
            this.name = name;
            this.horizontalOffset = horizontalOffset;
            this.verticalOffset = verticalOffset;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
