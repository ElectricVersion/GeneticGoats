package com.electricversion.geneticgoats.render;

import com.electricversion.geneticgoats.GeneticGoats;
import com.electricversion.geneticgoats.entity.EnhancedGoat;
import com.electricversion.geneticgoats.model.ModelEnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedGoat extends MobRenderer<EnhancedGoat, ModelEnhancedGoat<EnhancedGoat>> {

    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String GOAT_TEXTURE_LOCATION = "geneticgoats:textures/entity/goat/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("geneticgoats:textures/entity/goat/goat_base.png");
    public static final ModelLayerLocation GOAT_LAYER = new ModelLayerLocation(new ResourceLocation(GeneticGoats.MOD_ID, "goat"), "goat_layer");

    public RenderEnhancedGoat(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedGoat<>(renderManager.bakeLayer(GOAT_LAYER)), 0.2F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(EnhancedGoat goat) {
        String path = goat.getTexture();
        Colouration colors = goat.getRgb();

        if (path == null || path.isEmpty() || colors == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        path = path + colors.getRGBStrings();

        ResourceLocation resourceLocation = textureCache.getFromCache(path);

        if (resourceLocation == null) {
            TextureGrouping textureGrouping = goat.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourceLocation = new ResourceLocation(path);

                Minecraft.getInstance().getTextureManager().register(resourceLocation, new EnhancedLayeredTexturer(GOAT_TEXTURE_LOCATION, textureGrouping, colors, 128, 128));

                textureCache.putInCache(path, resourceLocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourceLocation;
    }
}
