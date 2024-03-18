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
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart cloak;

    public PlushModel(int texWidth, int texHeight, boolean slim) {
        // Create a model from the player model
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, slim);

        // Bake and get the parts
        PartDefinition part = mesh.getRoot();
        root = part.bake(texWidth, texHeight);
        head = root.getChild("head");
        body = root.getChild("body");
        rightArm = root.getChild("right_arm");
        leftArm = root.getChild("left_arm");
        rightLeg = root.getChild("right_leg");
        leftLeg = root.getChild("left_leg");
        cloak = root.getChild("cloak");
    }

    @Nullable
    public ModelPart getModelPart(PlushModelPart part) {
        return switch (part) {
            case ROOT -> root;
            case HEAD -> head;
            case BODY -> body;
            case LEFT_ARM -> leftArm;
            case RIGHT_ARM -> rightArm;
            case LEFT_LEG -> leftLeg;
            case RIGHT_LEG -> rightLeg;
            case CLOAK -> cloak;
        };
    }

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green,float blue, float alpha) {
        root.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
