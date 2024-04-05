package com.reimnop.player_plush.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.reimnop.player_plush.client.rendering.model.CapeMesh;
import com.reimnop.player_plush.client.rendering.model.PlushModel;
import com.reimnop.player_plush.client.rendering.model.PlushPose;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

@Environment(EnvType.CLIENT)
public class PlushRenderHelper {
    private static final PlushModel FULL_MODEL = new PlushModel(64, 64, false);
    private static final PlushModel SLIM_MODEL = new PlushModel(64, 64, true);
    private static final ModelPart CAPE_MODEL = CapeMesh.createMesh().getRoot().bake(64, 32);

    public static void render(
            PlushPose pose,
            ResourceLocation skinTexture,
            @Nullable ResourceLocation capeTexture,
            boolean slim,
            PoseStack matrices,
            MultiBufferSource vertexConsumers,
            int light,
            int overlay,
            float red,
            float green,
            float blue,
            float alpha) {

        // Prepare
        PlushModel model = slim ? SLIM_MODEL : FULL_MODEL;
        model.applyPose(pose);

        // Render skin
        RenderType renderType = getRenderType(skinTexture);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderType);

        matrices.pushPose();
        // Flip the model
        matrices.translate(0.0F, 1.875F, 0.0F);
        matrices.mulPose(Axis.XP.rotation((float) Math.PI));
        model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();

        // Render cape
        if (capeTexture != null) {
            RenderType capeRenderType = getRenderType(capeTexture);
            VertexConsumer capeVertexConsumer = vertexConsumers.getBuffer(capeRenderType);

            matrices.pushPose();
            // Flip the cape
            matrices.translate(0.0F, 2.375F, -0.125F);
            matrices.mulPose(Axis.YP.rotation((float) Math.PI));
            matrices.mulPose(Axis.XP.rotation((float) Math.PI));
            CAPE_MODEL.render(matrices, capeVertexConsumer, light, overlay, red, green, blue, alpha);
            matrices.popPose();
        }
    }

    private static RenderType getRenderType(ResourceLocation resourceLocation) {
        return RenderType.itemEntityTranslucentCull(resourceLocation);
    }
}
