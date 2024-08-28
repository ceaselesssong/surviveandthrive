package galena.oreganized.compat.create;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

public class CreateCompat {

    public static void register() {
        ArmInteractionPointType.register(new GargoyleArmPointType());
    }

}
