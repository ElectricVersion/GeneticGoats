package com.electricversion.geneticgoats.entity;

import com.electricversion.geneticgoats.ai.GoatAi;
import com.electricversion.geneticgoats.config.GoatsCommonConfig;
import com.electricversion.geneticgoats.entity.genetics.GoatGeneticsInitializer;
import com.electricversion.geneticgoats.entity.texture.GoatTexture;
import com.electricversion.geneticgoats.init.AddonEntities;
import com.electricversion.geneticgoats.init.AddonItems;
import com.electricversion.geneticgoats.model.modeldata.GoatModelData;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TextureLayer;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EnhancedGoat extends EnhancedAnimalAbstract {

    @OnlyIn(Dist.CLIENT)
    private GoatModelData goatModelData;

    /* Initialization & General Properties */
    public EnhancedGoat(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn) {
        super(type, worldIn, GoatGeneticsInitializer.SEXLINKED_GENES_LENGTH, GoatGeneticsInitializer.AUTOSOMAL_GENES_LENGTH, true);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new MoveControl(this);
        this.lookControl = new LookControl(this);
        initilizeAnimalSize();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor levelAccessor, BlockPos blockPos, boolean isBreed) {
        return new GoatGeneticsInitializer().generateNewGenetics(levelAccessor, blockPos, isBreed);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor levelAccessor, BlockPos blockPos, String breedName) {
        return new GoatGeneticsInitializer().generateWithBreed(levelAccessor, blockPos, breedName);
    }

    @Override
    public void initilizeAnimalSize() {
        setAnimalSize(1F);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag itemNbt) {
        // ageMinimum and ageMaximum parameters are unused so just put zeroes to avoid confusion
        return commonInitialSpawnSetup(inWorld, livingData, getAdultAge(), 0, 0, spawnReason);
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract otherParent) {
        EnhancedGoat goat = AddonEntities.ENHANCED_GOAT.get().create(level);
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
        EnhancedGoat goat = AddonEntities.ENHANCED_GOAT.get().create(level);
        Genes babyGenes = new Genes(genetics).makeChild(getOrSetIsFemale(), mateGender, mateGenetics);
        defaultCreateAndSpawn(goat, level, babyGenes, -getAdultAge());
        level.addFreshEntity(goat);
        goat.setInitialDefaults();
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_goat";
    }

    @Override
    protected void incrementHunger() {
        hunger += (sleeping ? 0.5F : 1F) * getHungerModifier();
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return true;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return FoodSerialiser.getAnimalFoodMap("goat");
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == AddonItems.ENHANCED_GOAT_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setBagSize(getMilkAmount() / 30F);
    }

    /* Gene Related Code */

    @Override
    protected void fixGeneLengths() {
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
    protected void lethalGenes() {
    }

    /* Model & Textures */

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
    @OnlyIn(Dist.CLIENT)
    public GoatModelData getModelData() {
        return goatModelData;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setModelData(AnimalModelData modelData) {
        goatModelData = (GoatModelData) modelData;
    }

    // Utility method to add a texture with a prefix based on a certain index
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[] texture, int geneValue, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue]));
            this.texturesIndexes.add(String.valueOf(prefixIndex) + String.valueOf(geneValue));
        }
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with a prefix based on a certain index and three keys
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[][][] texture, int geneValue0, int geneValue1, int geneValue2, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue0][geneValue1][geneValue2]));
            this.texturesIndexes.add(String.valueOf(prefixIndex) + String.valueOf(geneValue0) + String.valueOf(geneValue1) + String.valueOf(geneValue2));
        }
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with a prefix based on a certain index and two keys
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[][] texture, int geneValue0, int geneValue1,  boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue0][geneValue1]));
            this.texturesIndexes.add(String.valueOf(prefixIndex) + String.valueOf(geneValue0) + String.valueOf(geneValue1));
        }
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with three keys. Copied from Core GA for addon use since it's protected
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, TexturingType texturingType, String[][][] texture, int geneValue0, int geneValue1, int geneValue2, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(texturingType, texture[geneValue0][geneValue1][geneValue2]));
            this.texturesIndexes.add(String.valueOf(geneValue0)+String.valueOf(geneValue1)+String.valueOf(geneValue2));
        }
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    /* Brain/AI Related Code */

    static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.PATH, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.BREED_TARGET,
            ModMemoryModuleTypes.SLEEPING.get(),
            ModMemoryModuleTypes.SEEKING_FOOD.get(), ModMemoryModuleTypes.HUNGRY.get(),
            ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get(),
            ModMemoryModuleTypes.FOCUS_BRAIN.get(), ModMemoryModuleTypes.PAUSE_BRAIN.get()
    );


    private static final ImmutableList<? extends SensorType<? extends Sensor<? super EnhancedGoat>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT
    );


    @Override
    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel) this.level, this);
        if (!this.isNoAi()) {
            if (getHunger() > hungerLimit) {
                getBrain().setMemory(ModMemoryModuleTypes.HUNGRY.get(), true);
            }

            // Dairy Functionality
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {  
                    if (timeUntilNextMilk-- <= 0) {
                        int milk = getMilkAmount();
                        if (milk < maxBagSize * 30) {
                            milk++;
                            setMilkAmount(milk);
                            timeUntilNextMilk = 2400;
                            setBagSize(milk / (maxBagSize * 30));
                        }
                    }
                }
            }
            GoatAi.updateActivity(this);
        }
    }

    @Override
    protected Brain.Provider<EnhancedGoat> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return GoatAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<EnhancedGoat> getBrain() {
        return (Brain<EnhancedGoat>) super.getBrain();
    }

}
