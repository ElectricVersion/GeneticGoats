package com.electricversion.geneticgoats.entity;

import com.electricversion.geneticgoats.config.GoatsCommonConfig;
import com.electricversion.geneticgoats.entity.genetics.GoatGeneticsInitializer;
import com.electricversion.geneticgoats.entity.texture.GoatTexture;
import com.electricversion.geneticgoats.init.AddonEntities;
import com.electricversion.geneticgoats.init.AddonItems;
import com.electricversion.geneticgoats.model.modeldata.GoatModelData;
import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.*;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TextureLayer;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnhancedGoat extends EnhancedAnimalAbstract implements IForgeShearable {

    private static final EntityDataAccessor<Integer> WOOL_LENGTH = SynchedEntityData.defineId(EnhancedGoat.class, EntityDataSerializers.INT);

    public static final float maxPossibleMilk = 24;
    public static final float maxPossibleWool = 12;

    private int woolGrowthTimer = 0;
    private int individualMaxWool;
    private int woolLength; // Used for getting when possible, should never be modified except by setWoolLength.
    // It feels messy having two variables for wool length but its probably better than having to access the synched data every time

    @OnlyIn(Dist.CLIENT)
    private GoatModelData goatModelData;

    /* Initialization & General Properties */
    public EnhancedGoat(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn) {
        super(type, worldIn, GoatGeneticsInitializer.SEXLINKED_GENES_LENGTH, GoatGeneticsInitializer.AUTOSOMAL_GENES_LENGTH, true);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        moveControl = new MoveControl(this);
        lookControl = new LookControl(this);
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
        int[] genes = genetics.getAutosomalGenes();
        float size = 0.75F;
        size -= 0.20F * ((genes[124] + genes[125] + genes[126] + genes[127] - 4) / 16F);
        size += 0.10F * ((genes[128] + genes[129] - 2) / 8F);
        setAnimalSize(size);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
        initialWool();
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor inWorld, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnReason, @Nullable SpawnGroupData livingData, @Nullable CompoundTag itemNbt) {
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
        assert goat != null;
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
        // Wool growth code based on core GA's sheep code for functional consistency.

        // Babies have a lower wool cap so they don't grow out too fast
        int currentWoolCap = getCurrentMaxWool();

        if (currentWoolCap > 0 && currentWoolCap > woolLength) {
            if (hunger <= 36000) {
                woolGrowthTimer++;
            }
            if (woolGrowthTimer >= (24000 / currentWoolCap)) {
                woolGrowthTimer = 0;
                setWoolLength(woolLength + 1);
            }
        }
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
        if ((item == Items.BUCKET || item == ModItems.ONESIXTH_MILK_BUCKET.get() || item == ModItems.ONETHIRD_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BUCKET.get() || item == ModItems.TWOTHIRDS_MILK_BUCKET.get() || item == ModItems.FIVESIXTHS_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BOTTLE.get() || item == Items.GLASS_BOTTLE) && !isBaby() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            handleMilkingInteraction(player, hand);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }


    private void handleMilkingInteraction(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        // Partially copied from Core GA for consistency
        int maxRefill = 0;
        int bucketSize = 6;
        int currentMilk = getMilkAmount();
        boolean isBottle = false;
        if (item == Items.BUCKET) {
            maxRefill = 6;
        } else if (item == ModItems.ONESIXTH_MILK_BUCKET.get()) {
            maxRefill = 5;
        } else if (item == ModItems.ONETHIRD_MILK_BUCKET.get()) {
            maxRefill = 4;
        } else if (item == ModItems.HALF_MILK_BUCKET.get()) {
            maxRefill = 3;
        } else if (item == ModItems.TWOTHIRDS_MILK_BUCKET.get()) {
            maxRefill = 2;
        } else if (item == ModItems.FIVESIXTHS_MILK_BUCKET.get()) {
            maxRefill = 1;
        } else if (item == ModItems.HALF_MILK_BOTTLE.get()) {
            maxRefill = 1;
            isBottle = true;
            bucketSize = 2;
        } else if (item == Items.GLASS_BOTTLE) {
            maxRefill = 2;
            isBottle = true;
            bucketSize = 2;
        }

        int refillAmount = Math.min(currentMilk, maxRefill);

        if (!getLevel().isClientSide()) {
            int resultingMilkAmount = currentMilk - refillAmount;
            setMilkAmount(resultingMilkAmount);

            float milkBagSize = resultingMilkAmount / maxPossibleMilk;

            setBagSize(milkBagSize * maxBagSize);
        }

        int resultAmount = bucketSize - maxRefill + refillAmount;

        ItemStack resultItem = new ItemStack(Items.BUCKET);

        switch (resultAmount) {
            case 0:
                player.playSound(SoundEvents.GOAT_HURT, 1.0F, 1.0F);
                return;
            case 1:
                if (isBottle) {
                    resultItem = new ItemStack(ModItems.HALF_MILK_BOTTLE.get());
                } else {
                    resultItem = new ItemStack(ModItems.ONESIXTH_MILK_BUCKET.get());
                }
                break;
            case 2:
                if (isBottle) {
                    resultItem = new ItemStack(ModItems.MILK_BOTTLE.get());
                } else {
                    resultItem = new ItemStack(ModItems.ONETHIRD_MILK_BUCKET.get());
                }
                break;
            case 3:
                resultItem = new ItemStack(ModItems.HALF_MILK_BUCKET.get());
                break;
            case 4:
                resultItem = new ItemStack(ModItems.TWOTHIRDS_MILK_BUCKET.get());
                break;
            case 5:
                resultItem = new ItemStack(ModItems.FIVESIXTHS_MILK_BUCKET.get());
                break;
            case 6:
                resultItem = new ItemStack(Items.MILK_BUCKET);
                break;
        }

        player.playSound(SoundEvents.GOAT_MILK, 1.0F, 1.0F);
        itemStack.shrink(1);
        if (itemStack.isEmpty()) {
            player.setItemInHand(hand, resultItem);
        } else if (!player.getInventory().add(resultItem)) {
            player.drop(resultItem, false);
        }
    }

    private Block getWoolColor() {
        int[] genes = getGenes().getAutosomalGenes();
        boolean mediumOrHighWhite = GoatTexture.getWhiteExtension(genes) > 0;
        if (
                (genes[4] == 2 || genes[5] == 2) || // Dom White
                (genes[82] != 1 || genes[83] != 1) || // Any level of Schwartzal
                (mediumOrHighWhite && ( // Medium to high...
                            (genes[4] == 3 || genes[5] == 3) // ...Goulet
                            || (genes[4] == 4 && genes[5] == 4) // ...Piebald
                            || (genes[58] != 1 || genes[59] != 1) // ...Belt
                            || (genes[80] == 4 || genes[81] == 4) // ...Flowery
                        )
                )
        ) {
            // White markings
            return Blocks.WHITE_WOOL;
        }
        else if (genes[2] == 2 || genes[3] == 2) {
            // Dom Black
            return Blocks.BLACK_WOOL;
        }
        return Blocks.BROWN_WOOL;
    }

    public @NotNull List<ItemStack> onSheared(Player player, @NotNull ItemStack item, Level world, BlockPos pos, int fortune) {
        List<ItemStack> shearingDrops = new ArrayList<>();
        if (!getLevel().isClientSide()) {
            for (int i = 0; i < woolLength / 4; i++) {
                shearingDrops.add(new ItemStack(getWoolColor()));
            }
        }
        setWoolLength(0);
        return shearingDrops;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setBagSize(getMilkAmount() / maxPossibleMilk);

        setMaxWool();
        if (!compound.getString("breed").isEmpty()) {
            // When summoning a purebred, it should spawn with its wool fully grown
            setWoolLength(getCurrentMaxWool());
        }
        else {
            // Otherwise, load from saved data
            setWoolLength(compound.getInt("Wool"));
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Wool", getWoolLength());
    }

    @Override
    protected void setMaxBagSize() {
        if (getOrSetIsFemale() || GeneticAnimalsConfig.COMMON.omnigenders.get()) {
            int[] genes = getGenes().getAutosomalGenes();

            float bagSize = 0.125F; // Once calculations are complete this should add up to a max of 1.0

            // Dairy added by the udder capacity
            float udderSizeMult = (genes[120] + genes[121] + genes[122] + genes[123] - 4) / 36F;
            bagSize += udderSizeMult * 0.5F;

            // Dairy added by the body type
            int bodyTypeGenes = 24;
            for (int i = 108; i < 114; i++) {
                // Less dairy-bodied
                bodyTypeGenes -= genes[i];
            }
            for (int i = 114; i < 120; i++) {
                // More dairy-bodied
                bodyTypeGenes += genes[i];
            }
            float bodyTypeMult = bodyTypeGenes / 48F;
            bagSize += bodyTypeMult * 0.375F;

            //TODO: Set up scale

            maxBagSize = bagSize;
        }
    }

    @Override
    protected void initialMilk() {
        lactationTimer = -48000;
        // Since there are certain scenarios where maxBagSize isn't properly calculated, lets just be safe
        setMaxBagSize();
        int milk = Math.round(maxBagSize * maxPossibleMilk * 0.75F);
        setMilkAmount(milk);

        setBagSize(milk / (maxBagSize * maxPossibleMilk));
    }

    protected void initialWool() {
        setMaxWool();
        setWoolLength(getCurrentMaxWool());
    }


    public int getWoolLength() {
        return entityData.get(WOOL_LENGTH);
    }

    private void setWoolLength(int newWoolLength) {
        woolLength = newWoolLength; // Both variables should be updated to keep them consistent
        entityData.set(WOOL_LENGTH, newWoolLength);
    }


    protected void setMaxWool() {
        int[] genes = getGenes().getAutosomalGenes();

        int maxWool = 0;

        if (genes[134] == 2 || genes[135] == 2) {
            maxWool = 2;
        }

        for (int i = 142; i < 152; i++) {
            if (genes[i] == 2) maxWool++;
        }

        individualMaxWool = maxWool;
    }

    private int getCurrentMaxWool() {
        float age = getEnhancedAnimalAge();
        return (age >= getAdultAge()) ? individualMaxWool : (int) (individualMaxWool * (age / (float) getAdultAge()));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(WOOL_LENGTH, 0);
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
    public void addPrefixedTexture(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[] texture, int geneValue, String name, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue]));
            texturesIndexes.add(name);
            texturesIndexes.add(String.valueOf(prefixIndex) + geneValue);
        }
        texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with a prefix based on a certain index and three keys
    public void addPrefixedTexture(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[][][] texture, int geneValue0, int geneValue1, int geneValue2, String name, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue0][geneValue1][geneValue2]));
            texturesIndexes.add(name);
            texturesIndexes.add(String.valueOf(prefixIndex) + geneValue0 + geneValue1 + geneValue2);
        }
        texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with a prefix based on a certain index and two keys
    public void addPrefixedTexture(TextureGrouping textureGroup, String[] prefix, int prefixIndex, String[][] texture, int geneValue0, int geneValue1, String name, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(prefix[prefixIndex] + texture[geneValue0][geneValue1]));
            texturesIndexes.add(name);
            texturesIndexes.add(String.valueOf(prefixIndex) + geneValue0 + geneValue1);
        }
        texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with two keys. Copied from Core GA for addon use since it's protected
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, TexturingType texturingType, String[][] texture, int geneValue0, int geneValue1, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(texturingType, texture[geneValue0][geneValue1]));
            texturesIndexes.add(String.valueOf(geneValue0) + geneValue1);
        }
        texturesIndexes.add(CACHE_DELIMITER);
    }

    // Utility method to add a texture with three keys. Copied from Core GA for addon use since it's protected
    public void addTextureToAnimalTextureGrouping(TextureGrouping textureGroup, TexturingType texturingType, String[][][] texture, int geneValue0, int geneValue1, int geneValue2, boolean check) {
        if (check) {
            textureGroup.addTextureLayers(new TextureLayer(texturingType, texture[geneValue0][geneValue1][geneValue2]));
            texturesIndexes.add(String.valueOf(geneValue0) + geneValue1 + geneValue2);
        }
        texturesIndexes.add(CACHE_DELIMITER);
    }

    /* Brain/AI Related Code
    * Currently all brain related code is commented out because after seeing that modern minecraft is still using goals,
    * and brains have been changed considerably from 1.18, it's hard to justify using the more complex Brain system
    * for an animal with pretty basic AI functions.
    * */


