package com.reimnop.player_plush.client.rendering.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PlushMesh {
    public static MeshDefinition createMesh(boolean slim) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition
                .addOrReplaceChild(
                        "head",
                        CubeListBuilder.create()
                                .texOffs(0, 0)
                                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "hat",
                        CubeListBuilder.create()
                                .texOffs(32, 0)
                                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                        PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "body",
                        CubeListBuilder.create()
                                .texOffs(16, 16)
                                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "jacket",
                        CubeListBuilder.create()
                                .texOffs(16, 32)
                                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                        PartPose.offset(0.0F, 0.0F, 0.0F));
        if (slim) {
            partDefinition
                    .addOrReplaceChild("left_arm",
                            CubeListBuilder.create()
                                    .texOffs(32, 48)
                                    .addBox(9.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                            PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild("left_sleeve",
                            CubeListBuilder.create()
                                    .texOffs(48, 48)
                                    .addBox(9.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                            PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild("right_arm",
                            CubeListBuilder.create()
                                    .texOffs(40, 16)
                                    .addBox(-12.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                            PartPose.offset(5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild("right_sleeve",
                            CubeListBuilder.create()
                                    .texOffs(40, 32)
                                    .addBox(-12.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                            PartPose.offset(5.0F, 2.0F, 0.0F));

        } else {
            partDefinition
                    .addOrReplaceChild(
                            "left_arm",
                            CubeListBuilder.create()
                                    .texOffs(32, 48)
                                    .addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                            PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild(
                            "left_sleeve",
                            CubeListBuilder.create()
                                    .texOffs(48, 48)
                                    .addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                            PartPose.offset(-5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild(
                            "right_arm",
                            CubeListBuilder.create()
                                    .texOffs(40, 16)
                                    .addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                            PartPose.offset(5.0F, 2.0F, 0.0F));
            partDefinition
                    .addOrReplaceChild(
                            "right_sleeve",
                            CubeListBuilder.create()
                                    .texOffs(40, 32)
                                    .addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                            PartPose.offset(5.0F, 2.0F, 0.0F));
        }
        partDefinition
                .addOrReplaceChild(
                        "left_leg",
                        CubeListBuilder.create()
                                .texOffs(16, 48)
                                .addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "left_pants",
                        CubeListBuilder.create()
                                .texOffs(0, 48)
                                .addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                        PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "right_leg",
                        CubeListBuilder.create()
                                .texOffs(0, 16)
                                .addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition
                .addOrReplaceChild(
                        "right_pants",
                        CubeListBuilder.create()
                                .texOffs(0, 32)
                                .addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                        PartPose.offset(2.0F, 12.0F, 0.0F));
        return meshDefinition;
    }
}
