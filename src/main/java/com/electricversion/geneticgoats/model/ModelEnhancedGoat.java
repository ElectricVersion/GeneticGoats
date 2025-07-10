package com.electricversion.geneticgoats.model;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import com.electricversion.geneticgoats.model.modeldata.GoatModelData;
import com.electricversion.geneticgoats.model.modeldata.GoatPhenotype;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.model.EnhancedAnimalModel;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedGoat<T extends EnhancedGoat> extends EnhancedAnimalModel<T> {
    private static final float baseNeckAngle = Mth.HALF_PI*0.30F;

    private GoatModelData goatModelData;

    /* Bones */

    private final WrappedModelPart base;

    private final WrappedModelPart bBodyF;
    private final WrappedModelPart bBodyB;

    private final WrappedModelPart bNeck;

    private final WrappedModelPart bHead;
    private final WrappedModelPart bEarL;
    private final WrappedModelPart bEarR;

    private final WrappedModelPart bLegFL;
    private final WrappedModelPart bLegFR;
    private final WrappedModelPart bLegBL;
    private final WrappedModelPart bLegBR;

    /* Blocks */

    private final WrappedModelPart tail;

    private final WrappedModelPart neck;

    private final WrappedModelPart head;
    private final WrappedModelPart muzzle;
    private final WrappedModelPart upperMouth;
    private final WrappedModelPart mouth;
    private final WrappedModelPart eyeL;
    private final WrappedModelPart eyeR;

    private final WrappedModelPart earL5;
    private final WrappedModelPart earR5;
    private final WrappedModelPart earL7;
    private final WrappedModelPart earR7;
    private final WrappedModelPart earL9;
    private final WrappedModelPart earR9;
    private final WrappedModelPart earL12;
    private final WrappedModelPart earR12;


    private final WrappedModelPart legFL;
    private final WrappedModelPart legFR;
    private final WrappedModelPart legBL;
    private final WrappedModelPart legBR;
    private final WrappedModelPart legBFL;
    private final WrappedModelPart legBFR;
    private final WrappedModelPart legBBL;
    private final WrappedModelPart legBBR;

    private final WrappedModelPart bodyF;
    private final WrappedModelPart bodyB;

    /* Part Setup */

    public ModelEnhancedGoat(ModelPart modelPart) {
        super(modelPart);

        ModelPart basePart = modelPart.getChild("base");

        /* BONES */
        base = new WrappedModelPart(basePart, "base");

        bBodyF = new WrappedModelPart("bBodyF", basePart);
        bBodyB = new WrappedModelPart("bBodyB", basePart);

        bNeck = new WrappedModelPart("bNeck", basePart);

        bHead = new WrappedModelPart("bHead", basePart);
        bEarL = new WrappedModelPart("bEarL", basePart);
        bEarR = new WrappedModelPart("bEarR", basePart);

        bLegFL = new WrappedModelPart("bLegFL", basePart);
        bLegFR = new WrappedModelPart("bLegFR", basePart);
        bLegBL = new WrappedModelPart("bLegBL", basePart);
        bLegBR = new WrappedModelPart("bLegBR", basePart);

        /* BLOCKS */
        bodyF = new WrappedModelPart("bodyF", basePart);
        bodyB = new WrappedModelPart("bodyB", basePart);
        tail = new WrappedModelPart("tail", basePart);

        neck = new WrappedModelPart("neck", basePart);

        head = new WrappedModelPart("head", basePart);
        muzzle = new WrappedModelPart("muzzle", basePart);
        upperMouth = new WrappedModelPart("upperMouth", basePart);
        mouth = new WrappedModelPart("mouth", basePart);
        eyeL = new WrappedModelPart("eyeL", basePart);
        eyeR = new WrappedModelPart("eyeR", basePart);
        earL5 = new WrappedModelPart("earL5", basePart);
        earR5 = new WrappedModelPart("earR5", basePart);
        earL7 = new WrappedModelPart("earL7", basePart);
        earR7 = new WrappedModelPart("earR7", basePart);
        earL9 = new WrappedModelPart("earL9", basePart);
        earR9 = new WrappedModelPart("earR9", basePart);
        earL12 = new WrappedModelPart("earL12", basePart);
        earR12 = new WrappedModelPart("earR12", basePart);


        legFL = new WrappedModelPart("legFL", basePart);
        legFR = new WrappedModelPart("legFR", basePart);
        legBL = new WrappedModelPart("legBL", basePart);
        legBR = new WrappedModelPart("legBR", basePart);
        legBFL = new WrappedModelPart("legBFL", basePart);
        legBFR = new WrappedModelPart("legBFR", basePart);
        legBBL = new WrappedModelPart("legBBL", basePart);
        legBBR = new WrappedModelPart("legBBR", basePart);

        base.addChild(bBodyF);
        base.addChild(bBodyB);
        bBodyF.addChild(bodyF);
        bBodyB.addChild(bodyB);
        bBodyB.addChild(tail);

        base.addChild(bNeck);
        bNeck.addChild(neck);

        bNeck.addChild(bHead);
        bHead.addChild(head);
        bHead.addChild(muzzle);
        bHead.addChild(upperMouth);
        bHead.addChild(mouth);
        head.addChild(eyeL);
        head.addChild(eyeR);
        bHead.addChild(bEarL);
        bHead.addChild(bEarR);
        bEarL.addChild(earL5);
        bEarR.addChild(earR5);
        bEarL.addChild(earL7);
        bEarR.addChild(earR7);
        bEarL.addChild(earL9);
        bEarR.addChild(earR9);
        bEarL.addChild(earL12);
        bEarR.addChild(earR12);

        base.addChild(bLegFL);
        base.addChild(bLegFR);
        base.addChild(bLegBL);
        base.addChild(bLegBR);
        bLegFL.addChild(legFL);
        bLegFR.addChild(legFR);
        bLegBL.addChild(legBL);
        bLegBR.addChild(legBR);
        legFL.addChild(legBFL);
        legFR.addChild(legBFR);
        legBL.addChild(legBBL);
        legBR.addChild(legBBR);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();

        // X = left
        // Y = down
        // Z = back

        PartDefinition baseDef = meshDefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(),
                PartPose.offset(0F, 24F, 0F));

        baseDef.addOrReplaceChild("bBodyF", CubeListBuilder.create(),
                PartPose.offset(0F, -19F, -9F));
        baseDef.addOrReplaceChild("bBodyB", CubeListBuilder.create(),
                PartPose.offset(0F, -19F, 1F));
        baseDef.addOrReplaceChild("bNeck", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -16F, -6F, baseNeckAngle, 0F, 0F));
        baseDef.addOrReplaceChild("bHead", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -9F, -1.5F, -baseNeckAngle, 0F, 0F));
        baseDef.addOrReplaceChild("bLegFL", CubeListBuilder.create(),
                PartPose.offset(1.49F, -11F, -5.99F));
        baseDef.addOrReplaceChild("bLegFR", CubeListBuilder.create(),
                PartPose.offset(-4.49F, -11F, -5.99F));
        baseDef.addOrReplaceChild("bLegBL", CubeListBuilder.create(),
                PartPose.offset(1.49F, -11F, 10.99F));
        baseDef.addOrReplaceChild("bLegBR", CubeListBuilder.create(),
                PartPose.offset(-4.49F, -11F, 10.99F));
        baseDef.addOrReplaceChild("bEarL", CubeListBuilder.create(),
                PartPose.offset(3F, -5F, 1.95F));
        baseDef.addOrReplaceChild("bEarR", CubeListBuilder.create(),
                PartPose.offset(-3F, -5F, 1.95F));

        // Body
        baseDef.addOrReplaceChild("bodyF", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, 0F, 0F, 9, 9, 10),
                PartPose.offset(0F, 0F, 0F));

        baseDef.addOrReplaceChild("bodyB", CubeListBuilder.create()
                        .texOffs(0, 20)
                        .addBox(-4.5F, 0F, 0F, 9, 9, 10),
                PartPose.offset(0F, 0F, 0F));

        baseDef.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(39, 30)
                        .addBox(-1F, 0F, -2F, 2, 3, 6),
                PartPose.offsetAndRotation(0F, 0F, 10F, Mth.HALF_PI * 0.45F, 0F, 0F));

        baseDef.addOrReplaceChild("neck", CubeListBuilder.create()
                           .texOffs(0, 40)
                     .addBox(-2.5F, -12F, -3F, 5, 12, 6),
                PartPose.ZERO);

        // Head
        baseDef.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(50, 44)
                        .addBox(-3F, -5F, -6F, 6, 5, 6),
                PartPose.offset(0F, 0F, 3F));

        baseDef.addOrReplaceChild("muzzle", CubeListBuilder.create()
                        .texOffs(24, 44)
                        .addBox(-2.5F, -0.8F, -6F, 5, 4, 7, new CubeDeformation(-0.99F, -0.8F, -1)),
                PartPose.offsetAndRotation(0F, -4F, -2.75F, Mth.HALF_PI * 0.125F, 0F, 0F));

        baseDef.addOrReplaceChild("upperMouth", CubeListBuilder.create()
                        .texOffs(28, 56)
                        .addBox(-1.5F, -2F, -10F, 3, 1, 4),
                PartPose.offset(0F, 0F, 3F));

        baseDef.addOrReplaceChild("mouth", CubeListBuilder.create()
                        .texOffs(28, 62)
                        .addBox(-1.5F, -1F, -10F, 3, 1, 4),
                PartPose.offset(0F, 0F, 3F));

        baseDef.addOrReplaceChild("eyeL", CubeListBuilder.create()
                        .texOffs(61, 68)
                        .addBox(0.505F, -5.5F, -7.505F, 4, 4, 4, new CubeDeformation(-1.5F)),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("eyeR", CubeListBuilder.create()
                        .texOffs(44, 68)
                        .addBox(-4.505F, -5.5F, -7.505F, 4, 4, 4, new CubeDeformation(-1.5F)),
                PartPose.ZERO);


        baseDef.addOrReplaceChild("earL5", CubeListBuilder.create()
                        .texOffs(87, 64)
                        .addBox(0F, -5F, 0F, 3, 5, 1),
                PartPose.rotation(0F, 0F, Mth.HALF_PI));

        baseDef.addOrReplaceChild("earR5", CubeListBuilder.create()
                        .texOffs(78, 64)
                        .addBox(-3F, -5F, 0F, 3, 5, 1),
                PartPose.rotation(0F, 0F, -Mth.HALF_PI));

        baseDef.addOrReplaceChild("earL7", CubeListBuilder.create()
                        .texOffs(87, 64)
                        .addBox(0F, -7F, 0F, 3, 7, 1),
                PartPose.rotation(0F, 0F, Mth.HALF_PI*1.5F));

        baseDef.addOrReplaceChild("earR7", CubeListBuilder.create()
                        .texOffs(78, 64)
                        .addBox(-3F, -7F, 0F, 3, 7, 1),
                PartPose.rotation(0F, 0F, -Mth.HALF_PI*1.5F));


        baseDef.addOrReplaceChild("earL9", CubeListBuilder.create()
                        .texOffs(87, 64)
                        .addBox(0F, -9F, -1F, 3, 9, 1),
                PartPose.offsetAndRotation(0F,0F,-2F, 0F, -Mth.HALF_PI, Mth.HALF_PI));

        baseDef.addOrReplaceChild("earR9", CubeListBuilder.create()
                        .texOffs(78, 64)
                        .addBox(-3F, -9F, -1F, 3, 9, 1),
                PartPose.offsetAndRotation(0F,0F,-2F, 0F, Mth.HALF_PI, -Mth.HALF_PI));


        baseDef.addOrReplaceChild("earL12", CubeListBuilder.create()
                        .texOffs(87, 64)
                        .addBox(0F, -12F, -1F, 3, 12, 1),
                PartPose.offsetAndRotation(0F,0F,-2F, 0F, -Mth.HALF_PI, Mth.HALF_PI));

        baseDef.addOrReplaceChild("earR12", CubeListBuilder.create()
                        .texOffs(78, 64)
                        .addBox(-3F, -12F, -1F, 3, 12, 1),
                PartPose.offsetAndRotation(0F,0F,-2F, 0F, Mth.HALF_PI, -Mth.HALF_PI));

        // Upper Legs
        baseDef.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(13, 59)
                        .addBox(0F, 6F, 0F, 3, 6, 3),
                PartPose.offset(0F, -6F, -3F));

        baseDef.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(0, 59)
                        .addBox(0F, 6F, 0F, 3, 6, 3),
                PartPose.offset(0F, -6F, -3F));

        baseDef.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(13, 69)
                        .addBox(0F, 6F, 0F, 3, 6, 3),
                PartPose.offset(0F, -6F, -3F));

        baseDef.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(0, 69)
                        .addBox(0F, 6F, 0F, 3, 6, 3),
                PartPose.offset(0F, -6F, -3F));

        // Lower Legs
        baseDef.addOrReplaceChild("legBFL", CubeListBuilder.create()
                        .texOffs(13, 79)
                        .addBox(0F, 5F, 0F, 3, 5, 3),
                PartPose.offset(0F, 7F, 0F));

        baseDef.addOrReplaceChild("legBFR", CubeListBuilder.create()
                        .texOffs(0, 79)
                        .addBox(0F, 5F, 0F, 3, 5, 3),
                PartPose.offset(0F, 7F, 0F));

        baseDef.addOrReplaceChild("legBBL", CubeListBuilder.create()
                        .texOffs(13, 88)
                        .addBox(0F, 5F, 0F, 3, 5, 3),
                PartPose.offset(0F, 7F, 0F));

        baseDef.addOrReplaceChild("legBBR", CubeListBuilder.create()
                        .texOffs(0, 88)
                        .addBox(0F, 5F, 0F, 3, 5, 3),
                PartPose.offset(0F, 7F, 0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    /* Model Data */

    @Override
    protected Phenotype createPhenotype(T goat) {
        return new GoatPhenotype(goat.getGenes().getAutosomalGenes(), goat.getOrSetIsFemale());
    }

    private GoatModelData getCreateGoatModelData(T goat) {
        return (GoatModelData) getCreateAnimalModelData(goat);
    }

    @Override
    protected void setInitialModelData(T goat) {
        GoatModelData goatModelData = new GoatModelData();
        setBaseInitialModelData(goatModelData, goat);
    }

    /* Animation */

    private void setupInitialAnimationValues(AnimalModelData data, float netHeadYaw, float headPitch) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            bLegFL.setRotation(0F, 0F, 0F);
            bLegFR.setRotation(0F, 0F, 0F);
            bLegBL.setRotation(0F, 0F, 0F);
            bLegBR.setRotation(0F, 0F, 0F);
            bNeck.setRotation(baseNeckAngle, 0F, 0F);
            bHead.setRotation(-baseNeckAngle, 0F, 0F);
        } else {
            setRotationFromVector(bLegFL, map.get("bLegFL"));
            setRotationFromVector(bLegFR, map.get("bLegFR"));
            setRotationFromVector(bLegBL, map.get("bLegBL"));
            setRotationFromVector(bLegBR, map.get("bLegBR"));
            setRotationFromVector(bNeck, map.get("bNeck"));
            setRotationFromVector(bHead, map.get("bHead"));
        }
    }

    protected void saveAnimationValues(AnimalModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bLegFL", getRotationVector(bLegFL));
        map.put("bLegFR", getRotationVector(bLegFR));
        map.put("bLegBL", getRotationVector(bLegBL));
        map.put("bLegBR", getRotationVector(bLegBR));
        map.put("bNeck", getRotationVector(bNeck));
        map.put("bHead", getRotationVector(bHead));
    }

    private void lookAnim(float netHeadYaw, float headPitch) {
        float xRotDefault = baseNeckAngle;
        float xRot = (limit(headPitch, 45) * 0.0025F) * Mth.HALF_PI;
        float yRot = limit(netHeadYaw, 90) * Mth.HALF_PI * 0.005F;
        bHead.setXRot(lerpTo(bHead.getXRot(), xRot * 0.5F - xRotDefault));
        bNeck.setXRot(lerpTo(bNeck.getXRot(), xRot + xRotDefault));
        bHead.setYRot(lerpTo(bHead.getYRot(), yRot * 0.125F));
        bNeck.setYRot(lerpTo(bNeck.getYRot(), yRot));
    }

    private void walkAnim(float limbSwing, float limbSwingAmount) {
        float angleMultiplier = limbSwingAmount * 1.5F;
        float leftLegAngle = Mth.sin(limbSwing) * angleMultiplier;
        float rightLegAngle = Mth.sin(-limbSwing) * angleMultiplier;
        bLegFL.setXRot(leftLegAngle);
        bLegFR.setXRot(rightLegAngle);
        bLegBL.setXRot(leftLegAngle);
        bLegBR.setXRot(rightLegAngle);
    }

    private void grazeAnim(float ticksOfGrazing) {
        bNeck.setXRot(lerpTo(bNeck.getXRot(), Mth.HALF_PI*0.9F + baseNeckAngle));
        bHead.setXRot(lerpTo(bHead.getXRot(), -Mth.HALF_PI*0.2F - baseNeckAngle));
    }

    @Override
    public void setupAnim(@NotNull T goat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        goatModelData = getCreateGoatModelData(goat);
        if (goatModelData != null) {
            setupInitialAnimationValues(goatModelData, netHeadYaw, headPitch);
            if (goatModelData.isEating != 0) {
                if (goatModelData.isEating == -1) {
                    goatModelData.isEating = (int)ageInTicks + 90;
                } else if (goatModelData.isEating < ageInTicks) {
                    goatModelData.isEating = 0;
                }
                grazeAnim(goatModelData.isEating - ageInTicks);
            } else {
                lookAnim(netHeadYaw, headPitch);
            }

            if (goat.getDeltaMovement().horizontalDistanceSqr() > 0.001 || goat.xOld != goat.getX() || goat.zOld != goat.getZ()) {
                walkAnim(limbSwing, limbSwingAmount);
            } else {
                bLegFL.setXRot(lerpTo(bLegFL.getXRot(), 0F));
                bLegFR.setXRot(lerpTo(bLegFR.getXRot(), 0F));
                bLegBL.setXRot(lerpTo(bLegBL.getXRot(), 0F));
                bLegBR.setXRot(lerpTo(bLegBR.getXRot(), 0F));
            }
            saveAnimationValues(goatModelData);
        }
    }

    /* Render */

    private void resetCubes() {
        // Hide all swappable blocks
        earL5.hide();
        earR5.hide();
        earL7.hide();
        earR7.hide();
        earL9.hide();
        earR9.hide();
        earL12.hide();
        earR12.hide();

        setupEars();
    }

    private void setupEars() {
        // Enable the necessary blocks
        switch (goatModelData.getPhenotype().getEarLength()) {
            case NORMAL -> {
                earL5.show();
                earR5.show();
            }
            case LONG -> {
                earL7.show();
                earR7.show();
            }
            case EXTRA_LONG -> {
                earL9.show();
                earR9.show();
            }
            case LONGEST -> {
                earL12.show();
                earR12.show();
            }
        }
        // Set Ear Positioning
        switch (goatModelData.getPhenotype().getEarPlacement()) {
            case HIGH -> {
                bEarL.setY(-5F);
                bEarR.setY(-5F);
            }
            case MEDIUM -> {
                bEarL.setY(-4F);
                bEarR.setY(-4F);
            }
            case LOW -> {
                bEarL.setY(-3F);
                bEarR.setY(-3F);
            }
        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float r, float g, float b, float a) {
        if (goatModelData != null && goatModelData.getPhenotype() != null) {
            resetCubes();

            super.renderToBuffer(goatModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, r, g, b, a);
            Map<String, List<Float>> mapOfScale = new HashMap<>(); //Stores transformations for blocks and bones
            poseStack.pushPose();

            float goatScale = ((3F * goatModelData.size * goatModelData.growthAmount) + goatModelData.size) / 4F;
            poseStack.scale(goatScale, goatScale, goatScale);
            poseStack.translate(0.0F, -1.5F + 1.5F / goatScale, 0.0F);
            gaRender(base, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, r, g, b, a);
            poseStack.popPose();

        }
    }

}