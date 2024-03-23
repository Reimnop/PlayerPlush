package com.reimnop.player_plush.client.rendering.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CapeMesh {
    public static MeshDefinition createMesh() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition
                .addOrReplaceChild("cape",
                        CubeListBuilder.create()
                                .texOffs(0, 0)
                                .addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.0F, 8.0F, 0.5F));
        return meshDefinition;
    }
}
