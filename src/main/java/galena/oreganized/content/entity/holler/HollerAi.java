package galena.oreganized.content.entity.holler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.schedule.Activity;

public class HollerAi {

    public HollerAi() {
    }

    protected static Brain<?> makeBrain(Brain<Holler> hollerBrain) {
        initCoreActivity(hollerBrain);
        initIdleActivity(hollerBrain);
        hollerBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        hollerBrain.setDefaultActivity(Activity.IDLE);
        hollerBrain.useDefaultActivity();
        return hollerBrain;
    }

    private static void initCoreActivity(Brain<Holler> hollerBrain) {
        hollerBrain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new AnimalPanic(3F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initIdleActivity(Brain<Holler> hollerBrain) {
        hollerBrain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, SetEntityLookTargetSometimes.create(6.0F, UniformInt.of(30, 60))),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.fly(1.0F), 2),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                ))))
        );
    }

    public static void updateActivity(Holler holler) {
        holler.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }
}
