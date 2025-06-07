package com.electricversion.geneticgoats.entity;

import com.electricversion.geneticgoats.config.GoatsCommonConfig;
import com.electricversion.geneticgoats.entity.genetics.GoatGeneticsInitializer;
import com.electricversion.geneticgoats.entity.texture.GoatTexture;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import static com.electricversion.geneticgoats.init.AddonEntities.ENHANCED_GOAT;

public class EnhancedGoat extends EnhancedAnimalAbstract {

    public static final int AUTOSOMAL_GENES_LENGTH = 2;
    public static final int SEXLINKED_GENES_LENGTH = 2;

    public EnhancedGoat(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn) {
        super(type, worldIn, SEXLINKED_GENES_LENGTH, AUTOSOMAL_GENES_LENGTH, true);
        initilizeAnimalSize();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }


    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_goat";
    }

    @Override
    protected int getAdultAge() {
        return GoatsCommonConfig.COMMON.growthTime.get();
    }

    @Override
    protected int gestationConfig() {
        return GoatsCommonConfig.COMMON.birthTime.get();
    }

    @Override
    protected void incrementHunger() {
        hunger += (sleeping ? 0.5F : 1F) * getHungerModifier();
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }

    @Override
    protected void lethalGenes() {
    }

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
    protected void setTexturePaths() {
        if (getGenes() != null) {
            GoatTexture.calculateTexture(this, getGenes().getAutosomalGenes(), getStringUUID().toCharArray());
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
        // Unused in newer GA animals and can probably be considered deprecated.
    }

    @Override
    public void initilizeAnimalSize() {
        setAnimalSize(1F);
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract otherParent) {
        EnhancedGoat goat = ENHANCED_GOAT.get().create(level);
        Genes babyGenes = new Genes(getGenes()).makeChild(getOrSetIsFemale(), otherParent.getOrSetIsFemale(), otherParent.getGenes());
        if (goat != null) {
            goat.setGenes(babyGenes);
            goat.setSharedGenes(babyGenes);
            goat.setSireName(otherParent.getCustomName() == null ? "???" : otherParent.getCustomName().getString());
            goat.setDamName(getCustomName() == null ? "???" : getCustomName().getString());
            goat.setParent(getUUID().toString());
            goat.setGrowingAge();
            goat.setBirthTime();
            goat.initilizeAnimalSize();
            goat.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
            goat.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
            goat.setInitialDefaults();
        }
        return goat;
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level level) {
        EnhancedGoat goat = ENHANCED_GOAT.get().create(level);
        Genes babyGenes = new Genes(genetics).makeChild(getOrSetIsFemale(), mateGender, mateGenetics);
        defaultCreateAndSpawn(goat, level, babyGenes, -getAdultAge());
        level.addFreshEntity(goat);
        goat.setInitialDefaults();
    }

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return true;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return null;
    }

    @Override
    protected void fixGeneLengths() {
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor levelAccessor, BlockPos blockPos, boolean isBreed) {
        return new GoatGeneticsInitializer().generateNewGenetics(levelAccessor, blockPos, isBreed);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor levelAccessor, BlockPos blockPos, String breedName) {
        return new GoatGeneticsInitializer().generateWithBreed(levelAccessor, blockPos, breedName);
    }

}
