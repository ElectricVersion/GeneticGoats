package com.electricversion.geneticgoats.ai;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FaintGoal extends Goal {
    private final EnhancedGoat enhancedGoat;
    private int startTick = -1;

    public FaintGoal(EnhancedGoat enhancedGoat) {
        this.enhancedGoat = enhancedGoat;
        setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public void start() {
        enhancedGoat.getNavigation().stop();
        enhancedGoat.setFainted(true);
        startTick = enhancedGoat.tickCount;
    }

    @Override
    public void stop() {
        enhancedGoat.setFainted(false);
        startTick = -1;
    }

    @Override
    public boolean canUse() {
        // TODO: Make conditional on fainting gene
        if (enhancedGoat.getLastHurtByMob() != null) {
            return startTick == -1 || enhancedGoat.tickCount <= startTick + 100;
        }
        return false;
    }

}
