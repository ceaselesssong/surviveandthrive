package galena.oreganized.content.block;

import galena.oreganized.index.OBlockEntities;
import galena.oreganized.network.OreganizedNetwork;
import galena.oreganized.network.packet.EngraveStoneTabletPacket;
import net.mehvahdjukaar.moonlight.api.platform.network.forge.ChannelHandlerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;

import java.util.Arrays;
import java.util.UUID;

public class StoneTabletBlock extends Block implements SimpleWaterloggedBlock, TickingEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ATTACHED_TO_WALL = BlockStateProperties.ATTACHED;
    public static final BooleanProperty ENGRAVED = BooleanProperty.create("engraved");

    protected static final VoxelShape SHAPE_X = Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0);
    protected static final VoxelShape SHAPE_Z = Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0);

    protected static final VoxelShape SHAPE_SOUTH = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 4.0D);
    protected static final VoxelShape SHAPE_NORTH = Block.box(2.0D, 0.0D, 12.0D, 14.0D, 16.0D, 16.0D);

    protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 2.0D, 4.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_WEST = Block.box(12.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);


    public StoneTabletBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH).setValue(ATTACHED_TO_WALL, false)
                .setValue(ENGRAVED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED, FACING, ATTACHED_TO_WALL, ENGRAVED);
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
        if (pState.getValue(ATTACHED_TO_WALL)) {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };
        } else {
            return switch (pState.getValue(FACING)) {
                case NORTH, SOUTH -> SHAPE_X;
                default -> SHAPE_Z;
            };
        }
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

    public void openTextEdit(ServerPlayer pPlayer, StoneTabletBlockEntity pSignEntity) {
        pSignEntity.setAllowedPlayerEditor(pPlayer.getUUID());
        OreganizedNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> pPlayer),
                new EngraveStoneTabletPacket(pSignEntity.getBlockPos()));
    }

    private boolean otherPlayerIsEditingSign(Player pPlayer, StoneTabletBlockEntity pSignEntity) {
        UUID id = pSignEntity.getPlayerWhoMayEdit();
        return id != null && !id.equals(pPlayer.getUUID());
    }

    @Override
    public BlockEntityType getType() {
        return OBlockEntities.STONE_TABLET.get();
    }
}