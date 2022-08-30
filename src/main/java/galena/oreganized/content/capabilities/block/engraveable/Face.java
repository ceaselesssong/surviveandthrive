package galena.oreganized.content.capabilities.block.engraveable;

import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public enum Face {
    UP(new Vector3f(0.05F, 1.0007F, 0.19F), new Vector3f(90F, 0F, 0F), Direction.UP),
    DOWN(new Vector3f(0.95F, -0.017F, 0.19F), new Vector3f(-90F, 0F, 180F), Direction.DOWN),
    LEFT(new Vector3f(-0.0007f,0.81f,0.05f), new Vector3f(0f,90f,180f), Direction.WEST),
    RIGHT(new Vector3f(1.0007f,0.81f, 0.95f), new Vector3f(0f,-90f,180f), Direction.EAST),
    FRONT(new Vector3f(0.95f,0.81f,-0.0007f), new Vector3f(0f,0f,180f), Direction.NORTH),
    BACK(new Vector3f(0.05f,0.81f, 1.0007f), new Vector3f(0f,180f,180f), Direction.SOUTH);

    public final Vector3f translation;
    public final Vector3f rotation;
    public final Direction direction;

    public static final int LINES = 7;
    public static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4", "Text5", "Text6", "Text7"};
    public static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4", "FilteredText5", "FilteredText6", "FilteredText7"};
    public final Component[] messages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    public final Component[] filteredMessages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};

    Face(Vector3f translation, Vector3f rotation, Direction direction) {
        this.translation = translation;
        this.rotation = rotation;
        this.direction = direction;
    }
}
