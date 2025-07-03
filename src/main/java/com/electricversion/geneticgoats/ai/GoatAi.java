package com.electricversion.geneticgoats.ai;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import com.electricversion.geneticgoats.init.AddonEntities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mokiyoki.enhancedanimals.ai.brain.Grazing;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.schedule.Activity;

public class GoatAi {

    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 0.75F;

    public static Brain<?> makeBrain(Brain<EnhancedGoat> goatBrain) {
        initCoreActivity(goatBrain);
        initIdleActivity(goatBrain);

        goatBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        goatBrain.setDefaultActivity(Activity.IDLE);
        goatBrain.useDefaultActivity();
        return goatBrain;
    }

    private static void initCoreActivity(Brain<EnhancedGoat> goatBrain) {
        goatBrain.addActivity(Activity.CORE, 0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        new Grazing(),
                        new CountDownCooldownTicks(ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get())
        )
        );
    }

    private static void initIdleActivity(Brain<EnhancedGoat> goatBrain) {
        goatBrain.addActivity(Activity.IDLE, 10,
                ImmutableList.of(
                        new AnimalMakeLove(AddonEntities.ENHANCED_GOAT.get(), 0.6F),
                        new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60)),
                        new RunOne<>(ImmutableList.of(
                                Pair.of(new RandomStroll(SPEED_MULTIPLIER_WHEN_IDLING), 2),
                                Pair.of(new SetWalkTargetFromLookTarget(SPEED_MULTIPLIER_WHEN_IDLING, 3), 2),
                                Pair.of(new DoNothing(30, 60), 1))
                        )
                )
        );
    }

    public static void updateActivity(EnhancedGoat goat) {
        Brain<EnhancedGoat> brain = goat.getBrain();
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }
}
