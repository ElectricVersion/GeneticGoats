package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;

public class GoatModelData extends AnimalModelData {
    public GoatPhenotype getPhenotype() {
        return (GoatPhenotype) phenotype;
    }
}
