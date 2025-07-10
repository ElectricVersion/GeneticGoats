package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.Phenotype;

// The phenotype class is used for storing model data related to immutable genetic traits that need to be accessed by the model
public class GoatPhenotype implements Phenotype {
    public EarPlacement getEarPlacement() {
        return earPlacement;
    }

    public EarLength getEarLength() {
        return earLength;
    }

    public enum EarLength {
        NORMAL,
        LONG,
        EXTRA_LONG,
        LONGEST,
    }

    public enum EarPlacement {
        HIGH,
        MEDIUM,
        LOW,
    }

    private EarLength earLength;
    private EarPlacement earPlacement;

    public GoatPhenotype(int[] gene, boolean isFemale) {
        earLength = EarLength.LONGEST;
        earPlacement = EarPlacement.MEDIUM;
    }
}
