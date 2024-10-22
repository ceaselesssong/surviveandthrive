package galena.oreganized.content.block;

import galena.oreganized.content.entity.SepulcherBlockEntity;
import galena.oreganized.index.OBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SepulcherBlock extends Block implements TickingEntityBlock<SepulcherBlockEntity> {

    public static final int MAX_LEVEL = 8;
    public static final int BONES_LEVEL = 8;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, BONES_LEVEL);

    public SepulcherBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.LEVEL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var fillLevel = state.getValue(LEVEL);

        if(fillLevel == BONES_LEVEL) {
            extractBones(player, state, level, pos);
        }
    }


    public static void extractBones(Entity user, BlockState state, Level level, BlockPos pos) {
        if (!level.isClientSide) {
            Vec3 $$4 = Vec3.atLowerCornerWithOffset(pos, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
            ItemEntity $$5 = new ItemEntity(level, $$4.x(), $$4.y(), $$4.z(), new ItemStack(Items.BONE_MEAL));
            $$5.setDefaultPickUpDelay();
            level.addFreshEntity($$5);
        }

        BlockState $$6 = empty(user, state, level, pos);
        level.playSound((Player)null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return $$6;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SepulcherBlockEntity(pos, state);
    }

    @Override
    public BlockEntityType<SepulcherBlockEntity> getType() {
        return OBlockEntities.SEPULCHER.get();
    }
}
