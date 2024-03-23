package com.reimnop.player_plush.client.rendering.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class PlushModel {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart body;
    private final ModelPart jacket;
    private final ModelPart rightArm;
    private final ModelPart rightSleeve;
    private final ModelPart leftArm;
    private final ModelPart leftSleeve;
    private final ModelPart rightLeg;
    private final ModelPart rightPants;
    private final ModelPart leftLeg;
    private final ModelPart leftPants;

    public PlushModel(int texWidth, int texHeight, boolean slim) {
        // Create a model from the player model
        MeshDefinition mesh = PlushMesh.createMesh(slim);

        // Bake and get the parts
        PartDefinition part = mesh.getRoot();
        root = part.bake(texWidth, texHeight);
        head = root.getChild("head");
        hat = root.getChild("hat");
        body = root.getChild("body");
        jacket = root.getChild("jacket");
        rightArm = root.getChild("right_arm");
        rightSleeve = root.getChild("right_sleeve");
        leftArm = root.getChild("left_arm");
        leftSleeve = root.getChild("left_sleeve");
        rightLeg = root.getChild("right_leg");
        rightPants = root.getChild("right_pants");
        leftLeg = root.getChild("left_leg");
        leftPants = root.getChild("left_pants");
    }

    public void applyPose(PlushPose pose) {
        applyPose(pose, PlushBodyPart.ROOT, root);
        applyPose(pose, PlushBodyPart.HEAD, head, hat);
        applyPose(pose, PlushBodyPart.BODY, body, jacket);
        applyPose(pose, PlushBodyPart.RIGHT_ARM, rightArm, rightSleeve);
        applyPose(pose, PlushBodyPart.LEFT_ARM, leftArm, leftSleeve);
        applyPose(pose, PlushBodyPart.RIGHT_LEG, rightLeg, rightPants);
        applyPose(pose, PlushBodyPart.LEFT_LEG, leftLeg, leftPants);
    }

    @Nullable
    public ModelPart getModelPart(PlushModelPart part) {
        return switch (part) {
            case ROOT -> root;
            case HEAD -> head;
            case HAT -> hat;
            case BODY -> body;
            case JACKET -> jacket;
            case RIGHT_ARM -> rightArm;
            case RIGHT_SLEEVE -> rightSleeve;
            case LEFT_ARM -> leftArm;
            case LEFT_SLEEVE -> leftSleeve;
            case RIGHT_LEG -> rightLeg;
            case RIGHT_PANTS -> rightPants;
            case LEFT_LEG -> leftLeg;
            case LEFT_PANTS -> leftPants;
        };
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green,float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 1.875F, 0.0F);
        poseStack.mulPose(Axis.XP.rotation((float) Math.PI));
        root.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    private void applyPose(PlushPose pose, PlushBodyPart bodyPart, ModelPart... modelParts) {
        PlushBodyPartPose bodyPartPose = pose.getPose(bodyPart);
        if (bodyPartPose != null) {
            for (ModelPart modelPart : modelParts) {
                modelPart.x = bodyPartPose.x();
                modelPart.y = bodyPartPose.y();
                modelPart.z = bodyPartPose.z();
                modelPart.xRot = bodyPartPose.pitch();
                modelPart.yRot = bodyPartPose.yaw();
                modelPart.zRot = bodyPartPose.roll();
            }
        }
    }
}
