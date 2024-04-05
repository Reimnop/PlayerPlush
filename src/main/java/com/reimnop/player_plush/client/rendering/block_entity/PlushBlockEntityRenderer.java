package com.reimnop.player_plush.client.rendering.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.reimnop.player_plush.block_entity.PlushBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Environment(EnvType.CLIENT)
public class PlushBlockEntityRenderer implements BlockEntityRenderer<PlushBlockEntity> {
    public PlushBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PlushBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    }
}
