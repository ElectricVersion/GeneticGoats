package com.electricversion.geneticgoats.entity;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class EnhancedGoat extends EnhancedAnimalAbstract {

    public static final int AUTOSOMAL_GENES_LENGTH = 2;
    public static final int SEXLINKED_GENES_LENGTH = 2;

    public EnhancedGoat(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn) {
        super(type, worldIn, SEXLINKED_GENES_LENGTH, AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_goat";
    }

    @Override
    protected int getAdultAge() {
        return 0;
    }

    @Override
    protected int gestationConfig() {
        return 0;
    }

    @Override
    protected void incrementHunger() {
        hunger += (sleeping ? 0.5F : 1F) * getHungerModifier();
    }

    @Override
    protected void runExtraIdleTimeTick() {}

    @Override
    protected void lethalGenes() {}

    @Override
    public String getTexture() {
        if (enhancedAnimalTextureGrouping == null) {
            setTexturePaths();
        } else if (reload && !isBaby()) {
            reload = false;
            reloadTextures();
        }

        return getCompiledTextures("enhanced_goat");
    }

    @Override
    protected void setTexturePaths() {}

    @Override
    protected void setAlphaTexturePaths() {}

    @Override
    public void initilizeAnimalSize() {}

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract enhancedAnimalAbstract) {
        return null;
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level level) {}

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return null;
    }

    @Override
    protected void fixGeneLengths() {}

    @Override
    protected Genes createInitialGenes(LevelAccessor levelAccessor, BlockPos blockPos, boolean b) {
        return null;
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor levelAccessor, BlockPos blockPos, String s) {
        return null;
    }
}
