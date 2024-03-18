package com.reimnop.player_plush.client.rendering.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.Nullable;

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
    private final ModelPart cloak;

    public PlushModel(int texWidth, int texHeight, boolean slim) {
        // Create a model from the player model
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, slim);

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
        cloak = root.getChild("cloak");

        // TODO: Don't hardcode pose
        rightArm.xRot = rightSleeve.xRot = (float) Math.toRadians(-30.0D);
        leftArm.xRot = leftSleeve.xRot = (float) Math.toRadians(-30.0D);
        rightLeg.xRot = rightPants.xRot = (float) Math.toRadians(-85.0D);
        rightLeg.yRot = rightPants.yRot = (float) Math.toRadians(15.0D);
        leftLeg.xRot = leftPants.xRot = (float) Math.toRadians(-85.0D);
        leftLeg.yRot = leftPants.yRot = (float) Math.toRadians(-15.0D);
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
            case CLOAK -> cloak;
        };
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green,float blue, float alpha) {
        root.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
