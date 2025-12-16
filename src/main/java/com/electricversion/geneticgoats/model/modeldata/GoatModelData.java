package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;

public class GoatModelData extends AnimalModelData {
    private float udderSize = 0.0F;

    public GoatPhenotype getPhenotype() {
        return (GoatPhenotype) phenotype;
    }

    public float getUdderSize() {
        return udderSize;
    }

    public void setUdderSize(float udderSize) {
        this.udderSize = udderSize;
    }

    public float getNippleSize() {
        return udderSize >= 1 ? udderSize * 1.375F : 1F ;
    }
}
