package galena.oreganized.index;

import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class OSoundTypes {
    public static final SoundType BONE_PILE = new ForgeSoundType(1.0F, 1.0F,  OSoundEvents.BONE_PILE_BREAK, OSoundEvents.BONE_PILE_STEP, OSoundEvents.BONE_PILE_PLACE, OSoundEvents.BONE_PILE_HIT, OSoundEvents.BONE_PILE_FALL);
    public static final SoundType SEPULCHER = new ForgeSoundType(1.0F, 1.0F,  OSoundEvents.SEPULCHER_BREAK, OSoundEvents.SEPULCHER_STEP, OSoundEvents.SEPULCHER_PLACE, OSoundEvents.SEPULCHER_HIT, OSoundEvents.SEPULCHER_FALL);
}
