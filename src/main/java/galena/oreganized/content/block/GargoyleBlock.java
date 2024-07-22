package galena.oreganized.content.block;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class GargoyleBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<AttachmentType> ATTACHMENT = EnumProperty.create("attachment", AttachmentType.class);

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

    public enum AttachmentType implements StringRepresentable {
        FLOOR("floor"),
        WALL("wall");

        private final String name;

        AttachmentType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
