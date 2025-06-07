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
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
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
    private WrappedModelPart bBody;
    private WrappedModelPart testCube;

    public ModelEnhancedGoat(ModelPart modelPart) {
        super(modelPart);

        ModelPart basePart = modelPart.getChild("base");

        base = new WrappedModelPart(basePart, "base");
        bBody = new WrappedModelPart("base", basePart);
        testCube = new WrappedModelPart("testCube", basePart);

        base.addChild(bBody);
        bBody.addChild(testCube);
    }

    @Override
    protected Phenotype createPhenotype(T goat) {
        return new GoatPhenotype(goat.getGenes().getAutosomalGenes(), goat.getOrSetIsFemale());
    }

    @Override
    public void setupAnim(@NotNull T goat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

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

        PartDefinition base = meshDefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bBody = base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("testCube", CubeListBuilder.create()
                .addBox(0,0,0,8,8,8),
                PartPose.ZERO);


        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}