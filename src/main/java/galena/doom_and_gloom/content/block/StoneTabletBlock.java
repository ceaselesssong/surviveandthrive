package galena.doom_and_gloom.content.block;

import galena.doom_and_gloom.index.OBlockEntities;
import galena.doom_and_gloom.network.DGNetwork;
import galena.doom_and_gloom.network.packet.EngraveStoneTabletPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.UUID;

public class StoneTabletBlock extends Block implements SimpleWaterloggedBlock, TickingEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Attachment> ATTACHMENT = EnumProperty.create("attachment", Attachment.class);
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    protected static final VoxelShape SHAPE_Z = Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0);
    protected static final VoxelShape SHAPE_X = Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0);

    protected static final VoxelShape SHAPE_SOUTH = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 4.0D);
    protected static final VoxelShape SHAPE_NORTH = Block.box(2.0D, 0.0D, 12.0D, 14.0D, 16.0D, 16.0D);

    protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 2.0D, 4.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_WEST = Block.box(12.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);

    protected static final VoxelShape SHAPE_FLOOR_Z = Block.box(2.0, 0.0, 0.0, 14.0, 4.0, 16.0);
    protected static final VoxelShape SHAPE_FLOOR_X = Block.box(0.0, 0.0, 2.0, 16.0, 4.0, 14.0);

    protected static final VoxelShape SHAPE_CEILING_Z = Block.box(2.0, 0.0, 12.0, 14.0, 16.0, 16.0);
    protected static final VoxelShape SHAPE_CEILING_X = Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 14.0);

    public StoneTabletBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH).setValue(ATTACHMENT, Attachment.CENTER_UPRIGHT)
                .setValue(TYPE, Type.DEFAULT));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        boolean water = pContext.getLevel().getFluidState(pos).getType() == Fluids.WATER;
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED, water);

        Level level = pContext.getLevel();

        Direction clickFace = pContext.getClickedFace();
        if (clickFace.getAxis() == Direction.Axis.Y) {
            BlockState below = level.getBlockState(pos.relative(clickFace.getOpposite()));
            if (below.getBlock() instanceof StoneTabletBlock && below.getValue(ATTACHMENT).isUpright()) {
                return state.setValue(FACING, below.getValue(FACING))
                        .setValue(ATTACHMENT, below.getValue(ATTACHMENT));
            }

            Player p = pContext.getPlayer();
            if (p != null && p.isShiftKeyDown()) {
                return state.setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                        .setValue(ATTACHMENT, clickFace == Direction.UP ? Attachment.CEILING : Attachment.FLOOR);
            }

            return state.setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                    .setValue(ATTACHMENT, Attachment.CENTER_UPRIGHT);
        } else {
            return this.defaultBlockState().setValue(FACING, clickFace)
                    .setValue(ATTACHMENT, Attachment.WALL);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED, FACING, ATTACHMENT, TYPE);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(ATTACHMENT)) {
            case FLOOR -> switch (pState.getValue(FACING)) {
                case NORTH, SOUTH -> SHAPE_FLOOR_Z;
                default -> SHAPE_FLOOR_X;
            };

            case CEILING -> switch (pState.getValue(FACING)) {
                case NORTH, SOUTH -> SHAPE_CEILING_Z;
                default -> SHAPE_CEILING_X;
            };

            case WALL -> switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };

            case CENTER_UPRIGHT -> switch (pState.getValue(FACING)) {
                case NORTH, SOUTH -> SHAPE_X;
                default -> SHAPE_Z;
            };
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StoneTabletBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pLevel.getBlockEntity(pPos) instanceof StoneTabletBlockEntity tile) {
            if (pPlayer instanceof ServerPlayer sp) {
                if (!this.otherPlayerIsEditingSign(pPlayer, tile) && pPlayer.mayBuild() &&
                        this.hasEditableText(pPlayer, tile)) {
                    this.openTextEdit(sp, tile);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private boolean hasEditableText(Player pPlayer, StoneTabletBlockEntity pSignEntity) {
        StoneTabletText signText = pSignEntity.getText();
        return Arrays.stream(signText.getMessages(pPlayer.isTextFilteringEnabled()))
                .allMatch((p) -> p.equals(CommonComponents.EMPTY) || p.getContents() instanceof LiteralContents);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof StoneTabletBlockEntity tile) {
            ItemStack stack = this.asItem().getDefaultInstance();
            CompoundTag tag = new CompoundTag();
            tag.put("BlockEntityTag", tile.saveWithoutMetadata());
            CompoundTag c = new CompoundTag();
            c.putString("type", state.getValue(TYPE).getSerializedName());
            tag.put("BlockStateTag", c);
            stack.setTag(tag);
            return stack;
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    public void openTextEdit(ServerPlayer pPlayer, StoneTabletBlockEntity pSignEntity) {
        pSignEntity.setAllowedPlayerEditor(pPlayer.getUUID());
        DGNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> pPlayer),
                new EngraveStoneTabletPacket(pSignEntity.getBlockPos()));
    }

    private boolean otherPlayerIsEditingSign(Player pPlayer, StoneTabletBlockEntity pSignEntity) {
        UUID id = pSignEntity.getPlayerWhoMayEdit();
        return id != null && !id.equals(pPlayer.getUUID());
    }

    @Override
    public BlockEntityType<?> getType() {
        return OBlockEntities.STONE_TABLET.get();
    }

    public enum Type implements StringRepresentable {
        DEFAULT, ENGRAVED, CRACKED;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }

        public boolean canEdit() {
            return this == DEFAULT;
        }
    }

    public enum Attachment implements StringRepresentable {
        CENTER_UPRIGHT, WALL, CEILING, FLOOR;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }

        public boolean isUpright() {
            return this == CENTER_UPRIGHT || this == WALL;
        }
    }
}