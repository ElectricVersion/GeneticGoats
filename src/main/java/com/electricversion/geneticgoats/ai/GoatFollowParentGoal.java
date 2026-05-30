package com.electricversion.geneticgoats.ai;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class GoatFollowParentGoal extends FollowParentGoal {

    private final EnhancedGoat goat;

    public GoatFollowParentGoal(EnhancedGoat goat, double speedModifier) {
        super(goat, speedModifier);
        this.goat = goat;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !goat.isFainted();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !goat.isFainted();
    }
}
