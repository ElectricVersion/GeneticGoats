package com.electricversion.geneticgoats.model;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import com.electricversion.geneticgoats.model.modeldata.GoatModelData;
import com.electricversion.geneticgoats.model.modeldata.GoatPhenotype;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.model.EnhancedAnimalModel;
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
    private GoatModelData goatModelData;

    private WrappedModelPart base;
    private WrappedModelPart bBodyF;
    private WrappedModelPart bBodyB;
    private WrappedModelPart bHead;
    private WrappedModelPart bLegFL;
    private WrappedModelPart bLegFR;
    private WrappedModelPart bLegBL;
    private WrappedModelPart bLegBR;

    private WrappedModelPart bodyF;
    private WrappedModelPart bodyB;
    private WrappedModelPart head;
    private WrappedModelPart snout;
    private WrappedModelPart upperMouth;
    private WrappedModelPart mouth;

    private WrappedModelPart legFL;
    private WrappedModelPart legFR;
    private WrappedModelPart legBL;
    private WrappedModelPart legBR;

    public ModelEnhancedGoat(ModelPart modelPart) {
        super(modelPart);

        ModelPart basePart = modelPart.getChild("base");

        base = new WrappedModelPart(basePart, "base");
        bBodyF = new WrappedModelPart("bBodyF", basePart);
        bBodyB = new WrappedModelPart("bBodyB", basePart);
        bHead = new WrappedModelPart("bHead", basePart);
        bLegFL = new WrappedModelPart("bLegFL", basePart);
        bLegFR = new WrappedModelPart("bLegFR", basePart);
        
        bLegBL = new WrappedModelPart("bLegBL", basePart);
        bLegBR = new WrappedModelPart("bLegBR", basePart);

        bodyF = new WrappedModelPart("bodyF", basePart);
        bodyB = new WrappedModelPart("bodyB", basePart);
        head = new WrappedModelPart("head", basePart);
        snout = new WrappedModelPart("snout", basePart);
        upperMouth = new WrappedModelPart("upperMouth", basePart);
        mouth = new WrappedModelPart("mouth", basePart);

        legFL = new WrappedModelPart("legFL", basePart);
        legFR = new WrappedModelPart("legFR", basePart);
        legBL = new WrappedModelPart("legBL", basePart);
        legBR = new WrappedModelPart("legBR", basePart);

        base.addChild(bBodyF);
        base.addChild(bBodyB);
        bBodyF.addChild(bodyF);
        bBodyB.addChild(bodyB);

        base.addChild(bHead);
        bHead.addChild(head);
        bHead.addChild(snout);
        bHead.addChild(upperMouth);
        bHead.addChild(mouth);

        base.addChild(bLegFL);
        base.addChild(bLegFR);
        base.addChild(bLegBL);
        base.addChild(bLegBR);
        bLegFL.addChild(legFL);
        bLegFR.addChild(legFR);
        bLegBL.addChild(legBL);
        bLegBR.addChild(legBR);

    }

    @Override
    protected Phenotype createPhenotype(T goat) {
        return new GoatPhenotype(goat.getGenes().getAutosomalGenes(), goat.getOrSetIsFemale());
    }

    private GoatModelData getCreateGoatModelData(T goat) {
        return (GoatModelData) getCreateAnimalModelData(goat);
    }

    @Override
    public void setupAnim(@NotNull T goat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        goatModelData = getCreateGoatModelData(goat);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float r, float g, float b, float a) {
        if (goatModelData != null && goatModelData.getPhenotype() != null) {
            super.renderToBuffer(goatModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, r, g, b, a);
            Map<String, List<Float>> mapOfScale = new HashMap<>(); //Stores transformations for blocks and bones
            poseStack.pushPose();

            gaRender(base, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, r, g, b, a);
            poseStack.popPose();

        }
    }

    @Override
    protected void setInitialModelData(T goat) {
        GoatModelData goatModelData = new GoatModelData();
        setBaseInitialModelData(goatModelData, goat);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();

        PartDefinition baseDef = meshDefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(),
                PartPose.offset(0F, 16F, 0F));
        PartDefinition bBodyFDef = baseDef.addOrReplaceChild("bBodyF", CubeListBuilder.create(),
                PartPose.offset(0F,-9F,-8F));
        PartDefinition bBodyBDef = baseDef.addOrReplaceChild("bBodyB", CubeListBuilder.create(),
                PartPose.offset(0F,-9F,2F));
        PartDefinition bHeadDef = baseDef.addOrReplaceChild("bHead", CubeListBuilder.create(),
                PartPose.offset(0F,-22F,-14F));
        PartDefinition bLegFL = baseDef.addOrReplaceChild("bLegFL", CubeListBuilder.create(),
                PartPose.offset(-4.5F,0F,-8F));
        PartDefinition bLegFR = baseDef.addOrReplaceChild("bLegFR", CubeListBuilder.create(),
                PartPose.offset(1.5F,0F,-8F));
        PartDefinition bLegVL = baseDef.addOrReplaceChild("bLegBL", CubeListBuilder.create(),
                PartPose.offset(-4.5F,0F,8F));
        PartDefinition bLegBR = baseDef.addOrReplaceChild("bLegBR", CubeListBuilder.create(),
                PartPose.offset(1.5F,0F,8F));



        baseDef.addOrReplaceChild("bodyF", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, 0F, 0F, 9, 9, 10),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("bodyB", CubeListBuilder.create()
                        .texOffs(0, 19)
                        .addBox(-4.5F, 0F, 0F, 9, 9, 10),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(38, 0)
                        .addBox(-3.5F, 0F, 0F, 7, 5, 6),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("snout", CubeListBuilder.create()
                        .texOffs(38, 11)
                        .addBox(-2.5F, 1F, -5.5F, 5, 3, 6, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(0F, 0F, 0F, Mth.HALF_PI*0.125F, 0F, 0F));

        baseDef.addOrReplaceChild("upperMouth", CubeListBuilder.create()
                        .texOffs(52, 53)
                        .addBox(-1.5F, 3F, -4F, 3, 1, 4),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("mouth", CubeListBuilder.create()
                        .texOffs(38, 53)
                        .addBox(-1.5F, 4F, -4F, 3, 1, 4),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(22, 38)
                        .addBox(0F, 0F, 0F, 3, 9, 3),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(38, 29)
                        .addBox(0F, 0F, 0F, 3, 9, 3),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(34, 41)
                        .addBox(0F, 0F, 0F, 3, 9, 3),
                PartPose.ZERO);

        baseDef.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(46, 41)
                        .addBox(0F, 0F, 0F, 3, 9, 3),
                PartPose.ZERO);

        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}