//    static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
//            MemoryModuleType.PATH, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET,
//            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
//            MemoryModuleType.BREED_TARGET,
//            ModMemoryModuleTypes.SLEEPING.get(),
//            ModMemoryModuleTypes.SEEKING_FOOD.get(), ModMemoryModuleTypes.HUNGRY.get(),
//            ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get(),
//            ModMemoryModuleTypes.FOCUS_BRAIN.get(), ModMemoryModuleTypes.PAUSE_BRAIN.get(),
//            // The following required for the generic grazing AI, even though we won't ever be setting them obviously
//            ModMemoryModuleTypes.ROOSTING.get(), ModMemoryModuleTypes.EGG_LAYING.get()
//    );


//    private static final ImmutableList<? extends SensorType<? extends Sensor<? super EnhancedGoat>>> SENSOR_TYPES = ImmutableList.of(
//            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT
//    );


    @Override
    protected void customServerAiStep() {
//        getBrain().tick((ServerLevel) getLevel(), this);
        if (!isNoAi()) {
//            if (getHunger() > hungerLimit) {
//                getBrain().setMemory(ModMemoryModuleTypes.HUNGRY.get(), true);
//            }

            // Dairy Functionality
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (timeUntilNextMilk-- <= 0) {
                        int milk = getMilkAmount();
                        if (milk < maxBagSize * maxPossibleMilk) {
                            milk++;
                            setMilkAmount(milk);
                            timeUntilNextMilk = 2400;
                            setBagSize(milk / (maxBagSize * maxPossibleMilk));
                        }
                    }
                }
            }
//            GoatAi.updateActivity(this);
        }
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        // TODO: Add grazing
        return new HashMap<>();
    }


    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new EnhancedPanicGoal(this, 1.25D));
        goalSelector.addGoal(3, new EnhancedBreedGoal(this, 1.0D));
        goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(6, new StayShelteredGoal(this, 5723, 7000, 1000));
        goalSelector.addGoal(7, new SeekShelterGoal(this, 1.0D, 5723, 7000, 1000));
        goalSelector.addGoal(8, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        goalSelector.addGoal(9, new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50));
        goalSelector.addGoal(10, new EnhancedLookAtGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(11, new EnhancedLookRandomlyGoal(this));
    }


//    @Override
//    protected Brain.@NotNull Provider<EnhancedGoat> brainProvider() {
//        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
//    }

//    @Override
//    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
//        return GoatAi.makeBrain(brainProvider().makeBrain(dynamic));
//    }

//    @Override
//    public @NotNull Brain<EnhancedGoat> getBrain() {
//        return (Brain<EnhancedGoat>) super.getBrain();
//    }

}
