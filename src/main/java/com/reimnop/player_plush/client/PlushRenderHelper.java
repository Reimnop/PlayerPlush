package com.reimnop.player_plush.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.reimnop.player_plush.client.rendering.model.*;
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
        matrices.translate(0.0D, 1.5D, 0.0D);
        matrices.mulPose(Axis.XP.rotation((float) Math.PI));
        model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();

        // Render cape
        if (capeTexture != null) {
            RenderType capeRenderType = getRenderType(capeTexture);
            VertexConsumer capeVertexConsumer = vertexConsumers.getBuffer(capeRenderType);

            PlushBodyPartPose capePose = pose.getPose(PlushBodyPart.CAPE);
            if (capePose == null) {
                capePose = PlushBodyPartPose.DEFAULT_CAPE;
            }

            CAPE_MODEL.x = capePose.x();
            CAPE_MODEL.y = capePose.y();
            CAPE_MODEL.z = capePose.z();
            CAPE_MODEL.xRot = capePose.pitch();
            CAPE_MODEL.yRot = capePose.yaw();
            CAPE_MODEL.zRot = capePose.roll();

            matrices.pushPose();
            matrices.translate(0.0D, 1.5D, -0.125D);
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
